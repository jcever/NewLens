# Daily Log

Use this every day. Keep entries short and concrete.

## Template

### Date: YYYY-MM-DD

- Focus:
- What I built:
- What I studied:
- What broke:
- How I fixed it:
- Git branch:
- Commit(s):
- Next step:

## Entries

### Date: 2026-04-20

- Focus: set up `NewLens` planning workspace
- What I built: project folder, roadmap docs, architecture notes, Git workflow guide
- What I studied: how the month will progress from Java backend to full-stack app
- What broke: nothing critical, only an interrupted earlier scaffold
- How I fixed it: reused the partial folder and reorganized it into `NewLens`
- Git branch: `main`
- Commit(s): not committed yet
- Next step: install tools and scaffold the Spring Boot backend

### Date: 2026-04-29

- Focus: finalize 1-month plan, lock the stack, refresh all docs
- What I built: nothing yet (plan-finalization day)
- What I studied: realistic source choices (dropped Twitter/X due to cost; chose Reddit + Hacker News + Bluesky); decided on a three-language split where each language plays to its ecosystem (Java for gateway/auth/DB, Python for NLP/LLM in Week 4, TypeScript for frontend); committed to ~3 hours/day
- What broke: nothing
- How I fixed it: n/a
- Git branch: `main`
- Commit(s): docs refresh pending
- Next step: commit the updated docs, then move to Day 1 of Week 1 — confirm Spring Boot scaffold runs and walk through the `/health` endpoint end-to-end

### Date: 2026-05-01 (Day 1)

- Focus: Day 1 — repo + Spring Boot scaffold; first baseline commits and push to GitHub
- What I built: nothing new code-wise; verified the existing Spring Boot scaffold runs (`mvn spring-boot:run`) and `GET /health` returns `{"status":"ok","service":"newlens-backend","timestamp":...}`. Made two baseline commits and pushed to `origin/main` on GitHub
- What I studied: what `mvn` is and how it relates to `pom.xml`; the three sub-annotations of `@SpringBootApplication`; package-layer responsibilities (controller → service → repository); request/response lifecycle traced through `/health`; why `HealthResponse` is a `record`; how `MappingJackson2HttpMessageConverter` gets onto the classpath via `spring-boot-starter-web`. Notes saved in `docs/study_notes.md`
- What broke:
  - `mvn` was not installed on macOS (`mvn not found`)
  - Git refused to commit due to missing `user.name` / `user.email`
  - First `git push` failed because no GitHub credential helper was configured
- How I fixed it:
  - `brew install maven`
  - `git config --global user.name "jcever"` and matching email
  - `brew install gh`, then `gh auth login` (HTTPS, web browser flow), then `gh auth setup-git`
- Git branch: `main`
- Commit(s):
  - `7b57156` chore: add planning docs and project architecture
  - `3f41c29` feat(backend): scaffold Spring Boot project with /health endpoint
  - pushed: `git push -u origin main`
- Next step: Day 2 — add a small JUnit/MockMvc test for `/health`, then build the news API client (Day 3 starts the news provider integration). Practice creating a feature branch (`feature/health-endpoint-test`) instead of committing on `main`
