# NewLens

`NewLens` is a 1-month full-stack learning project. The goal is to grow a junior engineer's depth in **Java**, **Python**, and **TypeScript** by shipping a real platform end-to-end, not a tutorial clone.

## What NewLens Does

A user searches a keyword. The system:

1. fetches news articles from **official news APIs** (NewsAPI, GNews, or Guardian)
2. pulls public reactions from **Reddit, Hacker News, and Bluesky**
3. summarizes the topic using an **LLM API**
4. scores **sentiment** locally with lightweight NLP
5. presents everything on an **interactive dashboard**

Users can register, log in, save searches, and track sentiment over time.

## Stack

Each language is placed where its ecosystem actually pays off — not just to tick boxes:

| Layer | Language | Tooling |
|---|---|---|
| Frontend | TypeScript | Next.js, React, Recharts, Tailwind |
| API gateway, auth, persistence, orchestration | Java | Spring Boot, Spring Security, JPA |
| NLP + LLM ingestion worker (introduced in Week 4) | Python | FastAPI, VADER (or HuggingFace), Anthropic/OpenAI SDK |
| Database | — | PostgreSQL (Redis optional cache) |

## Out of Scope for Month 1

Cut deliberately so the month stays shippable:

- Twitter / X (paid API, low learning ROI)
- Custom web scraping (legal + anti-bot risk; official APIs only for v1)
- Microservice meshes, distributed jobs, advanced auth (SSO/OAuth providers)
- Heavy ML models or fine-tuning

## Month 1 Outcome

By the end of the month the project has:

- a Spring Boot backend with users, auth, saved searches, and source fan-out
- a Python NLP/LLM worker callable from the backend
- a TypeScript dashboard with charts and live status updates
- one WebSocket-driven real-time feature
- a clean Git history with feature branches and small commits
- a Dockerized stack and a public deploy

## Repository Structure

- `backend/` — Java + Spring Boot service (gateway, auth, DB, orchestration)
- `frontend/` — TypeScript + Next.js dashboard
- `worker/` — Python + FastAPI NLP + LLM service (created in Week 4)
- `docs/30-day-roadmap.md` — day-by-day learning and build plan
- `docs/project-architecture.md` — target architecture and request flows
- `docs/git-workflow.md` — daily Git habits
- `docs/skill-checklist.md` — concepts to track
- `docs/setup-checklist.md` — local environment requirements
- `docs/daily-log.md` — short daily journal
- `docs/weekly-review.md` — end-of-week reflection

## Execution Order

Build in this order. Do not skip ahead until the current layer works end-to-end.

1. Backend foundations (HTTP, controllers, services, news API client)
2. Database, users, JWT auth, saved searches
3. Reddit + Hacker News integrations (still in Java HTTP clients)
4. TypeScript frontend wired to the backend
5. Python NLP worker for sentiment
6. LLM summary endpoint
7. WebSocket live updates
8. Tests + Docker + deploy

## Project Rules

- Small commits. Read your diff before each one.
- One feature branch per task.
- No advanced features before the current vertical slice works.
- Learn each concept deeply enough to explain it back to yourself.
