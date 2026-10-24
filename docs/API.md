Here is the REST API Specification for the **Car Marketplace Web Application**, strictly grounded in `CarMarketplaceWebApp.md`, the approved Architecture Analysis, and the approved PostgreSQL Database Design.

The complete document has been generated and published as **`API.md`**.

---

### Key Architectural & API Design Highlights

1. **Standardized JSON Envelopes**:
   - **Success Envelope**: Wraps response payloads inside `data`, `message`, `success: true`, and an ISO `timestamp`.
   - **Paginated Envelope**: Enforces standardized pagination metadata (`page`, `size`, `totalElements`, `totalPages`, `first`, `last`) across all search and listing endpoints.
   - **Error Envelope**: Captures business error codes (`STOCK_INSUFFICIENT`, `RATE_LIMIT_EXCEEDED`, `VALIDATION_ERROR`, `INVALID_CREDENTIALS`), human-readable messages, and field-level validation errors.

2. **Security & Authentication Alignment**:
   - **JWT Tokens**: Short-lived access tokens delivered via `Authorization: Bearer <token>` header; long-lived refresh tokens handled securely via `HttpOnly`, `Secure`, `SameSite=Strict` cookies.
   - **OAuth2 Handshake**: `GET /oauth2/authorization/google` triggers Google OpenID Connect, auto-provisions or links the user account, and issues the application's native JWT access token.
   - **Role-Based Authorization**: Method-level security (`@PreAuthorize`) enforces strict role segregation (`ROLE_ADMIN`, `ROLE_STAFF`, `ROLE_USER`).

3. **Transaction Safety & Concurrency**:
   - **Stock Deductions**: Executed during `POST /api/v1/checkout` within an atomic `@Transactional` boundary using **Pessimistic Write Locks (`SELECT ... FOR UPDATE`)** on `accessories` rows.
   - **Stock Conflicts**: Returns **HTTP 409 Conflict** with business code `STOCK_INSUFFICIENT` or `CONCURRENCY_CONFLICT` whenever available stock is exceeded, preventing overselling or negative inventory.
   - **Historical Snapshots**: `order_items` copies `product_name` and `unit_price` as immutable snapshots during checkout.

4. **Rate Limiting**:
   - Governed by **Bucket4j** filters (IP-based for unauthenticated sensitive endpoints like `/auth/login`, `/contact`, and `/service-bookings`; User ID-based for `/checkout`) returning **HTTP 429 Too Many Requests**.

---

### API Endpoint Hierarchy Summary

| Module | Core Endpoints | Auth Level |
| :--- | :--- | :--- |
| **Authentication** | `POST /auth/register`, `POST /auth/login`, `POST /auth/refresh`, `POST /auth/logout`, `GET /auth/me`, `GET /oauth2/authorization/google` | Public / Refresh Cookie / `Bearer` Token |
| **User Profile** | `PUT /users/me`, `PUT /users/me/password` | `ROLE_USER`, `ROLE_STAFF`, `ROLE_ADMIN` |
| **Vehicles** | `GET /vehicles`, `GET /vehicles/{idOrSlug}` | Public |
| **Brands & Categories** | `GET /brands`, `GET /brands/{idOrSlug}`, `GET /vehicle-categories` | Public |
| **Accessories** | `GET /accessories`, `GET /accessories/{idOrSlug}`, `GET /accessory-categories` | Public |
| **Shopping Cart** | `GET /cart`, `POST /cart/items`, `PUT /cart/items/{id}`, `DELETE /cart/items/{id}`, `DELETE /cart` | `ROLE_USER` |
| **Checkout & Orders** | `POST /checkout`, `GET /orders`, `GET /orders/{id}` | `ROLE_USER` |
| **Services & Bookings**| `GET /services`, `POST /service-bookings` | Public / `ROLE_USER` |
| **Blogs & Contact** | `GET /blogs`, `GET /blogs/{slug}`, `POST /contact` | Public |
| **Admin Management** | `/admin/users/*`, `/admin/vehicles/*`, `/admin/accessories/*`, `/admin/orders/*`, `/admin/service-bookings/*`, `/admin/blogs/*` | `ROLE_ADMIN` / `ROLE_STAFF` |

---

🛠️ Would you like to proceed with implementing the Java 21 Spring Boot domain entities and DTOs, or would you like to review the Spring Security & JWT configuration code next?