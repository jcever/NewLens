# 30-Day Roadmap

This roadmap is for a junior engineer pursuing depth in **Java**, **Python**, and **TypeScript** through one shippable full-stack project. The pacing assumes **~3 hours/day, every day**.
## Week 1 — Java, Spring Boot, HTTP, Git foundations

### Day 1 — Repo + Spring Boot scaffold

- create Git repository, baseline commit
- create Spring Boot Maven project in `backend/`
- study: project structure, request/response lifecycle
- commit goal: initial backend scaffold

### Day 2 — Health endpoint + package layout

- add `GET /health`
- set up `controller`, `service`, `dto`, `config`, `exception`, `entity`, `repository` packages
- study: dependency injection, application startup
- commit goal: health endpoint and package layout

### Day 3 — News API client

- pick a provider (NewsAPI, GNews, or Guardian); store key in env var, read via `application.yml`
- create a `NewsClient` service using `RestClient` (or `WebClient`)
- study: HTTP clients, JSON mapping with Jackson
- commit goal: external news API client prototype
- note: tests for `NewsClient` + `SearchController` are backfilled at the start of Day 4 (the testing rule was introduced after Day 3 shipped)

### Day 4 — Article DTO + normalization

- **backfill from Day 3:** `NewsClientTest` with Spring's `MockRestServiceServer`; `SearchControllerTest` with `@WebMvcTest(SearchController.class)` + `@MockBean NewsClient`
- normalize provider-specific shapes into one internal `ArticleDto`
- handle blank or invalid input
- study: DTOs, validation (`@Valid`), exception handling
- test: unit test for the article mapper (Guardian raw → `ArticleDto`); `@NotBlank` on the query returns 400 with a clear error body
- commit goal: normalized search response + tests

### Day 5 — Search endpoint

- add `GET /api/search?q=...`
- test with curl and a REST client
- study: HTTP status codes, structured logging
- test: `@WebMvcTest` for the new endpoint — happy path, missing `q` → 400, provider error mapped to 502
- commit goal: working keyword search endpoint + tests

### Day 6 — Refactor + reflection

- review week 1 code; rename and tighten boundaries
- short note in your personal daily-log file on what clicked
- commit goal: cleanup and notes

### Day 7 — Rest / read your own diff history

- light review only

## Week 2 — Database, Auth, Multi-source ingestion (still Java)

### Day 8 — Schema + entities

- pick PostgreSQL (or H2 to start, migrate to PG later)
- create `User`, `SavedSearch`, `SearchRun`, `Article`, `Summary` entities
- study: tables, primary/foreign keys, JPA mapping
- commit goal: initial schema

### Day 9 — Repositories + service layer

- add `JpaRepository` for each entity
- service methods for save/fetch search history
- study: repository pattern, JPA lifecycle
- test: `@DataJpaTest` for one repository — verify save + find-by-user behavior against H2
- commit goal: persistence layer + tests

### Day 10 — Registration + password hashing

- `POST /api/auth/register`
- BCrypt for password storage
- study: authentication basics, password storage
- test: register response shape; assert the stored password is hashed, not equal to the plaintext input
- commit goal: user registration + tests

### Day 11 — Login + JWT

- `POST /api/auth/login` returns a JWT
- protect saved-searches endpoints with a security filter
- study: auth vs authorization, JWT vs sessions
- test: wrong password → 401; valid login returns a structurally valid JWT; protected endpoint without a token → 401
- commit goal: login + auth filter + tests

### Day 12 — Saved searches + history

- `POST /api/searches`, `GET /api/searches`
- user-scoped queries with ownership checks
- study: service boundaries
- test: user A cannot see or delete user B's saved searches
- commit goal: saved search history + tests

### Day 13 — Reddit + Hacker News clients

- add `RedditClient` (Reddit public JSON) and `HackerNewsClient` (Algolia HN API)
- normalize into a shared `SocialPostDto` alongside `ArticleDto`
- expand `/api/search` to fan out across news + Reddit + HN in parallel
- study: parallel HTTP, virtual threads or `CompletableFuture`, per-source error isolation
- test: each client in isolation with `MockRestServiceServer`; one source failing does not break the other sources (per-source error isolation)
- commit goal: multi-source ingestion + tests

### Day 14 — Validation, error handling, week review

- `@Valid` on inputs; global `@ControllerAdvice`
- document API routes
- test: `@ControllerAdvice` returns the expected JSON error-body shape for each handled exception type
- commit goal: backend hardening + week 2 review

## Week 3 — TypeScript frontend + full-stack wiring

### Day 15 — Next.js scaffold

- create `frontend/` with `create-next-app` (TypeScript, App Router, Tailwind)
- define routes: `/login`, `/register`, `/search`, `/dashboard`
- study: React components, TS props, Next.js routing
- commit goal: frontend scaffold

### Day 16 — Auth pages

- build login + register forms
- call backend; store JWT (httpOnly cookie preferred, localStorage acceptable for MVP)
- study: forms, controlled inputs, validation
- commit goal: auth UI

### Day 17 — Search page

- search input + results list
- handle loading and error states
- study: fetch lifecycle, async UI
- commit goal: search UI integration

### Day 18 — Saved searches panel

- render history; add save and delete actions
- study: list rendering, reusable components
- commit goal: saved searches UI

### Day 19 — Dashboard shell

- summary cards: article count, source breakdown, basic counts
- still showing raw data — sentiment + summary land in Week 4
- commit goal: dashboard shell

### Day 20 — Cross-cutting cleanup

- inspect network tab; tighten CORS, headers, cookies
- study: CORS, ports, token handling
- commit goal: frontend-backend connection cleanup

### Day 21 — Refactor + flow doc

- write a short doc tracing one search request from browser → Java → DB → response
- commit goal: week 3 review

## Week 4 — Python worker, NLP, LLM, WebSocket, deploy

### Day 22 — Python worker scaffold

- create `worker/` with FastAPI + `uv` (or Poetry)
- `GET /health` on the worker
- Java-side `WorkerClient` that calls it
- study: FastAPI basics, Pydantic models
- test: pytest for the worker's `/health`; Java-side `WorkerClient` test using `MockRestServiceServer`
- commit goal: Python worker scaffold + tests

### Day 23 — Sentiment endpoint

- `POST /sentiment` using VADER (`vaderSentiment`)
- batch scoring; return per-item polarity
- Java backend stores sentiment on `Article` rows
- study: NLP basics, batching
- test: pytest covers positive / negative / neutral text; empty batch returns empty list cleanly
- commit goal: sentiment pipeline + tests

### Day 24 — LLM summary endpoint

- `POST /summary` using Anthropic or OpenAI SDK
- prompt receives keyword + top N articles
- timeout, error fallback, cache by `(keyword, day)`
- study: prompt design, latency, failure handling, cost awareness
- test: pytest with mocked LLM client — verify cache hit on the second call; verify a timeout returns the fallback summary
- commit goal: AI summary feature + tests

### Day 25 — Sentiment chart on dashboard

- `GET /api/searches/{id}/timeline` returns sentiment-over-time data
- frontend renders a Recharts line chart
- commit goal: sentiment-over-time UI

### Day 26 — WebSocket progress

- Spring Boot WebSocket endpoint `/ws/search`
- emit `SEARCH_STARTED → FETCHING_ARTICLES → ANALYZING_SENTIMENT → SUMMARY_READY → SEARCH_COMPLETED`
- frontend subscribes and shows live status
- study: WebSocket lifecycle, reconnect basics
- test: connect a test client, run a search, assert the event sequence
- commit goal: real-time progress + tests

### Day 27 — Bluesky source (stretch) + UX polish

- add `BlueskyClient` if time allows (skip without guilt if behind)
- improve loading, empty, and error states
- commit goal: UX polish

### Day 28 — E2E test + coverage pass

- most slice / unit tests already exist from previous days
- add **one Playwright E2E** covering: register → log in → search → see results
- coverage pass: identify weakly-tested areas and add tests for whatever still feels fragile
- commit goal: E2E test + coverage gaps closed

### Day 29 — Docker + deploy

- `docker-compose.yml` for backend + worker + Postgres
- deploy frontend to Vercel; backend + worker + DB to Railway or Fly.io
- commit goal: dockerized + deployed

### Day 30 — Architecture write-up + demo

- update README architecture diagram
- record a short demo (or screenshots) for portfolio
- write what you now understand about each layer
- commit goal: month 1 milestone

## Rules For The Month

- one feature branch per task; small commits
- finish a vertical slice before adding another layer
- **every feature day ships with at least one test for the new behavior** — written the same day, while the *why* is still in your head
- no Twitter/X, no custom HTML scraping in v1
- learn each concept deeply enough to explain it back
- write a daily-log entry every day, even one line (file kept outside the repo)
