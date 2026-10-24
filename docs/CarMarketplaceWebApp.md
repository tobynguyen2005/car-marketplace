> **Document Status:** Source of Truth
>
> This document is the authoritative specification for the Car Marketplace Web Application.
>
> All architecture, business logic, database design, security requirements, concurrency rules, API behavior, and UI/UX requirements defined in this document must be followed when implementing the application.
>
> If implementation details conflict with this document, this document takes priority unless explicitly changed by the project owner.

# AI PROMPT — BUILD A PRODUCTION-READY CAR MARKETPLACE WEB APPLICATION

---

# 1. ROLE

You are a senior full-stack software engineer and software architect specializing in:

* Java 21
* Spring Boot 3.x
* Spring Security
* JWT Authentication
* OAuth2 / OpenID Connect
* React 18+
* TypeScript
* PostgreSQL
* JPA / Hibernate
* Flyway
* RESTful API design
* E-commerce architecture
* Concurrency control
* Transaction management
* Rate limiting
* Scalable marketplace applications
* Production-grade UI/UX

Your task is to design and implement a complete, production-ready **Car Marketplace Web Application**.

The application is an automotive marketplace where users can:

* Browse and search vehicles
* View vehicles by category and brand
* View detailed vehicle information
* Browse and purchase car accessories
* Add accessories to a shopping cart
* Update shopping cart quantities
* Remove products from the cart
* Checkout and create orders
* View order history
* Explore automotive services
* Book automotive services
* Read automotive blogs
* Contact the company
* Register an account
* Login with email/password
* Login with Google OAuth2
* Manage their profile

Administrators and staff can manage:

* Users
* Vehicles
* Brands
* Vehicle categories
* Accessories
* Accessory categories
* Stock
* Orders
* Services
* Service bookings
* Blogs
* Contact messages

The application must be:

* Production-ready
* Secure
* Scalable
* Maintainable
* Responsive
* Transaction-safe
* Concurrency-safe
* Well-structured

The backend must be the ultimate source of truth for all business-critical operations.

Never trust the frontend for:

* Product prices
* Product stock
* User roles
* Authorization
* Order totals
* Checkout validation

---

# 2. PROJECT OVERVIEW

Create a modern automotive marketplace web application.

The platform should allow customers to:

1. Search and browse vehicles
2. View vehicles by category
3. View vehicles by brand
4. Filter vehicles by price and specifications
5. View detailed vehicle information
6. Browse car accessories
7. Search and filter accessories
8. Add accessories to a shopping cart
9. Update cart quantities
10. Remove cart items
11. Clear the cart
12. Checkout
13. Purchase accessories
14. View order history
15. View order details
16. Explore automotive services
17. Submit service booking requests
18. Read automotive blogs
19. Contact the company
20. Register an account
21. Login using email/password
22. Login using Google OAuth2
23. Manage personal profile

The application must correctly handle concurrent purchases.

If multiple users attempt to purchase the same product simultaneously, the system must:

* Prevent overselling
* Prevent negative stock
* Atomically deduct stock
* Create orders transactionally
* Return appropriate errors when stock is insufficient
* Maintain database consistency

---

# 3. TECHNOLOGY STACK

## 3.1 Backend

Use:

* Java 21
* Spring Boot 3.x
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Spring Security
* JWT Authentication
* OAuth2 Client
* OAuth2 / OpenID Connect
* Lombok
* Jakarta Validation API
* MapStruct
* Maven
* Flyway
* Bucket4j or equivalent rate-limiting solution

For distributed deployments, Redis may be used for distributed rate limiting.

Recommended:

* Redis
* Bucket4j + Redis-backed storage

---

## 3.2 Backend Architecture

Use layered architecture:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
Database
```

Use DTO pattern:

```text
Entity
   |
DTO
   |
Mapper
   |
Service
   |
Controller
```

Follow:

* SOLID principles
* Clean separation of concerns
* RESTful API design
* Dependency Injection
* Transaction management
* Validation
* Global exception handling
* Logging
* Security best practices

Do not expose JPA entities directly from REST APIs.

Use DTOs for:

* Requests
* Responses
* Authentication
* Products
* Cart
* Orders
* Services
* Blogs

---

# 4. FRONTEND TECHNOLOGY

Use:

* React 18+
* Vite
* TypeScript
* React Router DOM
* Axios
* TanStack Query
* Zustand
* TailwindCSS
* React Hook Form
* Zod validation

Create reusable components:

```text
src/
    components/

        Navbar
        Footer

        ProductCard
        VehicleCard
        AccessoryCard

        CartItem
        CartSummary

        SearchBar
        Pagination

        Modal
        Toast
        LoadingSkeleton
        ErrorMessage

        ProtectedRoute
        RoleGuard
```

Use:

* TanStack Query for server state
* Zustand only for appropriate client-side state
* Axios interceptors for authentication
* React Hook Form + Zod for form validation

The backend remains the source of truth for:

* Cart
* Stock
* Product prices
* Orders

Do not rely exclusively on Zustand for cart persistence.

---

# 5. HOME PAGE

Create a modern automotive landing page.

The homepage should include:

## Hero Section

Example:

> Find Your Dream Car

Allow users to:

* Search vehicle
* Search brand
* Search price range

The hero should emphasize high-quality automotive photography.

Avoid generic AI-generated hero designs.

Do not use:

* Random gradients
* Generic SaaS layouts
* Excessive glassmorphism
* Fake statistics
* Excessive floating elements

---

## Featured Vehicles

Display:

* New arrivals
* Best sellers
* Recommended cars

---

## Vehicle Categories

Display:

* Sedan
* SUV
* Hatchback
* Pickup
* Coupe
* MPV
* Electric Vehicle
* Hybrid

---

## Popular Brands

Display:

* Toyota
* Honda
* BMW
* Mercedes-Benz
* Ford
* Hyundai
* Kia
* Tesla
* Audi
* Lexus

---

# 6. VEHICLE MODULE

Create a complete vehicle management system.

## Vehicle Categories

Database table:

```text
vehicle_categories

id
name
description
image_url
created_at
updated_at
```

Example data:

```text
Sedan
SUV
Hatchback
Pickup
Coupe
MPV
Electric Vehicle
Hybrid
```

---

## Vehicle Entity

Create:

```text
vehicles

id
name
brand_id
category_id
model_year
price
engine
fuel_type
transmission
mileage
color
description
main_image
created_at
updated_at
```

Relationships:

```text
Brand 1 -------- N Vehicle

VehicleCategory 1 -------- N Vehicle
```

Features:

* CRUD
* Search
* Filter
* Pagination
* Sorting

Filters:

* Brand
* Category
* Price range
* Year
* Fuel type
* Transmission

---

# 7. VEHICLE BRAND MODULE

Create brand management.

Table:

```text
brands

id
name
logo
country
description
created_at
updated_at
```

Example brands:

```text
Toyota
Honda
BMW
Mercedes-Benz
Ford
Tesla
Audi
Lexus
Hyundai
Kia
```

Features:

* Brand listing
* Brand detail
* Brand vehicles
* Admin CRUD

---

# 8. CAR ACCESSORIES MODULE

Create a product marketplace for car accessories.

Categories:

```text
Interior Accessories
Exterior Accessories
Spare Parts
Tyres & Wheels
Car Care
Performance Parts
```

Database:

```text
accessories

id
name
category_id
brand
price
stock
description
image
created_at
updated_at
version
```

Use `version` for optimistic locking where appropriate.

Features:

* Product listing
* Product detail
* Search
* Filter
* Pagination
* Sorting
* Shopping cart
* Checkout
* Order creation

Important:

The backend must never allow:

```text
stock < 0
```

The system must prevent overselling under concurrent requests.

---

# 9. SHOPPING CART MODULE

Implement a complete shopping cart.

## Cart Entity

```text
carts

id
user_id
created_at
updated_at
```

Relationship:

```text
User 1 -------- 1 Cart
```

---

## Cart Item

```text
cart_items

id
cart_id
accessory_id
quantity
created_at
updated_at
```

Relationships:

```text
Cart 1 -------- N CartItem

Accessory 1 -------- N CartItem
```

Add:

```sql
UNIQUE(cart_id, accessory_id)
```

This prevents duplicate cart items for the same product.

---

## Cart Features

Implement:

* Add item
* View cart
* Update quantity
* Remove item
* Clear cart
* Calculate subtotal
* Calculate total quantity
* Validate stock before checkout

APIs:

```http
GET    /api/cart

POST   /api/cart/items

PUT    /api/cart/items/{itemId}

DELETE /api/cart/items/{itemId}

DELETE /api/cart
```

Example request:

```json
{
    "accessoryId": 1,
    "quantity": 2
}
```

When adding or updating an item:

* Quantity must be greater than 0
* Product must exist
* Product must be active
* Quantity must not exceed available stock

Return clear business errors.

Important:

Do not permanently deduct or reserve stock simply because an item is added to a cart.

Stock should be validated and atomically deducted during checkout.

---

# 10. CHECKOUT AND ORDER MODULE

Implement complete checkout.

## Orders

```text
orders

id
user_id
status
total_amount
created_at
updated_at
```

Statuses:

```text
PENDING
CONFIRMED
PROCESSING
SHIPPED
COMPLETED
CANCELLED
```

---

## Order Items

```text
order_items

id
order_id
accessory_id
product_name
unit_price
quantity
subtotal
```

Store:

* `product_name`
* `unit_price`

as snapshots.

This ensures historical orders remain correct when the product name or price changes.

---

## Checkout API

```http
POST /api/orders/checkout
```

Checkout flow:

```text
Authenticate user
        |
Load user's cart
        |
Validate cart
        |
Load current product prices
        |
Validate stock
        |
Begin transaction
        |
Lock / safely update stock
        |
Deduct stock
        |
Create order
        |
Create order items
        |
Clear cart
        |
Commit transaction
```

If any step fails:

```text
ROLLBACK
```

No partial order or stock deduction may remain.

Never trust:

* Client-provided price
* Client-provided total
* Client-provided stock

The backend must calculate all financial values from the database.

---

# 11. RACE CONDITION AND STOCK CONCURRENCY

The system must explicitly handle race conditions.

Example:

```text
Initial stock = 1

User A buys 1
User B buys 1

Both requests arrive at nearly the same time.
```

Expected:

```text
One transaction succeeds.

One transaction fails with OUT_OF_STOCK or STOCK_CONFLICT.

Final stock = 0.

Stock never becomes negative.
```

The frontend must not be responsible for preventing this.

The database and backend transaction system must enforce consistency.

---

## 11.1 Optimistic Locking

Preferred approach:

```java
@Version
private Long version;
```

When updating stock:

```text
Read product
    |
Check stock
    |
Decrease stock
    |
Save
    |
Version conflict?
    |
Retry or return concurrency error
```

Handle:

```text
OptimisticLockException
ObjectOptimisticLockingFailureException
```

Example response:

```json
{
    "code": "STOCK_CONFLICT",
    "message": "The product stock was changed by another transaction. Please try again."
}
```

Return:

```text
409 Conflict
```

when appropriate.

---

## 11.2 Pessimistic Locking

For highly critical checkout operations, optionally use:

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

Inside a transaction:

```text
BEGIN TRANSACTION

SELECT accessory
FOR UPDATE

IF stock < requestedQuantity:
    ROLLBACK
    return OUT_OF_STOCK

stock = stock - requestedQuantity

CREATE ORDER
CREATE ORDER ITEMS

COMMIT
```

Choose the most appropriate strategy based on expected workload.

The final implementation must guarantee:

```text
No overselling
No negative stock
Atomic stock deduction
Transactional order creation
Correct concurrent behavior
```

---

# 12. AUTHENTICATION AND AUTHORIZATION

Roles:

```text
USER
STAFF
ADMIN
```

Authentication methods:

1. Email/password
2. JWT
3. Google OAuth2

---

# 13. JWT AUTHENTICATION

Implement:

* Register
* Login
* Logout
* Access token
* Refresh token
* Token expiration
* Role authorization
* Profile

Use:

```text
BCryptPasswordEncoder
```

Never store plaintext passwords.

APIs:

```http
POST /api/auth/register

POST /api/auth/login

POST /api/auth/refresh

POST /api/auth/logout

GET /api/auth/me
```

Example login response:

```json
{
    "accessToken": "...",
    "refreshToken": "...",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
        "id": 1,
        "email": "user@example.com",
        "roles": ["USER"]
    }
}
```

Implement:

* JWT signature validation
* JWT expiration validation
* Refresh token rotation where appropriate
* Role-based access control
* Secure token handling

For production:

* Prefer HttpOnly Secure cookies for refresh tokens
* Do not store sensitive refresh tokens in localStorage
* Configure SameSite correctly
* Never expose JWT secrets in source code

---

# 14. GOOGLE OAUTH2 LOGIN

Implement Google OAuth2 login.

Flow:

```text
Frontend
    |
    v
Backend OAuth2 Authorization
    |
    v
Google
    |
    v
OAuth2 Callback
    |
    v
Find or Create User
    |
    v
Issue Application JWT
    |
    v
Frontend
```

When a user logs in with Google:

1. Authenticate with Google
2. Retrieve provider user information
3. Find existing user
4. If user does not exist, create user
5. Link OAuth2 provider account
6. Assign default role USER
7. Generate application JWT
8. Generate refresh token if required
9. Redirect to frontend

Do not use Google's access token as the application's authorization token.

The application must issue its own JWT.

---

## OAuth2 Database Table

```text
user_oauth_accounts

id
user_id
provider
provider_user_id
email
created_at
updated_at
```

Constraint:

```sql
UNIQUE(provider, provider_user_id)
```

The architecture should support adding future OAuth2 providers without redesigning the entire authentication system.

---

# 15. USER MODULE

Create:

```text
users

id
email
password
full_name
phone
avatar
status
created_at
updated_at
```

Roles:

```text
roles

id
name
```

Relationship:

```text
user_roles

user_id
role_id
```

Features:

* Register
* Login
* OAuth2 login
* Logout
* Profile
* Update profile
* Change password
* View order history

---

# 16. ROLE-BASED AUTHORIZATION

## ADMIN

Can:

* Manage users
* Manage vehicles
* Manage brands
* Manage categories
* Manage accessories
* Manage stock
* Manage services
* Manage service bookings
* Manage blogs
* Manage orders
* Manage contact messages

---

## STAFF

Can:

* Manage orders
* Update order status
* Manage service bookings
* Update booking status
* View customer information

---

## USER

Can:

* Browse vehicles
* Browse accessories
* Manage own cart
* Checkout
* View own orders
* Book services
* View own bookings
* Manage own profile

Use method-level security where appropriate:

```java
@PreAuthorize("hasRole('ADMIN')")
```

and:

```java
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
```

---

# 17. RATE LIMITING

Implement server-side rate limiting.

Use:

* Bucket4j
* Redis-backed rate limiting for distributed deployments

For single-instance development:

* In-memory rate limiting is acceptable

For multiple backend instances:

* Use Redis or another shared distributed rate-limit store

---

## Rate-Limited APIs

Apply rate limiting to:

```text
POST /api/auth/login

POST /api/auth/register

POST /api/auth/refresh

POST /api/contact

POST /api/service-bookings

POST /api/orders/checkout
```

Example policies:

```text
Login:
5 requests per minute per IP

Register:
3 requests per minute per IP

Refresh token:
10 requests per minute per user/IP

Checkout:
10 requests per minute per authenticated user

Contact:
5 requests per minute per IP

Service booking:
10 requests per minute per authenticated user
```

All values must be configurable.

Example:

```properties
rate-limit.login.capacity=5
rate-limit.login.refill-duration=1m

rate-limit.register.capacity=3
rate-limit.register.refill-duration=1m
```

For authenticated APIs, prefer:

```text
userId
```

For unauthenticated APIs:

```text
IP address
```

Where appropriate, combine:

```text
IP-based limiting
+
User-based limiting
```

Return:

```http
429 TOO MANY REQUESTS
```

Example:

```json
{
    "code": "RATE_LIMIT_EXCEEDED",
    "message": "Too many requests. Please try again later.",
    "timestamp": "2026-07-30T10:00:00Z"
}
```

Include:

```http
Retry-After
```

where possible.

Do not aggressively rate-limit normal read-only browsing APIs unless necessary.

---

# 18. SERVICE MODULE

Create automotive services.

Categories:

```text
Car Maintenance
Car Repair
Car Inspection
Car Insurance
Car Washing
Car Modification
Car Rental
```

Database:

```text
services

id
name
description
price
duration
image
created_at
updated_at
```

Features:

* Service listing
* Service detail
* Booking request
* Customer information form

---

# 19. SERVICE BOOKING MODULE

Create:

```text
service_bookings

id
user_id
service_id
booking_date
status
customer_name
phone
note
created_at
updated_at
```

Statuses:

```text
PENDING
CONFIRMED
COMPLETED
CANCELLED
```

APIs:

```http
POST /api/service-bookings

GET /api/service-bookings/my

GET /api/admin/service-bookings

PUT /api/admin/service-bookings/{id}/status
```

Protect booking APIs with rate limiting.

---

# 20. BLOG MODULE

Create an automotive blog system.

Purpose:

* Vehicle introductions
* Automotive news
* Car reviews
* Maintenance guides

Database:

```text
blogs

id
title
content
thumbnail
author
category
slug
created_at
updated_at
```

Features:

* Blog listing
* Blog detail
* Related vehicles
* SEO-friendly URLs

Example posts:

```text
Toyota Camry 2026 Review

Best Electric Cars 2026

BMW Technology Overview
```

---

# 21. CONTACT MODULE

Create contact page.

Fields:

```text
name
email
phone
message
created_at
```

Features:

* Send inquiry
* Save messages
* Admin management

APIs:

```http
POST /api/contact

GET /api/admin/contacts
```

Protect contact API with rate limiting.

---

# 22. COMPLETE DATABASE IMPLEMENTATION

The AI must create and implement the actual PostgreSQL database.

Do not only describe an ERD.

The generated application must start with an empty PostgreSQL database and automatically create the complete schema using Flyway.

Required tables:

```text
users
roles
user_roles
user_oauth_accounts

vehicle_categories
brands
vehicles

accessory_categories
accessories

carts
cart_items

orders
order_items

services
service_bookings

blogs
contacts
```

Implement:

* Primary keys
* Foreign keys
* NOT NULL constraints
* UNIQUE constraints
* CHECK constraints
* Default values
* Created timestamps
* Updated timestamps
* Indexes
* Correct relationships

Important constraints:

```sql
UNIQUE(cart_id, accessory_id)

UNIQUE(provider, provider_user_id)

CHECK(stock >= 0)

CHECK(quantity > 0)

CHECK(price >= 0)
```

Use appropriate PostgreSQL types.

For money:

```text
NUMERIC / DECIMAL
```

Never use:

```text
FLOAT
REAL
DOUBLE
```

for financial values.

Primary keys may use:

```text
BIGINT
```

or:

```text
UUID
```

depending on the architecture.

---

# 23. DATABASE RELATIONSHIPS

Implement relationships:

```text
User
 ├── Roles
 ├── OAuth Accounts
 ├── Cart
 ├── Orders
 └── Service Bookings

Vehicle
 ├── Brand
 └── Vehicle Category

Accessory
 └── Accessory Category

Cart
 └── Cart Items
       └── Accessory

Order
 └── Order Items
       └── Accessory

Service
 └── Service Bookings
```

Use correct JPA mappings.

Avoid unnecessary bidirectional relationships where they create serialization or performance issues.

Avoid N+1 query problems.

---

# 24. FLYWAY MIGRATIONS

Create real executable Flyway migration files.

Example:

```text
src/main/resources/db/migration/

V1__create_users_and_roles.sql

V2__create_vehicle_tables.sql

V3__create_accessory_tables.sql

V4__create_cart_tables.sql

V5__create_order_tables.sql

V6__create_service_tables.sql

V7__create_blog_tables.sql

V8__create_contact_tables.sql

V9__create_indexes.sql

V10__insert_initial_data.sql
```

The exact migration structure may be adjusted.

Migration requirements:

* Must work on a fresh PostgreSQL database
* Must respect foreign-key dependency order
* Must be versioned through Flyway
* Must not require manual table creation
* Must include required seed data

---

# 25. INITIAL SEED DATA

Create realistic initial data.

Seed:

## Roles

```text
USER
STAFF
ADMIN
```

## Vehicle Categories

```text
Sedan
SUV
Hatchback
Pickup
Coupe
MPV
Electric Vehicle
Hybrid
```

## Accessory Categories

```text
Interior Accessories
Exterior Accessories
Spare Parts
Tyres & Wheels
Car Care
Performance Parts
```

## Vehicle Brands

```text
Toyota
Honda
BMW
Mercedes-Benz
Ford
Hyundai
Kia
Tesla
Audi
Lexus
```

Also add realistic sample:

* Vehicles
* Accessories
* Services
* Blogs

Do not use meaningless data such as:

```text
Test Product 1
Product ABC
Lorem Ipsum
Car 123
```

The application should be immediately usable after startup.

---

# 26. DATABASE DOCUMENTATION

Generate:

* ERD
* Database relationship explanation
* Table descriptions
* Migration documentation

README must explain:

1. How to start PostgreSQL
2. How Flyway creates the schema
3. How to configure database credentials
4. How to reset development database
5. How to run seed data

The final project must include an actual functional database implementation.

---

# 27. REST API DESIGN

## Authentication

```http
POST /api/auth/register

POST /api/auth/login

POST /api/auth/refresh

POST /api/auth/logout

GET /api/auth/me
```

## Vehicles

```http
GET /api/vehicles

GET /api/vehicles/{id}

POST /api/admin/vehicles

PUT /api/admin/vehicles/{id}

DELETE /api/admin/vehicles/{id}
```

## Brands

```http
GET /api/brands

GET /api/brands/{id}
```

## Accessories

```http
GET /api/accessories

GET /api/accessories/{id}
```

## Cart

```http
GET /api/cart

POST /api/cart/items

PUT /api/cart/items/{itemId}

DELETE /api/cart/items/{itemId}

DELETE /api/cart
```

## Orders

```http
POST /api/orders/checkout

GET /api/orders

GET /api/orders/{id}

GET /api/orders/{id}/items
```

## Admin Orders

```http
GET /api/admin/orders

PUT /api/admin/orders/{id}/status
```

## Services

```http
GET /api/services

POST /api/service-bookings

GET /api/service-bookings/my
```

## Blogs

```http
GET /api/blogs

GET /api/blogs/{slug}
```

## Contact

```http
POST /api/contact
```

---

# 28. API ERROR HANDLING

Implement:

```java
@RestControllerAdvice
```

Create standardized API responses.

Example:

```json
{
    "success": false,
    "code": "ACCESSORY_NOT_FOUND",
    "message": "Accessory not found",
    "timestamp": "2026-07-30T10:00:00Z"
}
```

Business error codes:

```text
USER_NOT_FOUND
INVALID_CREDENTIALS
EMAIL_ALREADY_EXISTS
UNAUTHORIZED
FORBIDDEN

ACCESSORY_NOT_FOUND
OUT_OF_STOCK
STOCK_CONFLICT

CART_NOT_FOUND
CART_ITEM_NOT_FOUND

ORDER_NOT_FOUND
ORDER_CANNOT_BE_CANCELLED

RATE_LIMIT_EXCEEDED

VALIDATION_ERROR

INTERNAL_SERVER_ERROR
```

Use appropriate HTTP status codes:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
429 Too Many Requests
500 Internal Server Error
```

Use:

```text
409 Conflict
```

for appropriate stock concurrency conflicts.

---

# 29. FRONTEND PAGES

Create:

```text
src/

pages/

    Home.tsx

    Vehicles.tsx
    VehicleDetail.tsx

    Brands.tsx

    Accessories.tsx
    AccessoryDetail.tsx

    Cart.tsx
    Checkout.tsx

    Orders.tsx
    OrderDetail.tsx

    Services.tsx
    ServiceDetail.tsx
    ServiceBookings.tsx

    Blog.tsx
    BlogDetail.tsx

    Contact.tsx

    Login.tsx
    Register.tsx
    OAuth2Callback.tsx

    Profile.tsx

    AdminDashboard.tsx
```

---

# 30. FRONTEND AUTHENTICATION

Implement:

* Axios interceptors
* TanStack Query
* Zustand
* JWT access token handling
* Refresh token handling
* OAuth2 login
* Protected routes
* Role-based route guards

Components:

```text
ProtectedRoute
RoleGuard
```

Protect:

```text
/admin/*
/profile
/cart
/checkout
/orders
/service-bookings
```

Unauthorized users must not access protected resources.

---

# 31. FRONTEND SHOPPING CART

Implement:

* Cart badge in Navbar
* Add to cart
* Quantity selector
* Increase quantity
* Decrease quantity
* Remove item
* Clear cart
* Subtotal
* Total quantity
* Checkout

When checkout fails due to changed stock:

Display:

> Some products are no longer available in the requested quantity. Please review your cart.

Refresh cart state after stock conflict.

Use TanStack Query to synchronize server state.

The backend database is the source of truth.

---

# 32. UI/UX DESIGN — HALLMARK-STYLE, AVOID AI SLOP

The frontend must have a distinctive, production-quality visual identity.

Do NOT generate generic "AI Slop" UI.

Avoid:

* Generic dashboard templates
* Excessive rounded cards
* Excessive glassmorphism
* Random neon colors
* Purple/blue AI gradients
* Excessive shadows
* Every section inside a card
* Oversized headings everywhere
* Excessive animations
* Decorative elements without purpose
* Generic stock-photo hero sections
* Cookie-cutter SaaS layouts
* Excessive icon usage
* Fake statistics
* Random floating elements
* Excessive gradients

The design should feel like a real automotive business website designed by a professional product designer.

---

# 33. DESIGN DIRECTION

Use a **Hallmark-style visual direction** as inspiration for the overall quality and presentation.

The interface should feel:

* Premium
* Trustworthy
* Mature
* Automotive-focused
* Editorial
* Clean
* Sophisticated
* Commercially realistic

Prioritize:

* Brand identity
* Usability
* Visual hierarchy
* Typography
* Photography
* Spacing
* Grid systems
* Color balance
* Content hierarchy

over visual effects.

The website should look like a real commercial automotive marketplace rather than an AI-generated template.

---

# 34. VISUAL LANGUAGE

Use a restrained and professional color palette.

Prefer:

* Neutral backgrounds
* White or warm off-white surfaces
* Charcoal or deep neutral text
* One strong brand accent color
* Subtle borders
* Minimal shadows

Do not use excessive gradients.

Do not make every component a floating card.

Use:

* Flat sections
* Editorial layouts
* Structured content blocks
* Strong photography

where appropriate.

---

# 35. TYPOGRAPHY

Use a professional typography system.

Define:

```text
Display Font
Heading Font
Body Font
UI Font
```

Typography must create a clear hierarchy.

Avoid:

* Excessive bold text
* Huge headings everywhere
* Random font sizes
* Too many font families

Use consistent typography across:

* Navigation
* Hero
* Vehicle pages
* Product pages
* Cart
* Checkout
* Blog
* Admin dashboard

---

# 36. HOMEPAGE DESIGN

The homepage should feel like a real automotive brand website.

Suggested structure:

```text
Header
    |
Hero / Featured Vehicle
    |
Vehicle Discovery
    |
Featured Vehicles
    |
Brands
    |
Accessories
    |
Automotive Services
    |
Editorial / Automotive Blog
    |
Contact / Dealership Information
    |
Footer
```

The hero should use strong automotive imagery.

Avoid the generic:

```text
Huge Heading
+
Paragraph
+
Two Random Buttons
+
Gradient Background
```

Instead, emphasize the vehicle and meaningful product information.

Example:

```text
Featured Vehicle

BMW X5
2026

Explore Vehicle
```

The vehicle itself should be the visual focus.

---

# 37. VEHICLE LISTING DESIGN

Vehicle listings should feel like a professional automotive catalog.

Display:

* High-quality vehicle image
* Brand
* Model
* Year
* Price
* Key specifications
* Clear CTA

Use consistent image ratios.

Do not overload cards with unnecessary information.

---

# 38. VEHICLE DETAIL DESIGN

Create an immersive vehicle detail experience.

Include:

* Large vehicle gallery
* Vehicle name
* Brand
* Price
* Key specifications
* Description
* Technical specifications
* Related vehicles

Use a strong editorial layout.

The vehicle image should be the main visual focus.

---

# 39. ACCESSORIES MARKETPLACE DESIGN

The accessory marketplace should feel like a professional e-commerce experience.

Display:

* Product image
* Product name
* Brand
* Price
* Stock availability
* Quantity selector
* Add to cart

Avoid excessive card decoration.

---

# 40. SHOPPING CART DESIGN

Keep the cart simple and functional.

Suggested layout:

```text
Cart Items                  Order Summary

Product Image               Subtotal
Product Name                Shipping
Quantity                    Total
Price

Remove

                            Checkout
```

Prioritize usability.

Do not overdesign the checkout experience.

---

# 41. CHECKOUT DESIGN

Create a clean, trustworthy checkout experience.

Use a structured flow:

```text
Cart Review
    |
Customer Information
    |
Order Summary
    |
Confirm Order
```

Clearly display:

* Products
* Quantities
* Unit prices
* Total
* Stock validation errors

Do not add unnecessary animations.

---

# 42. AUTHENTICATION PAGE DESIGN

Login and Register pages should be minimal and professional.

Include:

* Email
* Password
* Login
* Register
* Google OAuth2 Login

Avoid unnecessary illustrations.

Avoid excessive decorative elements.

---

# 43. ADMIN DASHBOARD DESIGN

The admin dashboard may use a traditional dashboard design.

Prioritize:

* Data tables
* Search
* Filters
* Pagination
* Forms
* Stock management
* Order management
* User management

The admin interface should prioritize efficiency over visual decoration.

---

# 44. RESPONSIVE DESIGN

The UI must work properly on:

* Desktop
* Laptop
* Tablet
* Mobile

Do not simply shrink the desktop design.

Create intentional responsive layouts.

Mobile must include:

* Mobile navigation
* Optimized product grids
* Easy checkout
* Accessible actions
* No horizontal overflow

---

# 45. ANIMATION

Use animations sparingly.

Use animation for:

* Page transitions
* Hover states
* Loading states
* Cart interactions
* Image transitions

Avoid:

* Constant floating animations
* Excessive parallax
* Long loading animations
* Distracting effects

The website should still feel premium with animations disabled.

---

# 46. IMAGES

Use high-quality automotive photography.

Images must:

* Have consistent aspect ratios
* Be optimized
* Use lazy loading
* Include meaningful alt text

Do not use random unrelated car images.

Do not show an image that conflicts with the vehicle brand or model being displayed.

---

# 47. DESIGN SYSTEM

Create a consistent design system.

Define:

```text
Colors
Typography
Spacing
Border Radius
Shadows
Buttons
Inputs
Cards
Tables
Badges
Navigation
Modal
Toast
Loading States
```

All pages must use the same design system.

The final UI must look like one cohesive automotive marketplace.

It must not look like a collection of unrelated AI-generated screens.

---

# 48. SECURITY REQUIREMENTS

Backend must implement:

* JWT authentication
* OAuth2 authentication
* BCrypt password hashing
* Role authorization
* Input validation
* Global exception handling
* CORS configuration
* CSRF considerations
* Rate limiting
* Secure refresh token handling
* Secure OAuth2 callback
* Brute-force protection
* Unauthorized access protection
* Mass assignment protection
* SQL injection protection

Never:

* Store plaintext passwords
* Store secrets in Git
* Hardcode JWT secrets
* Trust frontend stock validation
* Trust frontend prices
* Trust frontend roles
* Trust client authorization claims without validation

The backend must retrieve current product prices during checkout.

---

# 49. PERFORMANCE REQUIREMENTS

Backend:

Implement:

* Pagination
* Database indexing
* Lazy loading where appropriate
* Query optimization
* DTO projections where beneficial
* Transaction boundaries
* Connection pool configuration

Frontend:

Implement:

* React Query caching
* Lazy-loaded routes
* Image optimization
* Debounced search
* Pagination
* Optimistic UI updates only where safe

For checkout and stock updates:

Correctness is more important than optimistic UI behavior.

---

# 50. TRANSACTION AND CONCURRENCY REQUIREMENTS

Critical operations:

```text
Checkout
Stock deduction
Order creation
Cart modification
```

Checkout must use:

```java
@Transactional
```

The transaction must atomically handle:

```text
Stock validation
+
Stock deduction
+
Order creation
+
Order item creation
+
Cart clearing
```

The system must prevent:

```text
Overselling
Negative stock
Partial order creation
Partial stock deduction
```

---

# 51. TESTING REQUIREMENTS

Create unit tests for:

* Authentication service
* JWT service
* OAuth2 user service
* Cart service
* Checkout service
* Stock validation
* Rate limiter
* Order service

Create integration tests for:

* Register
* Login
* OAuth2 user creation
* Cart operations
* Checkout
* Order creation
* Role authorization

Create concurrency tests.

Example:

```text
100 concurrent users
+
10 available products
```

Expected:

```text
Maximum 10 successful purchases

No overselling

No negative stock

Remaining stock = 0
```

Also test:

```text
Stock = 1

User A buys 1
User B buys 1
```

Expected:

```text
One success
One OUT_OF_STOCK or STOCK_CONFLICT
Stock = 0
```

---

# 52. DEPLOYMENT STRUCTURE

Create:

```text
car-marketplace/

    backend/

    frontend/

    docker-compose.yml

    README.md
```

Docker services:

```text
postgres
backend
frontend
```

If Redis is used:

```text
redis
```

Add Redis to Docker Compose.

---

# 53. ENVIRONMENT VARIABLES

## Backend

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD

JWT_SECRET
JWT_ACCESS_TOKEN_EXPIRATION
JWT_REFRESH_TOKEN_EXPIRATION

GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET

REDIS_HOST
REDIS_PORT

CORS_ALLOWED_ORIGINS
```

## Frontend

```text
VITE_API_URL
VITE_OAUTH2_LOGIN_URL
```

Never commit secrets.

Use:

```text
.env
.env.example
```

Provide safe example values in `.env.example`.

---

# 54. BACKEND PACKAGE STRUCTURE

Recommended:

```text
com.example.carmarketplace

    config/
        SecurityConfig
        CorsConfig
        OAuth2Config
        RateLimitConfig

    security/
        JwtAuthenticationFilter
        JwtTokenService
        CustomUserDetailsService
        OAuth2AuthenticationSuccessHandler
        OAuth2UserService

    auth/
        controller/
        service/
        dto/

    user/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    vehicle/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    accessory/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    cart/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    order/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    service/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    blog/
        controller/
        service/
        repository/
        entity/
        dto/
        mapper/

    contact/
        controller/
        service/
        repository/
        entity/
        dto/

    common/
        exception/
        response/
        validation/
        util/
```

---

# 55. EXPECTED FINAL OUTPUT

Generate a complete project containing:

1. Complete Spring Boot backend
2. Complete React TypeScript frontend
3. PostgreSQL database
4. Complete database schema
5. Flyway migration scripts
6. Realistic seed data
7. Database ERD
8. Database documentation
9. JWT authentication
10. Refresh token mechanism
11. Google OAuth2 authentication
12. Role-based authorization
13. User management
14. Shopping cart
15. Checkout system
16. Order management
17. Stock management
18. Race-condition-safe stock deduction
19. Optimistic or pessimistic locking
20. Transactional checkout
21. Rate limiting
22. Redis support where appropriate
23. Vehicle management
24. Brand management
25. Accessory marketplace
26. Automotive services
27. Service booking
28. Automotive blogs
29. Contact system
30. Admin dashboard
31. REST API documentation
32. Standardized API error handling
33. Unit tests
34. Integration tests
35. Concurrency tests
36. Docker configuration
37. Environment configuration
38. README setup instructions
39. Professional Hallmark-style UI/UX
40. Responsive desktop and mobile design

---

# 56. CRITICAL BUSINESS REQUIREMENTS

Pay particular attention to:

```text
JWT Authentication
        +
Google OAuth2 Login
        +
Role-Based Authorization
        +
Rate Limiting
        +
Shopping Cart
        +
Checkout
        +
Order Management
        +
Transactional Stock Deduction
        +
Race Condition Handling
        +
Concurrency Control
        +
No Overselling
        +
Complete PostgreSQL Database
        +
Flyway Migrations
        +
Realistic Seed Data
        +
Production-Quality UI/UX
        +
No AI-Slop Design
```

The backend must be the ultimate source of truth for:

```text
Authentication
Authorization
User Roles
Product Prices
Product Stock
Cart Validation
Order Totals
Order Creation
```

The frontend must never be trusted to enforce business-critical rules.

The application must be able to start from a fresh environment with:

```text
PostgreSQL
+
Optional Redis
+
Spring Boot Backend
+
React Frontend
```

and automatically initialize the database through Flyway.

The final result should be a complete, coherent, production-ready automotive marketplace application that looks and behaves like a real commercial product, not a simple demo or generic AI-generated CRUD application.
