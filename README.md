# HunarLink Backend API

A production-ready REST API for HunarLink — a local service marketplace connecting consumers with skilled providers (electricians, plumbers, tutors, etc.) in Pakistan.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 3.5 |
| Database | PostgreSQL 16 |
| Cache | Redis 7 |
| Auth | JWT + OTP (Phone-based) |
| Build Tool | Maven 3.9 |
| Containerization | Docker |

## Project Structure
src/main/java/com/hunarlink/
├── config/          # Security, app configuration
├── modules/
│   ├── auth/        # OTP + JWT authentication
│   ├── user/        # User management
│   ├── provider/    # Provider profiles + search
│   ├── booking/     # Booking lifecycle
│   ├── review/      # Reviews + ratings
│   └── notification/# In-app notifications
└── shared/
├── exception/   # Global exception handling
├── response/    # API response wrapper
└── security/    # JWT filter + utilities

## Prerequisites

- Java 25+
- Maven 3.9+
- Docker Desktop

## Quick Start

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/hunarlink-backend.git
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

### 4. Access Swagger UI
http://localhost:8080/swagger-ui/index.html

## API Endpoints

### Authentication
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/v1/auth/send-otp | No | Send OTP to phone |
| POST | /api/v1/auth/verify-otp | No | Verify OTP, get JWT |

### Users
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/v1/users | No | Register user |
| GET | /api/v1/users/{id} | Yes | Get user by ID |
| GET | /api/v1/users | Yes | Get all users |

### Providers
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/v1/providers | Yes | Register provider |
| GET | /api/v1/providers/{id} | Yes | Get provider by ID |
| GET | /api/v1/providers | Yes | Search providers |

### Bookings
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/v1/bookings | Yes | Create booking |
| GET | /api/v1/bookings/{id} | Yes | Get booking |
| GET | /api/v1/bookings/my | Yes | My bookings |
| PATCH | /api/v1/bookings/{id}/status | Yes | Update status |

### Reviews
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/v1/reviews | Yes | Submit review |
| GET | /api/v1/reviews/provider/{id} | Yes | Provider reviews |

### Notifications
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | /api/v1/notifications/my | Yes | My notifications |
| GET | /api/v1/notifications/unread-count | Yes | Unread count |
| PATCH | /api/v1/notifications/{id}/read | Yes | Mark as read |
| PATCH | /api/v1/notifications/mark-all-read | Yes | Mark all read |

## Authentication Flow
POST /auth/send-otp    → OTP sent to phone (console in dev)
POST /auth/verify-otp  → Returns JWT token
Add token to requests  → Authorization: Bearer <token>

## Architecture

- **Layered Architecture**: Controller → Service → Repository
- **JWT Stateless Auth**: No server-side sessions
- **Global Exception Handling**: Consistent error responses
- **Input Validation**: Jakarta Validation on all DTOs
- **Docker Compose**: One command setup

## Developer

**Muhammad Adnan**
- GitHub: github.com/Adnanbalti
- LinkedIn: linkedin.com/in/adnanbalti