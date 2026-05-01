# Backend (Java + Spring Boot)

Month 1 backend stack:

- Java 21
- Spring Boot 3.3
- Spring Web
- Spring Validation
- Spring Data JPA (added in Week 2)
- Spring Security + JWT (added in Week 2)
- PostgreSQL (or H2 for early days)

## Role In The System

The backend is the **single entrypoint for the frontend**. It owns:

- user accounts and auth
- saved searches and history
- fan-out to news + Reddit + Hacker News + (optionally) Bluesky
- calls to the Python worker for sentiment + LLM summary
- WebSocket progress events

## Current Status

- Spring Boot Maven project scaffolded
- application entrypoint
- `GET /health` endpoint
- starter package layout: `controller`, `service`, `repository`, `entity`, `dto`, `config`, `exception`

## Run Requirements

- JDK 21
- Maven 3.9+ (bundled `./mvnw` works too)

```bash
cd backend
mvn spring-boot:run
# or
./mvnw spring-boot:run
```

Health check:

```bash
curl http://localhost:8080/health
```

## Configuration

Environment variables (set in shell, `.env`, or IntelliJ run config — never commit):

- `NEWS_API_KEY` — NewsAPI / GNews / Guardian key
- `JWT_SECRET` — random string, at least 32 chars
- `WORKER_BASE_URL` — e.g. `http://localhost:8000`
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` — Postgres connection (Week 2 onward)

## Endpoints (target by end of month)

| Method | Path | Purpose |
|---|---|---|
| GET | `/health` | liveness |
| POST | `/api/auth/register` | create user |
| POST | `/api/auth/login` | get JWT |
| POST | `/api/searches` | save a search keyword |
| GET | `/api/searches` | list user's saved searches |
| POST | `/api/search` | run a one-off search; returns `runId` |
| GET | `/api/search/{runId}` | full results for one run |
| GET | `/api/search/{runId}/timeline` | sentiment-over-time |
| WS | `/ws/search` | live progress events |

## Next Backend Milestones

1. `GET /api/search?q=...` (Week 1)
2. News API client + `ArticleDto` normalization (Week 1)
3. Database, users, JWT auth (Week 2)
4. Reddit + Hacker News clients with parallel fan-out (Week 2)
5. Worker integration for sentiment + summary (Week 4)
6. WebSocket progress events (Week 4)
