# Frontend (TypeScript + Next.js)

Month 1 frontend stack:

- TypeScript
- Next.js 14+ (App Router)
- React 18
- Tailwind CSS
- Recharts (or Chart.js) for sentiment-over-time

Start here in **Week 3**, after the Java backend is searchable end-to-end.

## Role In The System

The frontend talks **only to the Java backend** — never directly to the Python worker or to news / social APIs. The backend orchestrates everything.

## First Implementation Targets

- `/login` and `/register` pages (Day 16)
- `/search` page with keyword input + results list (Day 17)
- saved searches panel (Day 18)
- dashboard summary cards (Day 19)
- sentiment-over-time chart (Day 25)
- WebSocket live status bar (Day 26)

## Run Requirements

- Node.js 20+
- npm or pnpm

```bash
cd frontend
npm install
npm run dev
# http://localhost:3000
```

## Configuration

`.env.local` (do not commit):

```
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
NEXT_PUBLIC_WS_BASE_URL=ws://localhost:8080
```

## Conventions

- one folder per route under `app/`
- shared UI in `components/`
- API typings in `lib/api/types.ts` mirroring the backend DTOs
- prefer **server components** for read-only pages, **client components** only when state or events are needed
