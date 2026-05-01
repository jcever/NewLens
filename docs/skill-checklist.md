# Skill Checklist

Use this file to track what you understand well, what you can use with help, and what still feels weak. Status conventions: `[ ]` not yet, `[~]` can use with help, `[x]` can explain back to a peer.

## Java

- [ ] classes and objects
- [ ] interfaces and abstraction
- [ ] generics
- [ ] collections
- [ ] exceptions (checked vs unchecked)
- [ ] streams basics
- [ ] virtual threads / `CompletableFuture` (parallel HTTP)
- [ ] DTOs and records
- [ ] service and repository layers
- [ ] testing with JUnit + Mockito

## Spring Boot

- [ ] dependency injection
- [ ] `@RestController`, request mapping
- [ ] `@Service`, `@Repository`
- [ ] Spring Data JPA
- [ ] Spring Validation (`@Valid`)
- [ ] Spring Security + JWT
- [ ] `RestClient` / `WebClient` for outbound calls
- [ ] `@ControllerAdvice` for error handling
- [ ] WebSocket (`@MessageMapping` or raw handler)

## Python

- [ ] modules, packages, virtual envs (`uv` or `venv`)
- [ ] type hints + `mypy` mindset
- [ ] FastAPI route + Pydantic model
- [ ] async / await basics
- [ ] testing with pytest
- [ ] HTTP clients (`httpx`)

## NLP & LLM

- [ ] tokenization concepts
- [ ] sentiment scoring (VADER)
- [ ] LLM API call (Anthropic or OpenAI SDK)
- [ ] prompt design for summarization
- [ ] timeouts, retries, fallbacks for LLM calls
- [ ] cost awareness (token counts, caching responses)

## TypeScript & Frontend

- [ ] TypeScript interfaces and generics
- [ ] React components and hooks
- [ ] Next.js App Router (server vs client components)
- [ ] forms and controlled inputs
- [ ] fetch lifecycle, loading and error states
- [ ] state ownership (where does state live?)
- [ ] charts (Recharts or Chart.js)
- [ ] Tailwind basics

## Database

- [ ] tables and relationships
- [ ] primary and foreign keys
- [ ] joins
- [ ] indexing basics
- [ ] N+1 query awareness
- [ ] migrations (Flyway or Liquibase)

## Networking & HTTP

- [ ] DNS, ports
- [ ] TCP vs UDP basics
- [ ] request/response lifecycle
- [ ] headers, cookies
- [ ] JWT structure
- [ ] CORS and preflight
- [ ] HTTPS basics

## WebSocket

- [ ] handshake (HTTP upgrade)
- [ ] persistent connection lifecycle
- [ ] event design
- [ ] reconnect strategies
- [ ] WebSocket vs polling vs SSE — when to choose

## Containers & Deploy

- [ ] Docker image vs container
- [ ] writing a Dockerfile
- [ ] `docker-compose` for multi-service local dev
- [ ] env vars and secrets in deploy
- [ ] deploying frontend (Vercel)
- [ ] deploying backend (Railway / Fly.io / Render)

## Engineering Habits

- [ ] use Git branches consistently
- [ ] read diffs before commits
- [ ] write small, focused commits
- [ ] descriptive commit messages
- [ ] debug with logs, breakpoints, and the network tab
- [ ] write a short note after finishing each feature
- [ ] refactor only after the feature works
- [ ] read others' code (compare your patterns to popular Spring / Next.js repos)
