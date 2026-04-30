# AGENTS.md

## Cursor Cloud specific instructions

This is a full-stack exam preparation system ("Система подготовки к ЦТ/ЦЭ") with adaptive testing.

### Services

| Service | Port | How to run |
|---------|------|------------|
| Backend (Spring Boot) | 8080 | `cd backend && ./gradlew bootRun` |
| Frontend (Vite + React) | 5173 | `cd frontend && pnpm dev` |
| PostgreSQL | 5432 | Must be running before backend starts |
| Redis | 6379 | Optional (in-memory cache fallback used when unavailable) |

### Prerequisites

- **Java 21** (OpenJDK) — pre-installed
- **Node 20** via nvm — activate with `export NVM_DIR="$HOME/.nvm" && . "$NVM_DIR/nvm.sh" && nvm use 20`
- **Gradle 8.7** — available via `./gradlew` wrapper in `backend/`
- **pnpm** — installed globally via npm

### Database setup

PostgreSQL must be running with database `exam_prep`, user `exam_user`, password `exam_pass`. Start PostgreSQL:
```
pg_ctlcluster 16 main start
```
Create DB (if not exists):
```
su - postgres -c "psql -c \"CREATE USER exam_user WITH PASSWORD 'exam_pass' CREATEDB;\""
su - postgres -c "psql -c \"CREATE DATABASE exam_prep OWNER exam_user;\""
```
Flyway migrations run automatically on backend startup.

### Testing

- **Backend unit + integration tests**: `cd backend && ./gradlew test` (uses H2 in-memory DB, no PostgreSQL needed)
- **Frontend lint**: `cd frontend && pnpm lint`
- **Frontend build**: `cd frontend && pnpm build`

### Key gotchas

- The backend does NOT use `server.servlet.context-path`. All API paths include `/api/v1/...` directly in the controller `@RequestMapping`.
- The Vite proxy (`/api` → `localhost:8080`) handles frontend-to-backend communication in development.
- The OAuth2 client dependency is commented out in `build.gradle`. Uncomment and configure `GITHUB_CLIENT_ID` / `GITHUB_CLIENT_SECRET` env vars when enabling GitHub OAuth.
- Redis is optional; a `ConcurrentMapCacheManager` is used as fallback when Redis is not available (no `redis` Spring profile).
- The test attempt response does NOT include questions inline. Use `GET /api/v1/questions?subjectId=X` to fetch questions for answering.
- PostgreSQL cluster is managed via `pg_ctlcluster 16 main start` (not `systemctl`). The DB/user may already exist from a previous run; the create commands will show harmless errors in that case.
- nvm is installed at `$HOME/.nvm` (root user in Cloud VM). The `.bashrc` auto-sources it on shell init.
