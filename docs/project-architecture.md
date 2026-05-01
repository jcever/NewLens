# Project Architecture

This is a 1-month MVP. Decisions are biased toward **shipping**, **learning depth**, and **honest scope**. Anything that pulls focus without teaching one of the three target languages or a core full-stack concept is deferred.

## High-Level Diagram

```
Browser (Next.js, TS)
    │  HTTPS
    ▼
Spring Boot API (Java) ────► PostgreSQL
    │  REST                       ▲
    │                             │
    ▼                             │
Python Worker (FastAPI)  ─────────┘
    │
    ├── NewsAPI / GNews / Guardian
    ├── Reddit (public JSON)
    ├── Hacker News (Algolia API)
    ├── Bluesky AT Protocol (stretch)
    └── LLM API (Anthropic or OpenAI)
```

The Java backend is the **single source of truth for client requests**. The Python worker is a focused NLP + LLM service the backend calls. The frontend never talks to Python or to external APIs directly.

## Components

### Frontend — TypeScript / Next.js

- Login + register pages
- Search bar + results dashboard
- Saved searches panel
- Sentiment-over-time chart (Recharts)
- Live status bar via WebSocket

### Backend — Java / Spring Boot

Packages: `controller`, `service`, `repository`, `entity`, `dto`, `config`, `exception`.

Responsibilities:

- Spring Web for REST
- Spring Validation for input
- Spring Data JPA for persistence
- Spring Security + JWT for auth
- HTTP clients (`RestClient` / `WebClient`) for NewsAPI, Reddit, Hacker News, (Bluesky)
- WebSocket endpoint (`/ws/search`) for progress events
- Calls Python worker over REST for `/sentiment` and `/summary`

### Worker — Python / FastAPI (introduced in Week 4)

- `POST /sentiment` — input: list of `{title, description}`. Output: per-item polarity using **VADER** (start) or HuggingFace pipeline.
- `POST /summary` — input: keyword + top articles. Output: LLM-generated topic summary. Includes timeout + fallback.
- Stateless. No DB. Not exposed to the public internet.

### Database — PostgreSQL

Tables:

- `users(id, email, password_hash, created_at)`
- `saved_searches(id, user_id, keyword, created_at)`
- `search_runs(id, saved_search_id, started_at, completed_at, status)`
- `articles(id, search_run_id, source, url, title, description, sentiment_score, published_at)`
- `summaries(id, search_run_id, content, model, created_at)`

Relationships:

- one user → many saved searches
- one saved search → many search runs (each is a snapshot in time)
- one search run → many articles, one summary

### Cache — Redis (optional for v1)

- Cache LLM summaries by `(keyword, day)` to avoid repeat cost
- Skip in early weeks; add only if needed

## Request Flow — Search

1. User submits keyword in the frontend.
2. Frontend `POST /api/search { keyword }` → Spring Boot.
3. Backend validates input, creates `search_run` row, returns `runId` immediately.
4. Frontend opens WebSocket; backend emits `SEARCH_STARTED`.
5. Backend fans out to news + Reddit + Hacker News (and Bluesky, stretch) in parallel; emits `FETCHING_ARTICLES`.
6. Backend persists raw articles, batches them, calls Python worker `/sentiment`; emits `ANALYZING_SENTIMENT`.
7. Backend calls Python worker `/summary` with top articles; emits `SUMMARY_READY`.
8. Backend marks `search_run` completed; emits `SEARCH_COMPLETED`.
9. Frontend reads the full result via `GET /api/search/{runId}`.

## Source Selection

| Source | API | Why include |
|---|---|---|
| News | NewsAPI / GNews / Guardian | Official, free tier, JSON, no scraping |
| Reddit | Reddit public JSON endpoints | Free, no key for read-only public listings |
| Hacker News | Algolia HN API | Free, no key, fast |
| Bluesky | AT Protocol public XRPC | Free, modern; included as stretch in Week 4 |
| Twitter / X | — | **Excluded.** Paid (~$100/mo basic), strict limits, low learning ROI |

## Auth

- Local email + password
- Passwords hashed with BCrypt
- JWT issued on login, sent in `Authorization: Bearer <token>` header
- Saved-search and history endpoints require a valid token

## WebSocket Events

Single endpoint: `/ws/search`. Server pushes:

- `SEARCH_STARTED { runId }`
- `FETCHING_ARTICLES { source }`
- `ANALYZING_SENTIMENT { processed, total }`
- `SUMMARY_READY { runId }`
- `SEARCH_COMPLETED { runId }`

## Deferred (Not Month 1)

- Twitter / X
- Custom HTML scraping
- OAuth / SSO providers
- Distributed job queue (Celery, RabbitMQ, etc.)
- Multi-tenant access control
- Production monitoring stack
- Fine-tuned models or vector search

The goal of the month is **engineering depth in three languages**, not maximum feature count.
