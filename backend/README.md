Pastebin Lite – Spring Boot Backend

Pastebin Lite is a lightweight backend service built using Spring Boot that allows users to create and view text pastes via unique IDs.
The backend is Dockerized, deployed on Render, and uses MySQL hosted on Railway.
Swagger UI is enabled for easy API exploration and testing.

🌐 Live URLs
🔹 Backend (Render)

Base URL
https://pastebin-lite-2-vgtz.onrender.com

Swagger UI
https://pastebin-lite-2-vgtz.onrender.com/swagger-ui/index.html

🔹 Database (Railway – MySQL)

Database Engine: MySQL 8

Hosted on: Railway

Connection Host: yamabiko.proxy.rlwy.net

Port: 48859

Database Name: railway

🧰 Tech Stack
Layer	Technology
Backend	Spring Boot 3
Language	Java 17
Database	MySQL 8
ORM	Spring Data JPA (Hibernate)
Connection Pool	HikariCP
API Docs	Springdoc OpenAPI (Swagger)
Containerization	Docker
Backend Hosting	Render
Database Hosting	Railway

⚙️ Configuration & Environment Variables

The application uses environment variables with local defaults, allowing it to run both locally and in production without code changes.

application.properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/pastebin}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

frontend.base.url=${FRONTEND_BASE_URL:http://localhost:5173}
server.port=${PORT:8080}

Required Environment Variables (Production)
Variable	Description
DB_URL	JDBC MySQL URL
DB_USERNAME	MySQL username
DB_PASSWORD	MySQL password
FRONTEND_BASE_URL	Frontend base URL
PORT	Render-provided port

Running the Project Locally
✅ Prerequisites

Ensure you have the following installed:

Java 17

Maven 3.8+

MySQL 8

Docker (optional)

1️. Clone the Repository
git clone https://github.com/pratikg1234/pastebin-lite.git
cd backend

2. Setup Local MySQL Database

Login to MySQL:

mysql -u root -p

Create database:

CREATE DATABASE pastebin;

3. Run the Application (Without Docker)
mvn spring-boot:run


Application will start on:

http://localhost:8080

📖 Swagger API Documentation
Local Swagger
http://localhost:8080/swagger-ui/index.html

Production Swagger
https://pastebin-lite-2-vgtz.onrender.com/swagger-ui/index.html

🐳 Docker & Render Deployment
Dockerfile

Uses multi-stage build

Optimized for production

Exposes port 8080

Render Configuration

Service Type: Web Service

Runtime: Docker

Port: 8080

Environment variables configured in Render dashboard

🛢️ Database Hosting (Railway)

MySQL deployed as a managed service

Backend connects using JDBC

No database container required on Render

Secrets stored securely as environment variables

🔐 Security Best Practices

No credentials are hardcoded

Secrets are injected via environment variables

.env and credentials are excluded from version control

Production DB not exposed publicly