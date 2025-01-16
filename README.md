# Multi-Service Application with Docker

This project is a distributed application based on Spring Boot, consisting of three main microservices: **Account Service**, **Customer Service**, and **Transaction Service**. The services are containerized using Docker, allowing for streamlined setup and execution.

## Architecture

- **Account Service**: Manages user account information.
- **Customer Service**: Manages customer details.
- **Transaction Service**: Handles financial transactions between accounts.

These services communicate using **HTTP REST APIs** configured via **Feign Clients**.

---

## Technologies Used

- **Spring Boot**: Framework for microservices.
- **Docker**: For containerizing and running services.
- **Spring Cloud OpenFeign**: For inter-service communication.
- **H2 Database**: In-memory database for data storage.
- **Spring Data JPA**: For database interaction.

---

## Requirements

- Docker
- Docker Compose

---

## How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/armsb1/java-challenge-equadis.git
cd <project_directory>
```

### 2. Start All Services with Docker Compose

Run the following command in the root directory (where the `docker-compose.yml` file is located):

```bash
docker-compose up --build
```

This command builds and starts all the microservices in their respective containers. No additional setup is required for JAR files, as the Dockerfiles handle everything.

---

## Service Endpoints and Example Requests

### **1. Customer Service**

**Base URL**: `http://localhost:8081/api/v1/customers`

#### Endpoints:

- **Get all customers**  
  `GET /`
  ```bash
  curl http://localhost:8081/api/v1/customers
  ```

- **Get customer by ID**  
  `GET /{userId}`
  ```bash
  curl http://localhost:8081/api/v1/customers/1
  ```

- **Get customer by user profile ID**  
  `GET /userProfileId/{userProfileId}`
  ```bash
  curl http://localhost:8081/api/v1/customers/userProfileId/1001
  ```

- **Create a new customer**  
  `POST /`
  Request Body:
  ```json
  {
    "userProfileId": 1001,
    "name": "John Doe",
    "email": "johndoe@example.com"
  }
  ```
  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"userProfileId":1001,"name":"John Doe","email":"johndoe@example.com"}' http://localhost:8081/api/v1/customers
  ```

---

### **2. Account Service**
**Base URL**: `http://localhost:8080/api/v1/accounts`

#### Endpoints:

- **Get all accounts**  
  `GET /`
  ```bash
  curl http://localhost:8080/api/v1/accounts
  ```

- **Get account by ID**  
  `GET /{accountId}`
  ```bash
  curl http://localhost:8080/api/v1/accounts/1
  ```

- **Get account by account number**  
  `GET /accountNumber/{accountNumber}`
  ```bash
  curl http://localhost:8080/api/v1/accounts/accountNumber/123456
  ```

- **Get all accounts by user profile ID**  
  `GET /userId/{userProfileId}`
  ```bash
  curl http://localhost:8080/api/v1/accounts/userId/1001
  ```

- **Create a new account**  
  `POST /`
  Request Body:
  ```json
  {
    "accountNumber": 1233456789,
    "accountStatus": "ACTIVE",
    "totalBalance": 5000.00,
    "userProfileId": 1001
  }
  ```
  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"accountStatus":"ACTIVE","totalBalance":5000.00,"userProfileId":1001}' http://localhost:8080/api/v1/accounts
  ```

---

### **3. Transaction Service**

**Base URL**: `http://localhost:8082/api/v1/transactions`

#### Endpoints:

- **Get all transactions**  
  `GET /`
  ```bash
  curl http://localhost:8082/api/v1/transactions
  ```

- **Get transactions by account number**  
  `GET /{accountNumber}`
  ```bash
  curl http://localhost:8082/api/v1/transactions/123456
  ```

- **Deposit funds**  
  `POST /deposit`  
  Request Body:
  ```json
  {
    "referenceId": 1,
    "accountNumber": 123456,
    "amount": 1000.00,
    "comments": "Deposit for savings"
  }
  ```
  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"referenceId":1,"accountNumber":123456,"amount":1000.00,"comments":"Deposit for savings"}' http://localhost:8082/api/v1/transactions/deposit
  ```

- **Withdraw funds**  
  `POST /withdraw`  
  Request Body:
  ```json
  {
    "referenceId": 1,
    "accountNumber": 123456,
    "amount": 500.00,
    "comments": "ATM withdrawal"
  }
  ```
  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"referenceId":1,"accountNumber":123456,"amount":500.00,"comments":"ATM withdrawal"}' http://localhost:8082/api/v1/transactions/withdraw
  ```

---

## Project Structure

```
.
├── account-service
│   ├── Dockerfile
│   ├── src
│   └── target
├── customer-service
│   ├── Dockerfile
│   ├── src
│   └── target
├── transaction-service
│   ├── Dockerfile
│   ├── src
│   └── target
├── docker-compose.yml
└── README.md
```
```