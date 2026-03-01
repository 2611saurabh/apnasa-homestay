# ApnaSa HomeStay

A production-grade home rental platform inspired by Airbnb.

## Architecture
📌 1️⃣ High-Level Architecture Overview

Client (Postman / Web / Mobile)
        |
        v
Spring Boot REST API
        |
        v
Spring Security Layer
        |
        v
JWT Authentication Filter
        |
        v
Business Layer (Services)
        |
        v
JPA Repository Layer
        |
        v
PostgreSQL Database

📌 2️⃣ Authentication Flow (Step-by-Step)

🔐 Registration Flow

Client → POST /api/auth/register
        |
        v
AuthController
        |
        v
AuthService.register()
        |
        |-- Check email exists?
        |-- Encode password (BCrypt)
        |-- Create AppUser entity
        |
        v
UserRepository.save()
        |
        v
PostgreSQL users table

Result:
✔ User saved with encrypted password
✔ Status = ACTIVE
✔ emailVerified = false


🔑 Login Flow (JWT Generation)

Client → POST /api/auth/login
        |
        v
AuthController
        |
        v
AuthService.login()
        |
        v
AuthenticationManager.authenticate()
        |
        v
CustomUserDetailsService.loadUserByUsername()
        |
        v
Database lookup
        |
        v
Password match check
        |
        v
JwtService.generateToken()
        |
        v
Return JWT to client

Result:
✔ Authentication verified
✔ JWT generated
✔ Token returned to client

🔐 Protected API Flow (JWT Validation)

Client → GET /protected-api
        |
        |  Header:
        |  Authorization: Bearer <token>
        v
JwtAuthenticationFilter
        |
        |-- Extract token
        |-- Validate signature
        |-- Extract username
        |-- Load user from DB
        |-- Set Authentication in SecurityContext
        v
Spring Security
        |
        |-- Check authentication exists?
        v
Controller method executes

📌 3️⃣ Security Components You Built
✅ JwtService

Generates token

Validates token

Extracts username

✅ JwtAuthenticationFilter

Intercepts every request

Reads Authorization header

Sets SecurityContext

✅ SecurityConfig

Stateless session

CSRF disabled

Public endpoints configured

JWT filter added before UsernamePasswordAuthenticationFilter

✅ PasswordEncoder

BCrypt hashing

4️⃣ Database Structure So Far

Secure password storage

| Column         | Type      | Purpose                 |
| -------------- | --------- | ----------------------- |
| id             | BIGINT    | Primary key             |
| username       | VARCHAR   | Unique login name       |
| email          | VARCHAR   | Unique email            |
| password_hash  | VARCHAR   | Encrypted password      |
| phone          | VARCHAR   | Optional                |
| status         | VARCHAR   | ACTIVE / BLOCKED        |
| email_verified | BOOLEAN   | Email verification flag |
| created_at     | TIMESTAMP | Account creation        |


📌 5️⃣ Current Package Structure

auth_service
 ├── config
 │    └── SecurityConfig
 │
 ├── controller
 │    └── AuthController
 │
 ├── domain.entity
 │    └── AppUser
 │
 ├── repository
 │    └── UserRepository
 │
 ├── service
 │    ├── AuthService
 │    └── CustomUserDetailsService
 │
 ├── security
 │    ├── JwtService
 │    └── JwtAuthenticationFilter
 │
 ├── dto
 │    ├── RegisterRequest
 │    ├── LoginRequest
 │    └── LoginResponse

 📌 6️⃣ What Is Production-Ready Now

✔ Stateless authentication
✔ JWT token-based security
✔ BCrypt password hashing
✔ Database-backed authentication
✔ Clean separation of concerns
✔ Proper filter chain configuration
✔ Spring Security 6 compatible setup

You have crossed beginner level.

## Tech Stack

Backend:
- Java 21
- Spring Boot 3.x
- PostgreSQL
- Valkey

Frontend:
- Next.js
- Tailwind CSS

Infrastructure:
- Docker
- AWS
- GitHub Actions
