# Setup Checklist

Use this to verify your local environment before each phase. Don't try to install everything on day 1 — install per phase as the roadmap reaches it, so you don't lose hours yak-shaving up front.

## Phase 1 — Java backend (Week 1)

- [x] Git installed
- [x] Homebrew installed
- [x] JDK 21 installed (`java --version`)
- [x] Maven 3.9+ installed (`mvn --version`) — or use bundled `./mvnw`
- [x] IntelliJ IDEA installed
- [ ] Spring Boot project running in `backend/`
- [ ] API testing tool installed (curl, Postman, Bruno, or HTTPie)
- [ ] News API account + key (NewsAPI, GNews, or Guardian)

## Phase 2 — Database + auth (Week 2)

- [ ] PostgreSQL 16 installed (or running in Docker) — H2 acceptable to start
- [ ] DB GUI installed (TablePlus, DBeaver, or just `psql`)
- [ ] Reddit access — public JSON works for read-only with no key
- [ ] Hacker News — Algolia HN API needs no key

## Phase 3 — Frontend (Week 3)

- [x] Node.js 20+ installed (`node --version`)
- [x] npm or pnpm installed
- [x] VS Code installed
- [ ] Next.js app scaffolded in `frontend/`
- [ ] Comfortable with browser DevTools (Network tab, Application tab)

## Phase 4 — Python worker + LLM + deploy (Week 4)

- [ ] Python 3.11+ installed (`python3 --version`)
- [ ] `uv` installed (`brew install uv`) — or Poetry
- [ ] Docker Desktop installed and running
- [ ] Anthropic API key (or OpenAI key)
- [ ] Vercel account
- [ ] Railway or Fly.io account
- [ ] Bluesky account (optional, for Day 27 stretch)

## Workflow

- [x] repository initialized
- [ ] first baseline commit created
- [x] `daily-log.md` in use
- [x] `weekly-review.md` in use
- [ ] feature branch naming in use
- [ ] `.env` files in `.gitignore` and never committed
