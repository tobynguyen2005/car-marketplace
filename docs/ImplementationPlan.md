I have generated the 24-phase **Implementation Plan** for the **Car Marketplace Web Application**, grounded in `CarMarketplaceWebApp.md`, the Architecture Analysis, the Database Design, and `API.md`.

The complete implementation plan has been generated and published as **`ImplementationPlan.md`**.

---

### Implementation Sequencing & Key Phases Summary

#### **Backend Foundation & Core E-Commerce Stack (Phases 1–12)**

1. **Phase 1: Project Initialization & Backend Foundation**: Configures Spring Boot 3.x, Maven dependencies, Java 21 properties, standardized response envelopes (`ApiResponse`, `PageResponse`, `ErrorResponse`), and `@ControllerAdvice` global exception handling.
2. **Phase 2: PostgreSQL & Flyway Migrations**: Executes Flyway SQL scripts `V1` through `V8` sequentially on a clean PostgreSQL instance.
3. **Phase 3: JPA Entities & Repositories**: Maps PostgreSQL tables to Java JPA entities (with `@Version` for optimistic locking and `DECIMAL(12,2)` for financial fields) and sets up Spring Data JPA repositories.
4. **Phase 4: Authentication & Authorization**: Integrates Spring Security, BCrypt password hashing, short-lived JWT access tokens, `HttpOnly` refresh token cookies, Google OAuth2 auto-provisioning, and RBAC.
5. **Phase 5–6: Vehicle, Brand & Accessory Catalog Modules**: Implements multi-param JPA Specifications for vehicle/accessory search, filtering, pagination, and admin management.
6. **Phase 7–9: Cart, Transactional Checkout & Stock Concurrency**:
   - Implements database-backed carts enforcing `UNIQUE(cart_id, accessory_id)`.
   - Executes atomic `@Transactional` checkouts that query `accessories` using **Pessimistic Write Locks (`SELECT ... FOR UPDATE`)**, deduct stock, record snapshot line items, and clear the cart.
   - Enforces concurrency safety where simultaneous checkout attempts for depleted inventory cleanly roll back and return **HTTP 409 Conflict**.
7. **Phase 10: Rate Limiting**: Applies Bucket4j IP-based rate limiting to `/auth/login`, `/contact`, and `/service-bookings`, and User ID-based limits to `/checkout`, returning **HTTP 429 Too Many Requests** on limit breach.
8. **Phase 11–12: Services, Bookings, Blog & Contact**: Implements booking appointments, published blog articles, and customer contact inquiry forms.

#### **Frontend SPA & Editorial Visual Identity (Phases 13–19)**

9. **Phase 13–14: React SPA Foundation & Auth UI**: Configures React 18+, Vite, TypeScript, TailwindCSS with Hallmark design tokens, Axios interceptors for automatic token refresh retries, Zustand auth store, and route guards.
10. **Phase 15–18: Catalog, Cart, Checkout, Order & Content UI**:
    - Integrates TanStack Query for server state management.
    - Implements vehicle/accessory browsing, search, and detail galleries.
    - Implements checkout forms with real-time stock conflict error banners (_"Some products are no longer available in the requested quantity. Please review your cart."_).
    - Implements customer profile and order history with snapshot pricing receipts.
11. **Phase 19: Admin Dashboard**: Build data tables, filter controls, inline stock adjustments, and order status fulfillment transitions for staff and administrators.

#### **Containerization, Audits & Verification (Phases 20–24)**

12. **Phase 20: Docker & Deployment**: Constructs Multi-Stage Dockerfiles for Java 21 backend and Nginx React frontend, orchestrating everything via `docker-compose.yml` with PostgreSQL 16 and Redis services.
13. **Phase 21–24: Testing, Security Audit, Performance Audit & Acceptance**:
    - Executes automated unit tests, integration tests, and multi-threaded concurrency tests verifying zero overselling under race conditions.
    - Conducts OWASP security scans, JPA N+1 query optimizations, HikariCP connection pool tuning, and end-to-end acceptance testing.

---

💡 _All documentation files (`Architecture.md`, `DatabaseDesign.md`, `API.md` and `ImplementationPlan.md`) are synchronized in your workspace and ready for development._
