# Microservices Banking Project

> A scalable, modular banking application built using Spring Boot and Spring Cloud microservices architecture.

**Repository:** [yajatdhand/Microservices-Banking-Project](https://github.com/yajatdhand/Microservices-Banking-Project)  
**Language:** Java (100%)  
**Config Repo:** [yajatdhand/microservices-config](https://github.com/yajatdhand/microservices-config)

---

## Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Services](#services)
- [Configuration](#configuration)
- [API Reference](#api-reference)
- [Running the Project](#running-the-project)

---

## Project Overview

This banking application is designed to streamline and modernize core banking processes. It implements the **microservices pattern** to keep each domain concern (customer management, account management) independently deployable and scalable. Services communicate via an API Gateway and register themselves with a Eureka service discovery server.

---

## Architecture

```
Client
  │
  ▼
┌─────────────────────┐
│     API Gateway      │  :8082
│   (Spring Cloud GW)  │
└────────┬────────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌──────────┐  ┌──────────────────┐
│ Customer │  │  Account         │
│ Service  │  │  Service         │
│  :8080   │  │  :8081           │
└──────────┘  └──────────────────┘
    │                │
    └───────┬────────┘
            ▼
  ┌──────────────────┐
  │  Service Registry │  :8761 (Eureka)
  └──────────────────┘
            │
  ┌──────────────────┐
  │  Config Server    │  (reads from GitHub config repo)
  └──────────────────┘
```

---

## Services

### 1. Service Registry (`ServiceRegistry`)
- **Port:** `8761`
- **Technology:** Spring Cloud Netflix Eureka Server
- **Role:** Acts as the central service discovery hub. All microservices register here on startup, enabling dynamic discovery without hardcoded URLs.
- **Dashboard:** [http://localhost:8761](http://localhost:8761)

---

### 2. Config Server (`ConfigServer`)
- **Technology:** Spring Cloud Config Server
- **Role:** Provides externalized, centralized configuration for all microservices. Pulls properties from a dedicated GitHub repository ([microservices-config](https://github.com/yajatdhand/microservices-config)).
- **Config source:** `application.properties` in the config repo contains shared database credentials, JPA settings, and Eureka registration URL.

---

### 3. API Gateway (`ApiGateway`)
- **Port:** `8082`
- **Technology:** Spring Cloud Gateway
- **Role:** Single entry point for all client requests. Routes traffic to downstream microservices based on path prefixes:
  - `/customer/**` → Customer Management Service
  - `/account/**` → Account Management Service

---

### 4. Customer Management Service (`CustomerManagementService`)
- **Port:** `8080`
- **Technology:** Spring Boot, Spring Data JPA, PostgreSQL
- **Role:** Manages all customer lifecycle operations — create, read, update, and delete customer records.

**Data Model:**

| Field | Type | Description |
|---|---|---|
| `custName` | String | Full name of the customer |
| `custAddress` | String | Residential address |
| `custPhone` | String | Phone number |
| `custDob` | Date | Date of birth (`YYYY-MM-DD`) |

---

### 5. Account Management Service (`AccountManagementService`)
- **Port:** `8081`
- **Technology:** Spring Boot, Spring Data JPA, PostgreSQL
- **Role:** Manages bank accounts linked to customers. Supports querying account details, performing deposits/withdrawals, and deleting accounts.

**Transaction Types:**

| Code | Operation |
|---|---|
| `D` | Deposit |
| `W` | Withdrawal |

---

## Configuration

All shared configuration is stored in the [microservices-config](https://github.com/yajatdhand/microservices-config) repository and served by the Config Server at runtime.

**`application.properties` (shared config):**

```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
eureka.instance.client.serverUrl.defaultZone=http://localhost:8761/eureka/
```

**Database:** PostgreSQL  
**JPA SQL logging:** Enabled (useful for debugging during development)

---

## API Reference

All requests go through the **API Gateway** at `http://localhost:8082`.

### Customer Service Endpoints

#### Add Customer
```
POST http://localhost:8082/customer/addCustomer
Content-Type: application/json

{
  "custName": "Ayush",
  "custAddress": "Jaipur",
  "custPhone": "8876149087",
  "custDob": "2001-01-01"
}
```

#### Update Customer
```
PUT http://localhost:8082/customer/updateCustomer
Content-Type: application/json

{
  "custName": "Ayush Sharma",
  "custAddress": "Mumbai",
  "custPhone": "8876149087",
  "custDob": "2002-01-01"
}
```

#### Get All Customers
```
GET http://localhost:8082/customer/getAllCustomers
```

#### Get Customer by ID
```
GET http://localhost:8082/customer/getCustomer/{id}
```

#### Delete Customer
```
DELETE http://localhost:8082/customer/deleteCustomer/{id}
```

---

### Account Service Endpoints

#### Get Account Details
```
GET http://localhost:8082/account/getAccount?custId=10&accountNumber=14000000990670
```

#### Deposit / Withdraw Money
```
POST http://localhost:8082/account/transaction
Content-Type: application/json

{
  "custId": 11,
  "accountNumber": "14000000029130",
  "transactionType": "W",
  "amount": 100
}
```
> Use `"transactionType": "D"` for deposit, `"W"` for withdrawal.

#### Delete Account
```
DELETE http://localhost:8082/account/deleteAccount?custId=10&accountNumber=14000000990670
```

---

## Running the Project

Start the services **in the following order** to ensure proper registration and config loading:

1. **Config Server** — must be up first so other services can fetch their configuration
2. **Service Registry (Eureka)** — must be up before any service tries to register
3. **Customer Management Service**
4. **Account Management Service**
5. **API Gateway** — start last, after downstream services are registered

**Port Summary:**

| Service | Port | URL |
|---|---|---|
| Eureka Dashboard | 8761 | http://localhost:8761 |
| API Gateway | 8082 | http://localhost:8082 |
| Customer Service | 8080 | http://localhost:8080 |
| Account Service | 8081 | http://localhost:8081 |

**Prerequisites:**
- Java 17+
- Maven
- PostgreSQL running locally with a `postgres` user and `postgres` password
- Internet access (Config Server fetches config from GitHub on startup)
