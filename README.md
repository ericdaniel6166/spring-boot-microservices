# spring-boot-microservices

## Prerequisite
- Java 17
- Maven
- Docker
- Microservice repositories
  - spring-boot-microservices : https://github.com/ericdaniel6166/spring-boot-microservices
  - discovery-server : https://github.com/ericdaniel6166/discovery-server
  - api-gateway : https://github.com/ericdaniel6166/api-gateway
  - product-service : https://github.com/ericdaniel6166/product-service
  - order-service : https://github.com/ericdaniel6166/order-service
  - inventory-service : https://github.com/ericdaniel6166/inventory-service

```bash
.
├── spring-boot-microservices
├── discovery-server
├── api-gateway
├── product-service
├── order-service
└── inventory-service
```
## Environment setup

```bash
# Docker compose for local DB, keycloak, zipkin (run in spring-boot-microservices location)
docker compose up

# Build Maven project for all projects
mvn clean install -DskipTests
```

## Installation

## Running the app

```bash
# Start discovery-server, api-gateway 

# Start microservice   
```

## Test

## Migrations