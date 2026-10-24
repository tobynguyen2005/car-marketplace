# Car Marketplace

A full-stack car marketplace built with **Spring Boot, React, TypeScript, and PostgreSQL**. The application provides vehicle browsing, search, authentication, and management features through a modern web interface.

## Tech Stack

### Backend

- Java 21
- Spring Boot 3
- Spring Security and JWT authentication
- Google OAuth2 authentication
- Spring Data JPA and Hibernate
- Flyway database migrations
- PostgreSQL
- Maven
- Bucket4j for rate limiting

### Frontend

- React 18
- TypeScript
- Vite
- Tailwind CSS v4
- React Router
- Axios
- TanStack Query
- Zustand
- React Hook Form and Zod

## Features

- **Vehicle marketplace:** Browse and search available vehicles.
- **Authentication:** Secure login and registration with JWT-based authentication.
- **Google OAuth2:** Support for Google sign-in.
- **Access control:** Role-based permissions for administrative functionality.
- **Vehicle management:** Manage vehicle listings and related information.
- **Brands and accessories:** Organize vehicles by brand and support related marketplace content.
- **Search and filtering:** Find vehicles using available search and filter options.
- **Checkout protection:** Protect checkout operations against conflicting requests.
- **Blog and contact:** Provide supporting content and contact functionality.
- **Rate limiting:** Help protect API endpoints against excessive requests.
- **Database migrations:** Manage database schema changes through Flyway.

## Project Structure

```text
car-marketplace/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/
│   ├── src/
│   ├── package.json
│   ├── package-lock.json
│   └── ...
└── README.md
```

## Prerequisites

Install the following before running the project:

- Java Development Kit (JDK) 21
- Maven
- Node.js 20 or later
- npm
- PostgreSQL 16 or a compatible PostgreSQL version

## Getting Started

### 1. Clone the repository

```bash
git clone <YOUR_REPOSITORY_URL>
cd car-marketplace
```

Replace `<YOUR_REPOSITORY_URL>` with the repository URL.

### 2. Configure PostgreSQL

Create a PostgreSQL database for the application, for example:

```sql
CREATE DATABASE car_marketplace;
```

Configure the database connection using the environment variables expected by the backend configuration:

```text
DATABASE_URL=jdbc:postgresql://localhost:5432/car_marketplace
DATABASE_USERNAME=your_database_username
DATABASE_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
```

Ensure the variable names and JDBC URL format match the backend's actual configuration. Use a sufficiently strong JWT secret in the format expected by the application's JWT implementation.

Keep credentials and secrets in environment variables or an untracked local environment file. Never commit real credentials to the repository.

### 3. Run the backend

Open a terminal in the project root:

```bash
cd backend
```

Run the application:

```bash
mvn spring-boot:run
```

The backend uses the configuration defined in its Spring Boot configuration files. Confirm the configured server port and database connection before starting the application.

To run backend tests:

```bash
mvn clean test -Dspring.profiles.active=test
```

To build the application:

```bash
mvn package -DskipTests
```

### 4. Configure and run the frontend

Open another terminal from the project root:

```bash
cd frontend
npm ci
```

Create a local `.env` file if required by the frontend configuration:

```env
VITE_API_URL=http://localhost:8080
```

Adjust the API URL to match the backend's actual base URL and the variable name expected by the frontend.

Start the development server:

```bash
npm run dev
```

Open the local URL displayed by Vite in the terminal.

## Environment Configuration

| Variable            | Purpose                                                      |
| ------------------- | ------------------------------------------------------------ |
| `DATABASE_URL`      | PostgreSQL JDBC connection URL                               |
| `DATABASE_USERNAME` | Database username                                            |
| `DATABASE_PASSWORD` | Database password                                            |
| `JWT_SECRET`        | Secret used by the application's JWT implementation          |
| `VITE_API_URL`      | Backend API URL used by the frontend, if configured this way |

Additional configuration may be required for Google OAuth2 or other integrations enabled in the application. Refer to the backend and frontend configuration files for the exact variable names.

**Security:** Never commit production credentials, JWT secrets, OAuth client secrets, or database passwords. Configure deployment secrets through the environment settings of the hosting platform.

## Continuous Integration

GitHub Actions runs automated checks when code is pushed to, or a pull request targets, the configured branches:

- **Backend CI:** Sets up Java 21, runs Maven tests, and packages the application.
- **Frontend CI:** Sets up Node.js, installs dependencies using `npm ci`, and runs the production build.

The backend test workflow uses a GitHub Actions repository secret named `TEST_JWT_SECRET`. Configure it under **Settings → Secrets and variables → Actions**.

## Development Notes

- Use the test profile for backend automated tests.
- Keep database migrations in the configured Flyway migrations directory.
- Verify database and authentication configuration before deploying.
- Do not expose administrative credentials or secrets in source control.
- Keep frontend and backend API configuration consistent across environments.

## License

Add the applicable license information here if the project is distributed under an open-source license.
