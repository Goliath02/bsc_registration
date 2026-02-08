# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Registration web application for the 1.BSC (Erste Badischer Schwimmclub) swim club. Users fill out a multi-step registration form; the backend sends confirmation emails to club contacts and course-responsible trainers.

**Live**: https://registration.erster-bsc-pforzheim.de/

## Build & Run Commands

### Frontend (from `frontend/`)
```bash
npm run dev       # Dev server on http://localhost:3000 (proxies /api → localhost:8080)
npm run build     # Production build to dist/
npm run preview   # Preview production build
```

### Backend (from `bsc-registration-server/`)
```bash
mvn spring-boot:run              # Run on http://localhost:8080
mvn clean package                # Build JAR
mvn -B package -DskipTests      # Build without tests (used in CI)
mvn test                         # Run all tests
mvn -Dtest=ClassName test        # Run a single test class
mvn -Dtest=ClassName#methodName test  # Run a single test method
```

### Docker (full stack)
```bash
docker compose --env-file dev.env up    # Starts api + nginx + postgres
```

## Architecture

**Monorepo** with two main modules:

- `frontend/` — Vue 3 SPA (Vite, PrimeVue, Tailwind CSS, Pinia, Vue Router)
- `bsc-registration-server/` — Spring Boot 3.4.4 API (Java 21, Maven)

The frontend is built and bundled into the backend's JAR via a multi-stage Docker build. In development, Vite proxies `/api` requests to the Spring Boot backend.

### Backend Structure (feature-based)

Source root: `bsc-registration-server/src/main/java/bsc/tools/registration/`

- `feature/auth/` — JWT authentication, rate limiting (Bucket4j), sign-up keys
- `feature/registration/` — Member registration form processing, `BscMember` entity
- `feature/course/` — Course CRUD, training units, holiday management
- `feature/mail/` — Email sending via Spring Mail (IONOS SMTP)
- `feature/trainer/` — Trainer management
- `feature/trainingPlace/` — Training venue management
- `config/` — Security (JWT + CORS), mail, dynamic config loading (`bscConf.json`)
- `common/` — Info endpoint
- `utils/` — CSV, date, form, dev utilities

### Frontend Structure

Source root: `frontend/src/`

- `components/AdminPanel/` — Dashboard, course/member/trainer management, settings
- `components/BasicRegistration/` — Registration form inputs
- `components/FinancesRegistration/` — IBAN, SEPA, data privacy
- `components/CourseManager/` — Course selection
- `components/Login/` — Auth UI
- `pages/` — Main, confirmation, success, data protection pages
- `stores/` — Pinia stores (MemberRegistrationStore.ts, RegistrationStore.js)
- `services/` — API clients (AuthService.ts, InfoService.ts, DateService.ts)

### Key Routes (frontend)
`/` → registration form, `/kontodaten` → banking data, `/zusammenfassung` → confirmation, `/datenschutz` → data protection, `/erfolg` → success

## Infrastructure

- **Database**: PostgreSQL 17 (auto-schema via Hibernate DDL-auto: update)
- **Reverse proxy**: Nginx 1.27.2
- **Deployment**: Docker Compose on VPS, images pushed to Docker Hub (`goliath02/bsc_registration`)
- **CI/CD**: GitHub Actions — `verify-build.yml` (PRs), `build-and-deploy.yml` (master push)

## Environment Variables

Defined in `dev.env` / `prod.env`: `POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DATABASE_URL`, `JWT_SECRET_KEY`, `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `ADMIN_EMAIL`, `ADMIN_PASSWORD`

## Commit Convention

Prefix commits with a semantic tag: `[+]` additions, `[*]` refactors/improvements, `[-]` removals.

## Key Config Files

- `bsc-registration-server/src/main/resources/application.properties` — DB, JWT, mail, logging config
- `bsc-registration-server/src/main/resources/bscConf.json` — Course definitions, registration email recipients, price list
- `frontend/vite.config.js` — Dev proxy, Tailwind/PrimeVue plugins, `@` alias → `./src`
- `config/nginx.conf` — Production reverse proxy rules