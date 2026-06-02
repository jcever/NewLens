# Week 1 Search Flow

End-of-Week-1 snapshot of how one search request travels through the backend.
Written after Day 6 as a reference for the upcoming weeks.

## Request lifecycle

```
Browser / curl
     │  GET /api/search?q=openai
     ▼
Tomcat (embedded, :8080)
     │
     ▼
DispatcherServlet
     │  routes /api/search -> SearchController.search
     ▼
@Validated proxy
     │  runs Bean Validation on the @RequestParam q
     │  - q missing       -> MissingServletRequestParameterException
     │  - q blank ("")    -> ConstraintViolationException
     │  - q non-blank     -> continue
     ▼
SearchController.search(q)
     │  thin HTTP plumbing: delegates immediately
     ▼
SearchService.search(q)
     │  log: search_start query='openai' source=guardian
     ▼
NewsClient.searchRaw(q)
     │  log: upstream_request provider=guardian query='openai'
     │  builds URL via RestClient + UriBuilder (URL-encoded)
     │
     ├──► HTTPS GET https://content.guardianapis.com/search?q=openai&api-key=...
     ◄──┤  200 OK + JSON body (Guardian's wire shape)
     │
     │  Jackson deserializes JSON -> GuardianRawResponse (nested records)
     ▼
GuardianArticleMapper.toArticles(raw)
     │  pure static function: Guardian shape -> ArticleDto list
     │  parses webPublicationDate into Instant
     ▼
SearchService.search returns List<ArticleDto>
     │  log: search_complete query='openai' source=guardian result_count=10
     ▼
SearchController.search returns List<ArticleDto>
     │
     ▼
Spring MessageConverter (Jackson)
     │  serializes List<ArticleDto> -> JSON
     │  Instant -> ISO-8601 string (Spring Boot 3 default)
     ▼
Tomcat writes 200 + JSON body to socket
     │
     ▼
Browser / curl sees the response
```

## Error paths

```
GET /api/search             (q missing)
    -> MissingServletRequestParameterException
    -> GlobalExceptionHandler.handleMissingParam
    -> 400 + {"code":"missing_parameter", ...}

GET /api/search?q=          (q blank)
    -> @NotBlank fails inside @Validated proxy
    -> ConstraintViolationException
    -> GlobalExceptionHandler.handleConstraintViolation
    -> 400 + {"code":"validation_error", "message":"must not be blank"}

GET /api/search?q=openai    (Guardian unreachable / 4xx / 5xx)
    -> NewsClient's RestClient.retrieve() throws RestClientException
    -> propagates through SearchService.search
    -> GlobalExceptionHandler.handleUpstreamError
    -> log WARN upstream_error class=... message=...
    -> 502 + {"code":"upstream_error", ...}
```

## Layer responsibilities

| Layer | File(s) | One-line job |
|---|---|---|
| HTTP entry | `controller/SearchController` | Translate between HTTP and Java. No business logic. |
| Validation | `@Validated` + `@NotBlank` + `GlobalExceptionHandler` | Reject bad input at the boundary with a stable error contract. |
| Orchestration | `service/SearchService` | "What does a search mean?" — calls the client, runs the mapper. |
| Outbound HTTP | `service/NewsClient` | Talk to Guardian. Knows the URL shape and API key. |
| Translation | `service/GuardianArticleMapper` | Pure function: Guardian's wire format -> our `ArticleDto`. |
| Public DTOs | `dto/ArticleDto`, `dto/ApiError` | The shapes the API caller actually sees. Provider-agnostic. |
| Provider DTOs | `dto/GuardianRawResponse` | Mirror of Guardian's wire format. Lives only inside the backend. |
| Config | `config/NewsProperties` + `application.yml` | Type-safe binding of `app.news.*` YAML; API key from env var. |

## Why the boundaries are drawn here

- The frontend never sees Guardian's vocabulary (`webTitle`, `pillarId`).
  Provider-specific shapes are translated at the earliest possible point.
- The service never sees HTTP details (URLs, query strings, status codes).
  Same `SearchService.search(...)` could be called from a CLI or a scheduler
  unchanged once we have one.
- Errors from any layer end up as a consistent `ApiError` JSON body via
  one `@RestControllerAdvice`. There is exactly one place that decides
  status codes — easy to audit when the contract grows.

## What is intentionally NOT here yet

- Per-request correlation (MDC `requestId`) — adds in Week 2 once auth gives us `userId`.
- Caching — premature; LLM summaries on Day 24 will be the first cache.
- Retry / circuit breaker on the Guardian client — wait until rate limits bite.
- A second news provider — fan-out lives in `SearchService` when Day 13 adds Reddit + HN.

## Test coverage map (11 tests as of end-of-Week-1)

| Layer | Test class | Style | What it pins |
|---|---|---|---|
| App context | `NewLensApplicationTests` | `@SpringBootTest` | The app can boot at all. |
| Health endpoint | `HealthControllerTest` | `@WebMvcTest` | `/health` returns 200 + expected JSON. |
| Outbound HTTP | `NewsClientTest` | `@RestClientTest` + `MockRestServiceServer` | Builds the right Guardian URL; deserializes the response. |
| Mapper | `GuardianArticleMapperTest` | plain JUnit | Pure function: shape mapping + null safety + order preservation. |
| Controller | `SearchControllerTest` | `@WebMvcTest` + `@Import(GlobalExceptionHandler)` | Happy path; missing `q` 400; blank `q` 400; upstream failure 502. |
