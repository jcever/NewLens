# Git Workflow

Use Git every day as part of engineering, not as a separate topic. By the end of the month, small commits and clear messages should feel automatic.

## Default — Commit Directly To `main`

For **solo work**, just commit on `main`:

1. make one small working change
2. inspect `git diff`
3. run the code or test what changed
4. `git add <specific files>` and `git commit -m "..."`
5. `git push origin main`
6. repeat

No branch ceremony required. Branches exist for team workflows (PRs, code review, CI per branch). Solo, you don't need them by default.

## When To Use A Branch Anyway

Branches are a tool — reach for one only when you actually need isolation:

- a **risky experiment** you might throw away (`experiment/swap-postgres-for-mongo`)
- a **multi-day refactor** where main needs to keep working
- work you want to **stash and come back to later** without polluting main
- a **public PR** to a team repo or open-source project

If none of those apply, commit on `main` and move on.

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
git status
git diff
git diff --staged
git add path/to/file.java path/to/test.java   # prefer specific files over `git add .`
git commit -m "feat(news): integrate news search service"
git push origin main
git log --oneline --decorate --graph
git restore --staged path/to/file       # unstage
git restore path/to/file                 # discard local change (careful)
```

## When You Do Use A Branch

Reference for the rare case you need one:

```bash
git checkout -b experiment/swap-db
# ... commits ...
git switch main
git merge --no-ff experiment/swap-db    # --no-ff keeps the branching visible in history
git branch -d experiment/swap-db
git push origin main
```

## Rules

- do not make huge commits when smaller ones would work
- read your diff before each commit
- write commit messages that describe the change, not your mood
- never `git add .` blindly — at minimum scan `git status` first
- one logical change per commit (a refactor and a feature do not belong together)
