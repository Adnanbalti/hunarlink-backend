# HunarLink Backend

Service marketplace REST API built with Spring Boot 3.

## Tech Stack
- Java 25 + Spring Boot 3.5
- PostgreSQL 16 (Docker)
- Redis 7 (Docker)
- JWT Authentication
- Maven

## Prerequisites
- Java 25+
- Maven 3.9+
- Docker Desktop

## Setup

### 1. Clone the repository
```bash
git clone https://github.com/Adnanbalti/hunarlink-backend.git
cd hunarlink-backend
```

### 2. Start Docker containers
```bash
docker-compose up -d
```

### 3. Run the application
```bash
mvn spring-boot:run
```

### 4. Test the API
## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/auth/send-otp | Send OTP to phone |
| POST | /api/v1/auth/verify-otp | Verify OTP, get JWT |

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/users | Register user |
| GET | /api/v1/users/{id} | Get user by ID |
| GET | /api/v1/users | Get all users |

### Providers
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/providers | Register provider |
| GET | /api/v1/providers/{id} | Get provider by ID |
| GET | /api/v1/providers?skill=X&city=Y | Search providers |

## Authentication
Protected routes require JWT token in header: