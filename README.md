# spring-boot-microservices

## List what has been used
- [Spring Boot](https://spring.io/projects/spring-boot) web framework, makes it easy to create stand-alone, production-grade Spring based Applications
- [Apache Kafka](https://kafka.apache.org/) distributed and fault-tolerant stream processing system.
- [Spring Cloud Netflix Eureka](https://spring.io/projects/spring-cloud-netflix) service discovery, allows services to find and communicate with each other without hard-coding the hostname and port
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) api gateway, provide a simple, yet effective way to route to APIs and provide cross-cutting concerns to them such as: security, monitoring/metrics, and resiliency.
- [Resilience4j circuit breaker](https://github.com/resilience4j/resilience4j) library, helps prevent cascading failures and provides mechanisms for graceful degradation and self-healing when external services experience issues
- [Zipkin](https://zipkin.io/) open source, end-to-end distributed tracing
- [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth) autoconfiguration for distributed tracing
- [Docker](https://www.docker.com/) and docker-compose
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) provides repository support for the Jakarta Persistence API
- [Flywaydb](https://flywaydb.org/) for migrations
- [Keycloak](https://www.keycloak.org/) for providing authentication, user management, fine-grained authorization
- [PostgreSQL](https://www.postgresql.org/)

## Prerequisite
- Java 17
- Maven
- Docker
- GNU Make
- Microservice repositories
  - spring-boot-microservices : https://github.com/ericdaniel6166/spring-boot-microservices
    - shared configuration files, components, etc. that can be reused in other microservices (order-service, inventory-service, etc)
  - discovery-server : https://github.com/ericdaniel6166/discovery-server
  - api-gateway : https://github.com/ericdaniel6166/api-gateway
  - product-service : https://github.com/ericdaniel6166/product-service
  - order-service : https://github.com/ericdaniel6166/order-service
  - inventory-service : https://github.com/ericdaniel6166/inventory-service
  - notification-service : https://github.com/ericdaniel6166/notification-service

```bash
.
├── spring-boot-microservices
├── discovery-server
├── api-gateway
├── product-service
├── order-service
├── inventory-service
└── notification-service
```
## Environment setup

```bash
# Build all microservices and Docker compose up for local DB, keycloak, zipkin, zookeeper, kafka
make up
```

## Running the app

```bash
# Start discovery-server, api-gateway 

# Start microservice   

# Docker compose down
make down
```
