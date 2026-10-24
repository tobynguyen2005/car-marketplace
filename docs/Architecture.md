Here is the complete, source-grounded system architecture for the **Car Marketplace Web Application**, designed strictly according to the specifications in `CarMarketplaceWebApp.md`.

The complete document has been generated and published as **`Architecture.md`**.

---

### 1. High-Level System Architecture

The application is structured as a **decoupled, multi-tier client-server architecture**:

* **Presentation Tier**: A Single Page Application (SPA) built with **React 18+**, **TypeScript**, **Vite**, and **TailwindCSS**.
* **Application Tier**: A stateless RESTful backend built with **Java 21** and **Spring Boot 3.x**, enforcing business rules, authorization, transaction control, and rate limiting.
* **Persistence Tier**: A relational **PostgreSQL 16** database managed via **Flyway schema migrations**.
* **Caching & Rate Limiting Subsystem**: An in-memory or **Redis-backed Bucket4j** store for distributed rate limiting across backend nodes.
* **Deployment Containerization**: Docker Compose orchestrating `db`, `redis`, `backend`, and `frontend` (Nginx) containers .

#### Rationale
Decoupling the frontend SPA from the REST API guarantees that the **backend remains the sole source of truth** for critical business logic (pricing, inventory, role permissions, and financial totals). The client cannot tamper with financial or stock calculations.

---

### 2. Backend Architecture

The backend follows a **layered, domain-driven REST architecture** adhering to SOLID principles:

1. **Controller Layer (`@RestController`)**: Handles HTTP request parsing, path mapping, request payload validation (`Jakarta Validation`), and returns DTO responses wrapped in standardized HTTP envelopes.
2. **Security Layer (`Spring Security`)**: Stateless security filter chain enforcing CORS, CSRF rules, JWT validation, Google OAuth2 handshake, and method-level access control (`@PreAuthorize`).
3. **Rate Limiting Filter (`Bucket4j Filter`)**: Intercepts requests prior to business logic execution to evaluate IP-based or User-based rate buckets.
4. **Service Layer (`@Service`, `@Transactional`)**: Contains all e-commerce business rules, pricing queries, inventory deduction logic, and transaction boundaries.
5. **Repository Layer (`Spring Data JPA / Hibernate`)**: Manages object-relational mapping, custom SQL/JPQL queries, pessimistic row locking (`SELECT ... FOR UPDATE`), and Flyway migrations.
6. **Domain & DTO Mapping Layer**: Standard JPA Entities managed strictly internally; **MapStruct** maps between Entities and Request/Response DTOs. **JPA entities are never exposed directly via REST endpoints**.
7. **Global Exception Handler (`@ControllerAdvice`)**: Catches all validation errors, authentication failures, stock conflicts, and resource missing exceptions, translating them into uniform JSON error payloads.

#### Rationale
Layered separation ensures clean boundaries, high testability, and maintainability. Using DTOs prevents over-posting/mass-assignment vulnerabilities and isolates database entity changes from client API contracts.

---

### 3. Frontend Architecture

The React SPA follows a modular feature-based architecture styled according to a **Hallmark-style visual identity** (warm off-white surfaces, charcoal typography, structured editorial layouts, avoiding "AI slop"):

* **Routing & Guards**: **React Router DOM** with custom higher-order route guard components (`ProtectedRoute`, `RoleGuard`) checking JWT state for `ROLE_ADMIN`, `ROLE_STAFF`, and `ROLE_USER`.
* **Server State Management**: **TanStack Query (React Query)** handles all asynchronous data fetching, caching, background refetching, and cache invalidation (e.g., refreshing cart state after a checkout conflict).
* **Client UI State Management**: **Zustand** is restricted strictly to ephemeral, client-only state (e.g., sidebar toggles, modal open/close). Client state is **never trusted for cart items, prices, or inventory persistence**.
* **Form Management**: **React Hook Form** paired with **Zod** schema validation for client-side field validation prior to API submissions.
* **HTTP Client**: **Axios** instance configured with request interceptors (attaching `Authorization: Bearer <token>`) and response interceptors (detecting HTTP 401 to trigger token refresh).

#### Rationale
Separating server state (TanStack Query) from ephemeral client state (Zustand) eliminates stale data synchronization bugs and ensures the UI always mirrors backend database reality.

---

### 4. PostgreSQL Architecture

The database is built on **PostgreSQL 16** with schema evolution strictly controlled by versioned **Flyway migration scripts (`V1__...sql` to `V8__...sql`)** starting on an empty database.

#### Core Tables & Constraints
* **`users`**: `id` (PK), `email` (UNIQUE, NOT NULL), `password_hash`, `full_name`, `phone`, `role` (`ADMIN`, `STAFF`, `USER`), `enabled`, timestamps.
* **`oauth2_accounts`**: `id` (PK), `user_id` (FK -> `users`), `provider` (`GOOGLE`), `provider_user_id`, timestamps. Unique constraint on `(provider, provider_user_id)`.
* **`vehicle_categories` & `brands`**: Category/Brand metadata with unique `slug` fields and indexes.
* **`vehicles`**: `id` (PK), `brand_id` (FK), `category_id` (FK), `title`, `price` (`DECIMAL(12,2)`), `year`, `fuel_type`, `transmission`, `version` (locking), timestamps.
* **`accessory_categories` & `accessories`**: Accessories with `price` (`DECIMAL(12,2)`), `stock_quantity` (INTEGER, `CHECK (stock_quantity >= 0)`), `version` (optimistic locking), `is_active`.
* **`carts` & `cart_items`**: `carts` tied 1:1 to `users`. `cart_items` features a **UNIQUE constraint on `(cart_id, accessory_id)`** to prevent duplicate line items.
* **`orders` & `order_items`**: `orders` tracks order status (`PENDING`, `PAID`, `SHIPPED`, `DELIVERED`, `CANCELLED`) and `total_amount` (`DECIMAL(12,2)`). `order_items` stores **snapshot copies** of `product_name` and `unit_price` (`DECIMAL(12,2)`).
* **`services`, `service_bookings`, `blogs`, `contact_messages`**: Operational domain tables with foreign keys and status tracking.

#### Financial & Indexing Rules
* All monetary values (`price`, `total_amount`, `unit_price`, `subtotal`) **MUST use `DECIMAL(12,2)`** — `FLOAT` and `DOUBLE` are strictly prohibited.
* B-Tree indexes are applied on foreign keys (`brand_id`, `category_id`, `user_id`), unique slugs, order user IDs, and search/filter columns.

#### Rationale
`DECIMAL(12,2)` prevents floating-point rounding errors. Snapshot fields in `order_items` ensure historical order accuracy even if product details or prices change in the future.

---

### 5. Redis Usage

Redis is integrated as a shared, distributed cache and bucket store:

* **Distributed Rate Limiting**: Bucket4j integrates with a Redis backend to synchronize request counters across multiple backend instances.
* **Token Blacklisting / Session Revocation**: Stores revoked refresh tokens or logged-out token identifiers when scaling horizontally.

#### Rationale
In multi-instance deployments, in-memory rate limiting allows clients to bypass rate caps by hitting different backend nodes. Redis provides centralized, low-latency state synchronization for rate limiting and security enforcement.

---

### 6. Authentication Architecture

The application supports dual authentication methods managed by **Spring Security** in stateless mode (`SessionCreationPolicy.STATELESS`):

1. **Email/Password Credentials**: Verified against `users.password_hash` using **BCrypt** hashing.
2. **Google OAuth2 / OpenID Connect**: Verified via Google's OAuth2 authorization code grant.
3. **Role-Based Access Control (RBAC)**:
   * `ROLE_ADMIN`: Full access to user management, vehicles, accessories, stock, orders, services, and blogs.
   * `ROLE_STAFF`: Access to update order statuses, manage service bookings, and view customer info.
   * `ROLE_USER`: Access to manage personal cart, execute checkout, view own orders, book services, and edit profile.

#### Rationale
BCrypt ensures passwords are never stored in plaintext. Stateless session management eliminates server-side session memory overhead and allows seamless horizontal scaling.

---

### 7. JWT Architecture

JWT handling is divided into two distinct tokens with clear security boundaries:

* **Access Token**:
  * **Lifetime**: Short-lived (e.g., 15 minutes).
  * **Contents**: User ID, email, role claims, issue time, expiration time.
  * **Transport**: Included in HTTP headers as `Authorization: Bearer <token>`.
  * **Storage**: In-memory on the React client (TanStack Query/Axios state).
* **Refresh Token**:
  * **Lifetime**: Long-lived (e.g., 7 days).
  * **Transport**: Delivered via `HttpOnly`, `Secure`, `SameSite=Strict/Lax` HTTP cookies.
  * **Storage**: Browser cookie jar (inaccessible to JavaScript, preventing XSS theft).
* **Rotation Flow**: When an API request fails with `401 Unauthorized`, the Axios response interceptor calls `POST /api/v1/auth/refresh`. The browser automatically transmits the `HttpOnly` cookie. Upon verification, the backend issues a new short-lived access token.

#### Rationale
Storing refresh tokens in `HttpOnly` cookies eliminates XSS extraction risks while short-lived access tokens limit the impact of potential token interception.

---

### 8. Google OAuth2 Architecture

1. **Client Authorization**: User initiates OAuth2 login on the frontend, redirecting to `/oauth2/authorization/google`.
2. **Provider Exchange**: Spring Security completes the OAuth2 code exchange directly with Google servers.
3. **Profile Extraction**: Backend retrieves `email`, `provider_user_id` (Google Sub ID), and name.
4. **Account Matching & Auto-Provisioning**:
   * System queries `oauth2_accounts` by `(provider='GOOGLE', provider_user_id)`.
   * If found, fetches linked `users` record.
   * If not found, checks if `users` table contains matching email. If email exists, links `oauth2_accounts` to existing user; if not, creates a new `users` record with role `USER` and links `oauth2_accounts`.
5. **Application Token Generation**: The backend **generates its own application Access Token and Refresh Token** — Google's access token is **NEVER** used for application authorization.
6. **Redirection Bridge**: Backend sets the application Refresh Token in an `HttpOnly` cookie and redirects the browser back to the frontend SPA.

#### Rationale
Decoupling application tokens from Google tokens ensures the system retains complete authority over its own JWT lifetimes, role claims, and session revocation. The `oauth2_accounts` table allows adding future providers (GitHub, Facebook) without schema changes.

---

### 9. Shopping Cart Architecture

* **Source of Truth**: The database (`carts` and `cart_items` tables) tied strictly to the user ID.
* **Validation Rules**:
  * Added quantity must be > 0.
  * Accessory must exist and be `is_active = true`.
  * Quantity must not exceed current `stock_quantity`.
* **Deduplication**: `(cart_id, accessory_id)` unique constraint ensures adding an existing item updates its quantity rather than inserting duplicate rows.
* **Stock Policy**: **Adding items to a cart DOES NOT reserve or deduct stock**. Stock is checked for availability during cart operations but is only deducted upon checkout execution.

#### Rationale
Reserving stock on cart addition leads to inventory denial-of-service attacks where users lock up items without purchasing. Deducting exclusively at checkout keeps inventory accessible.

---

### 10. Checkout Architecture

Checkout executes inside an atomic, database-level **`@Transactional`** boundary:

1. **Retrieve Cart**: Load user's active cart and `cart_items`. Reject if cart is empty.
2. **DB Price & Stock Fetch**: Fetch current product records directly from PostgreSQL. **NEVER trust client-provided prices, totals, or stock levels**.
3. **Stock Verification**: Compare requested line-item quantities against current database `stock_quantity`.
4. **Atomic Inventory Deduction**: Decrement `accessories.stock_quantity` for each purchased item.
5. **Order Snapshot Creation**: Insert record into `orders` (`total_amount`, status `PENDING`/`PAID`). Insert records into `order_items` copying `product_name` and `unit_price` as immutable snapshots.
6. **Cart Cleanup**: Delete items from `cart_items`.
7. **Commit Transaction**: Complete database commit.
8. **Rollback Guarantee**: If any item lacks stock or a lock conflict occurs, the entire transaction rolls back cleanly with zero partial stock deductions or orphaned orders.

#### Rationale
Fetching prices directly from the database prevents client-side price tampering. Immutable snapshots in `order_items` protect historical financial records from future catalog updates.

---

### 11. Stock Concurrency Architecture

#### How the System Prevents Overselling Under Concurrent Purchases
When multiple users attempt to purchase the same accessory simultaneously (e.g., 2 users trying to buy the last remaining item with `stock_quantity = 1`), overselling is prevented through a **multi-layered database concurrency mechanism**:

```
User A Checkout Request ──┐
                          ├──> [Spring Transactional Boundary]
User B Checkout Request ──┘           │
                                      ▼
                        Pessimistic Row Lock (SELECT ... FOR UPDATE)
                                      │
               ┌──────────────────────┴──────────────────────┐
               ▼                                             ▼
       Thread A (Acquires Lock)                     Thread B (Blocked/Waiting)
       • Reads Stock = 1                            • Waits for Thread A commit
       • Deducts Stock: 1 - 1 = 0                   
       • Creates Order                              
       • Commits Transaction & Releases Lock ──────> Acquires Lock
                                                    • Reads Stock = 0
                                                    • Validation Fails (Insufficient Stock)
                                                    • Transaction Rolls Back
                                                    • Returns HTTP 409 Conflict
```

1. **Pessimistic Row Locking (`SELECT ... FOR UPDATE`)**: During checkout, the checkout service executes a SELECT query with `@Lock(LockModeType.PESSIMISTIC_WRITE)` on the targeted `accessories` rows. This forces PostgreSQL to obtain a write lock on those specific rows, serializing concurrent checkout transactions.
2. **Optimistic Locking Guard (`@Version`)**: The `accessories` entity contains a `@Version` field. If optimistic locking is used, concurrent updates detect version mismatches and throw an `OptimisticLockException`, triggering a rollback.
3. **PostgreSQL Physical CHECK Constraint**: A hard database constraint `CHECK (stock_quantity >= 0)` guarantees that PostgreSQL physically rejects any UPDATE statement that attempts to set stock below zero.
4. **Atomic Update Statements**: Stock decrements execute as atomic database updates (`UPDATE accessories SET stock_quantity = stock_quantity - :qty WHERE id = :id AND stock_quantity >= :qty`).
5. **Client Conflict Handling**: When Thread B fails stock validation due to Thread A's completed purchase, Thread B's transaction rolls back completely and returns an **HTTP 409 Conflict** error.
6. **Frontend Synchronization**: The React frontend catches HTTP 409, displays *"Some products are no longer available in the requested quantity. Please review your cart."*, invalidates TanStack Query cart caches, and refreshes the current stock display.

#### Rationale
Relying on database-level row locks and physical CHECK constraints makes overselling impossible, regardless of how many concurrent threads attempt checkout at the exact same millisecond.

---

### 12. Rate Limiting Architecture

* **Engine**: **Bucket4j** filter integrated into the Spring Security filter chain.
* **Storage Mode**: In-memory for single-instance local execution; **Redis-backed storage** for distributed production deployments.
* **Rate Limiting Policies**:
  * **Unauthenticated / Sensitive Endpoints** (`POST /api/v1/auth/login`, `POST /api/v1/contact`, `POST /api/v1/services/bookings`): Strict IP-based limits (e.g., 5 requests per minute) to block brute-force attacks and spam.
  * **Checkout & Financial Endpoints** (`POST /api/v1/orders/checkout`): User ID-based limits (or combined IP + User ID) to prevent double-click or automated checkout spam.
  * **Read-Only Catalog Browsing** (`GET /api/v1/vehicles`, `GET /api/v1/accessories`): High/Generous limits to preserve seamless browsing.
* **Exceeded Policy Response**: Returns **HTTP 429 Too Many Requests** with standardized JSON error payload and response headers (`X-RateLimit-Remaining`, `Retry-After`).

#### Rationale
Differentiating between unauthenticated IP limits and authenticated User ID limits prevents API abuse while keeping catalog browsing fast and unrestricted for real users.

---

### 13. Docker Architecture

The environment is containerized via `docker-compose.yml` :

* **`db`**: PostgreSQL 16 image with persistent volume mapping and health checks.
* **`redis`**: Redis Alpine image providing shared memory storage for Bucket4j rate limiting.
* **`backend`**: Multi-stage Docker build executing Java 21 Spring Boot app. On startup, it connects to `db` and runs Flyway migrations automatically.
* **`frontend`**: Multi-stage Docker build compiling React Vite TS assets and serving them via Nginx.
* **Configuration**: Externalized via `.env` files (`DB_PASSWORD`, `JWT_SECRET`, `GOOGLE_CLIENT_ID`) so no secrets are committed to git.

#### Rationale
Containerization guarantees an identical execution environment across development, testing, and production, enabling single-command database bootstrap via Flyway.

---

### 14–17. Request Flow Diagrams

#### 15. Authentication Flow Sequence

```
User / Client SPA            Spring Security / Auth Filter         Google OAuth2 Server          Database (PostgreSQL)
       │                                  │                                 │                              │
[A. Email/Password Login]                 │                                 │                              │
       ├─ POST /api/v1/auth/login ───────>│                                 │                              │
       │  (email, password)               ├─ Query User & Password Hash ────┼─────────────────────────────>│
       │                                  │<─ Return User Entity ───────────┼──────────────────────────────┤
       │                                  ├─ Verify BCrypt Password Hash    │                              │
       │                                  ├─ Generate JWT Access Token      │                              │
       │                                  ├─ Generate Refresh Token Cookie  │                              │
       │<─ 200 OK (Access Token in Body, ─┤                                 │                              │
       │   Refresh Cookie in Header)      │                                 │                              │
       │                                  │                                 │                              │
[B. Google OAuth2 Login]                  │                                 │                              │
       ├─ Click "Login with Google" ─────>│                                 │                              │
       │  Redirect /oauth2/authorization/google                              │                              │
       │───────────────────────────────────────────────────────────────────>│                              │
       │<───────────────────────────────────────────────────────────────────┤ (User Authenticates)         │
       │                                  ├─ Exchange Auth Code ───────────>│                              │
       │                                  │<─ Return Google User Profile ───┤                              │
       │                                  ├─ Query oauth2_accounts / users ─┼─────────────────────────────>│
       │                                  │<─ Return Existing/Created User ─┼──────────────────────────────┤
       │                                  ├─ Issue APP Access Token & Cookie│                              │
       │<─ 302 Redirect to SPA Dashboard ─┤ (Never use Google Token directly)                             │
```

---

#### 16. Checkout Flow Sequence

```
User / Client SPA              Checkout Controller & Service          Database (PostgreSQL)
       │                                  │                                    │
       ├─ POST /api/v1/orders/checkout ──>│                                    │
       │  (shipping_address, phone)       ├─ BEGIN @Transactional               │
       │                                  ├─ Fetch User Cart & CartItems ─────>│
       │                                  │<─ Return Cart Data ────────────────┤
       │                                  │                                    │
       │                                  ├─ Fetch Fresh Product Prices & Stock│
       │                                  │  (SELECT ... FOR UPDATE) ─────────>│
       │                                  │<─ Return Fresh Product Rows ───────┤
       │                                  │                                    │
       │                                  ├─ Validate Stock >= Requested Qty   │
       │                                  ├─ Deduct stock_quantity in DB ─────>│
       │                                  ├─ Insert Order & OrderItems ───────>│
       │                                  │  (Snapshot name & unit_price)      │
       │                                  ├─ Clear cart_items ────────────────>│
       │                                  ├─ COMMIT Transaction ──────────────>│
       │<─ 201 Created (Order Response) ──┤                                    │
```

---

#### 17. Concurrent Checkout Flow Sequence

```
User A SPA                   User B SPA               Checkout Service & DB (PostgreSQL)
    │                            │                                    │
    ├─ Checkout Item X (Qty=1) ──┼───────────────────────────────────>│ (Thread A Begins Transaction)
    │                            ├─ Checkout Item X (Qty=1) ─────────>│ (Thread B Begins Transaction)
    │                            │                                    │
    │                            │                                    ├─ Thread A acquires Row Lock
    │                            │                                    │  (SELECT ... FOR UPDATE on Item X)
    │                            │                                    ├─ Thread B attempts Row Lock on Item X
    │                            │                                    │  └──> BLOCKED (Waits for Thread A)
    │                            │                                    │
    │                            │                                    ├─ Thread A reads Stock = 1
    │                            │                                    ├─ Thread A deducts Stock (1 -> 0)
    │                            │                                    ├─ Thread A creates Order A
    │                            │                                    ├─ Thread A COMMITS Transaction
    │<─ 201 Created (Order A) ───┼────────────────────────────────────┤  └──> Releases Row Lock
    │                            │                                    │
    │                            │                                    ├─ Thread B unblocked, acquires Row Lock
    │                            │                                    ├─ Thread B reads Stock = 0
    │                            │                                    ├─ Thread B validation fails (Stock < Qty)
    │                            │                                    ├─ Thread B ROLLBACK Transaction
    │                            │<─ 409 Conflict Error ──────────────┤
    │                            │   ("Insufficient stock")           │
    │                            │                                    │
    │                            ├─ Catch 409 Error                   │
    │                            ├─ Display Error Banner              │
    │                            ├─ Invalidate Cart Cache             │
    │                            ├─ Refetch Fresh Stock Data ────────>│
```

---

### Summary of Architectural Decisions & Source Grounding

| Architectural Decision | Chosen Strategy | Primary Reason / Source Requirement |
| :--- | :--- | :--- |
| **Backend Source of Truth** | Server evaluates all prices, stock, and roles | Prevents client-side financial/stock manipulation |
| **Monetary Types** | `DECIMAL(12,2)` | Prevents floating-point rounding errors |
| **Order Line Items** | Snapshot `product_name` and `unit_price` | Preserves historical audit accuracy when catalog updates occur |
| **Stock Concurrency** | Pessimistic locking + `@Version` + DB `CHECK` constraint | Guarantees zero overselling under simultaneous requests |
| **JWT Storage** | In-memory Access Token + `HttpOnly` Refresh Cookie | Prevents XSS token theft while remaining stateless |
| **OAuth2 Token Handling** | Issue app JWT; ignore Google access token | Maintains complete backend control over application session limits |
| **Rate Limiting Engine** | Bucket4j with Redis store | Enables uniform rate limiting across scaled backend nodes |
| **Cart Storage Policy** | DB-backed; no stock deduction on add-to-cart | Prevents inventory lockup attacks prior to checkout |

---

📊 I can turn this system architecture into a visual architectural diagram or a structured implementation roadmap broken down by phase. Which direction would you like to explore next?