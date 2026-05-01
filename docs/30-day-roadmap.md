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

### Day 4 — Article DTO + normalization

- normalize provider-specific shapes into one internal `ArticleDto`
- handle blank or invalid input
- study: DTOs, validation (`@Valid`), exception handling
- commit goal: normalized search response

### Day 5 — Search endpoint

- add `GET /api/search?q=...`
- test with curl and a REST client
- study: HTTP status codes, structured logging
- commit goal: working keyword search endpoint

### Day 6 — Refactor + reflection

- review week 1 code; rename and tighten boundaries
- short note in `docs/daily-log.md` on what clicked
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
- commit goal: persistence layer

### Day 10 — Registration + password hashing

- `POST /api/auth/register`
- BCrypt for password storage
- study: authentication basics, password storage
- commit goal: user registration

### Day 11 — Login + JWT

- `POST /api/auth/login` returns a JWT
- protect saved-searches endpoints with a security filter
- study: auth vs authorization, JWT vs sessions
- commit goal: login + auth filter

### Day 12 — Saved searches + history

- `POST /api/searches`, `GET /api/searches`
- user-scoped queries with ownership checks
- study: service boundaries
- commit goal: saved search history

### Day 13 — Reddit + Hacker News clients

- add `RedditClient` (Reddit public JSON) and `HackerNewsClient` (Algolia HN API)
- normalize into a shared `SocialPostDto` alongside `ArticleDto`
- expand `/api/search` to fan out across news + Reddit + HN in parallel
- study: parallel HTTP, virtual threads or `CompletableFuture`, per-source error isolation
- commit goal: multi-source ingestion

### Day 14 — Validation, error handling, week review

- `@Valid` on inputs; global `@ControllerAdvice`
- document API routes
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
- commit goal: Python worker scaffold

### Day 23 — Sentiment endpoint

- `POST /sentiment` using VADER (`vaderSentiment`)
- batch scoring; return per-item polarity
- Java backend stores sentiment on `Article` rows
- study: NLP basics, batching
- commit goal: sentiment pipeline

### Day 24 — LLM summary endpoint

- `POST /summary` using Anthropic or OpenAI SDK
- prompt receives keyword + top N articles
- timeout, error fallback, cache by `(keyword, day)`
- study: prompt design, latency, failure handling, cost awareness
- commit goal: AI summary feature

### Day 25 — Sentiment chart on dashboard

- `GET /api/searches/{id}/timeline` returns sentiment-over-time data
- frontend renders a Recharts line chart
- commit goal: sentiment-over-time UI

### Day 26 — WebSocket progress

- Spring Boot WebSocket endpoint `/ws/search`
- emit `SEARCH_STARTED → FETCHING_ARTICLES → ANALYZING_SENTIMENT → SUMMARY_READY → SEARCH_COMPLETED`
- frontend subscribes and shows live status
- study: WebSocket lifecycle, reconnect basics
- commit goal: real-time progress

### Day 27 — Bluesky source (stretch) + UX polish

- add `BlueskyClient` if time allows (skip without guilt if behind)
- improve loading, empty, and error states
- commit goal: UX polish

### Day 28 — Tests

- JUnit tests for one Java service + one controller
- pytest for the Python worker
- one Playwright E2E if time allows
- commit goal: initial tests

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
- no Twitter/X, no custom HTML scraping in v1
- learn each concept deeply enough to explain it back
- write a `daily-log.md` entry every day, even one line
