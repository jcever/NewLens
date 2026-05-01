# Git Workflow

Use Git every day as part of engineering, not as a separate topic. By the end of the month, branching and small commits should feel automatic.

## Basic Daily Flow

1. create or switch to a feature branch
2. make one small working change
3. inspect `git diff`
4. run the code or test what changed
5. commit with a clear message
6. repeat

## Recommended Branch Names

Backend (Java):

- `feature/health-endpoint`
- `feature/news-search-api`
- `feature/reddit-client`
- `feature/hn-client`
- `feature/user-registration`
- `feature/jwt-auth`
- `feature/search-history`
- `feature/websocket-progress`

Worker (Python):

- `feature/worker-scaffold`
- `feature/sentiment-endpoint`
- `feature/llm-summary`

Frontend (TypeScript):

- `feature/frontend-scaffold`
- `feature/auth-pages`
- `feature/search-page`
- `feature/saved-searches-ui`
- `feature/sentiment-chart`

Cross-cutting:

- `fix/login-validation`
- `refactor/search-service`
- `chore/docker-compose`
- `docs/architecture-diagram`

## Recommended Commit Style (Conventional Commits)

- `feat: add health endpoint`
- `feat(news): integrate news search service`
- `feat(reddit): fan out search to reddit json`
- `feat(worker): add VADER sentiment endpoint`
- `feat(frontend): wire search page to backend`
- `fix: reject blank keyword queries`
- `refactor: split article mapping into dto mapper`
- `test: add search service unit tests`
- `chore: add docker-compose for backend + worker + db`
- `docs: add month 1 roadmap`

## Commands To Practice Often

```bash
git init
git status
git checkout -b feature/news-search-api
git add path/to/file.java path/to/test.java   # prefer specific files over `git add .`
git commit -m "feat(news): integrate news search service"
git diff
git diff --staged
git log --oneline --decorate --graph
git restore --staged path/to/file       # unstage
git restore path/to/file                 # discard local change (careful)
git switch main && git merge --no-ff feature/...
```

## What Good Git Habits Teach

- change isolation
- clean thinking
- review discipline
- safe experimentation
- collaboration readiness — every PR you ever open will be a small commit history

## Rules

- do not make huge commits when smaller ones would work
- read your diff before each commit
- one feature branch per task — do not pile work on `main`
- write commit messages that describe the change, not your mood
- never `git add .` blindly — at minimum scan `git status` first
