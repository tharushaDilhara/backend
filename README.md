# Task Manager - Backend

A RESTful Spring Boot backend with JWT authentication for managing tasks.

## Tech Stack

- Java 21
- Spring Boot 3.4.4
- Spring Security
- JWT (jjwt 0.12.6)
- Spring Data JPA
- MySQL
- Lombok
- Gradle

## Prerequisites

- Java 21
- MySQL 8
- Gradle

## Getting Started

### 1. Clone the repository

git clone <your-repo-url>
cd backend

### 2. Create MySQL database

CREATE DATABASE your_db_name;

### 3. Configure application.properties

Update `src/main/resources/application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8085

### 4. Build and run

./gradlew bootRun

The server starts at `http://localhost:8085`

## Project Structure

src/main/java/com/example/backend/
├── Config/
│   ├── SecurityConfig.java        # Spring Security + CORS configuration
│   └── CorsConfig.java            # CORS filter(This is empty in my project as the CORS configurations are done in SecurityConfig.java file)
├── Controller/
│   ├── AuthController.java        # Register and login endpoints
│   └── TaskController.java        # Task CRUD endpoints
├── Entity/
│   ├── UserEntity.java            # User database entity
│   └── TaskEntity.java            # Task database entity
├── Repository/
│   ├── UserRepo.java              # User JPA repository
│   └── TaskRepo.java              # Task JPA repository
├── Service/
│   ├── ServiceIMPL/
│   │   └── TaskServiceIMPL.java   # Task service implementation
│   ├── TaskService.java           # Task service interface
│   ├── UserService.java           # User service + UserDetailsService
│   ├── JwtUtil.java               # JWT token generation and validation
│   └── JwtFilter.java             # JWT request filter
└── BackendApplication.java        # Main application entry point

## API Endpoints

### Auth Endpoints (Public)

| Method | URL                    | Description       | Body                              |
|--------|------------------------|-------------------|-----------------------------------|
| POST   | /api/auth/register     | Register new user | `{"email":"","password":""}`      |
| POST   | /api/auth/login        | Login user        | `{"email":"","password":""}`      |

### Task Endpoints (Protected - requires JWT)

| Method | URL                | Description        | Body                                               |
|--------|--------------------|--------------------|----------------------------------------------------|
| GET    | /api/tasks         | Get all tasks      | -                                                  |
| GET    | /api/tasks/{id}    | Get task by id     | -                                                  |
| POST   | /api/tasks         | Create a task      | `{"title":"","description":"","status":""}`        |
| PUT    | /api/tasks/{id}    | Update a task      | `{"title":"","description":"","status":""}`        |
| DELETE | /api/tasks/{id}    | Delete a task      | -                                                  |

## Authentication Flow

1. Register a user via `POST /api/auth/register`
2. Login via `POST /api/auth/login` — returns a JWT token
3. Add the token to all protected requests:
   `Authorization: Bearer <token>`

## Security

- Passwords are hashed using BCrypt
- JWT tokens expire after 10 hours
- Each user can only access their own tasks
- CORS is configured to allow requests from `http://localhost:4200`

## Docker

### Build the jar

./gradlew bootJar

### Build Docker image

docker build -t backend-app .

### Run the container

docker run -p 8085:8085 --name backend backend-app

### Run with docker-compose

docker-compose up --build

## Scripts

| Command                | Description                        |
|------------------------|------------------------------------|
| ./gradlew bootRun      | Run the application                |
| ./gradlew bootJar      | Build production jar               |
| ./gradlew test         | Run tests                          |
| ./gradlew clean build  | Clean and build the project        |