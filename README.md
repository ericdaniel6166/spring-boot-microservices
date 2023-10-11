# spring-boot-microservices

## List what has been used

- [Spring Boot](https://spring.io/projects/spring-boot) web framework, makes it easy to create stand-alone,
  production-grade Spring based Applications
- [Apache Kafka](https://kafka.apache.org/) distributed and fault-tolerant stream processing system.
- [Spring Cloud Netflix Eureka](https://spring.io/projects/spring-cloud-netflix) service discovery, allows services to
  find and communicate with each other without hard-coding the hostname and port
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) api gateway, provide a simple, yet effective
  way to route to APIs and provide cross-cutting concerns to them such as: security, monitoring/metrics, and resiliency.
- [Resilience4j circuit breaker](https://github.com/resilience4j/resilience4j) library, helps prevent cascading failures
  and provides mechanisms for graceful degradation and self-healing when external services experience issues
- [Zipkin](https://zipkin.io/) open source, end-to-end distributed tracing
- [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth) autoconfiguration for distributed tracing
- [Docker](https://www.docker.com/) and docker-compose
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) provides repository support for the Jakarta Persistence
  API
- [Flywaydb](https://flywaydb.org/) for migrations
- [Keycloak](https://www.keycloak.org/) for providing authentication, user management, fine-grained authorization
- [PostgreSQL](https://www.postgresql.org/)

## Prerequisite

- Java 17
- Maven
- Docker
- GNU Make

## Setup

- Microservice repositories
    - spring-boot-microservices : https://github.com/ericdaniel6166/spring-boot-microservices
        - shared configuration files, components, etc. that can be reused in other microservices (order-service,
          inventory-service, etc)
    - discovery-server : https://github.com/ericdaniel6166/discovery-server
    - api-gateway : https://github.com/ericdaniel6166/api-gateway
    - product-service : https://github.com/ericdaniel6166/product-service
        - cursor pagination
    - order-service : https://github.com/ericdaniel6166/order-service
        - kafka
    - inventory-service : https://github.com/ericdaniel6166/inventory-service
        - kafka
    - payment-service : https://github.com/ericdaniel6166/payment-service
        - kafka
    - notification-service : https://github.com/ericdaniel6166/notification-service
        - kafka

- Make sure microservice repositories at directory as below, otherwise should change this for dev-environment setup

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

## Running the app

##### Run command in spring-boot-microservices directory

- Docker environment

```bash
# Docker compose up
make up

# Docker compose down
make down
```

- Non Docker / standalone environment

```bash
# Docker compose up
make local-up

# Start discovery-server, api-gateway 

# Start microservice   

# Docker compose down
make local-down
```

## Sequence diagrams (Updating...)

- Using mermaid.live

```
sequenceDiagram
    client->>+order-service: place(orderRequest)
    order-service->>order-service: validateRequest(orderRequest)
    order-service->>order_service_db: save(order) 
    note over order-service, order_service_db : save t_order, status PENDING

    order-service->>order_service_db: save(orderStatusHistory)
    note over order-service, order_service_db : save order_status_history, status PENDING
   
    order-service->>kafka: send(orderPendingEvent)
    order-service-->>-client: orderStatus
    note over order-service, client: orderStatus PENDING

    kafka->>+inventory-service: consume(orderPendingEvent)
    inventory-service->>inventory-service: validateEvent(orderPendingEvent)
    inventory-service->>+inventory_service_db: getInfo(inventory, product)
    note over inventory-service, inventory_service_db: get from inventory, product
    inventory_service_db-->>-inventory-service: inventoryInfo

    inventory-service->>inventory-service: validateItemAvailable(orderPendingEvent, inventoryInfo)

    alt is valid
        inventory-service->>kafka: send(orderProcessingEvent)
        kafka->>order-service:consume(orderProcessingEvent)
        activate order-service
        order-service->>+order_service_db:update(order)
        note over order-service, order_service_db : update t_order, status PROCESSING
        
        order-service->>+order_service_db:save(orderStatusHistory)
        note over order-service, order_service_db:save order_status_history, status PROCESSING
        
        deactivate order-service


    else is not valid
        inventory-service->>-kafka: send(orderItemNotAvailableEvent)
        kafka->>order-service:consume(orderItemNotAvailableEvent)
        activate order-service
        order-service->>+order_service_db:update(order)
        note over order-service, order_service_db : update t_order, status FAIL_ITEM_NOT_AVAILABLE
        
        order-service->>+order_service_db:save(orderStatusHistory)
        note over order-service, order_service_db:save order_status_history, status FAIL_ITEM_NOT_AVAILABLE
        
        deactivate order-service
    end 
```

[![](https://mermaid.ink/img/pako:eNrVVl1v2jAU_SuWn4oaUCgLhTxUYivbkFpalWoPU6TITS40IrFZ7ERjiP8-x-YrXy3p0-an5Nrn3ON7jy1vsMd8wDbm8CsB6sFtQBYxiRyK5PDCAKho39xcstiHuM0hTgMPbLQKiQcXKviUAbloaURunQQWcCkJA58I2IHOZnB3_67_YiNO0l3uFtIYygQglkKcRxuoCEYajYSrZgzEBREJR4_j6e1k-s2hzTXMFMP3gAsWr1sf1LOLKyr3VXOVxUnman1LMl8SSQXU16Ie5VdAF-NUtq-yrhLU1t210ck23pFfgSiWTknJHBPQLLncx7H7HqM8iaBWYgkieSpo9iZS0EZkR1G5Zi5ATOicXRxmDbSKmZ94otTPEquBajnRPGYRKpMW9J3AVFsqdnwIZTL3lW5YrYmAaJSSICQvYUULjHyS1j4LCQUKuGbRkbrcZRPGzAPOC305NUn-dsjboxZMPBGkREDenMf54uG4LJ3eZJUVZHeFHIFNTq2mKN8jTw9fxrPZ_rSq0UTZ29dKI5H2GRfLm2p9qKv03hsQcsjMITWdY5B2ySGZJ6dMHGz5AZ-8R_Evu-XraHLnTp7H9-704dkd_ZC_o8934__BOg2k1_tIeYj6CBs4gjgigS8fIpss7mDxChE42JafPomXDnboVq4jiWCzNfWwLeIEDKwru3u0YHtOpCUNvCIU2xv8G9tXvWHHNM1B37QGVnd4PewZeI3tTx2ra12Zvf51d9Azu73-1sB_GJMMZmeoh2UNza45kOvBD-Te7_U7ST2XVIafan0mY_sXTnw17g?type=png)](https://mermaid.live/edit#pako:eNrVVl1v2jAU_SuWn4oaUCgLhTxUYivbkFpalWoPU6TITS40IrFZ7ERjiP8-x-YrXy3p0-an5Nrn3ON7jy1vsMd8wDbm8CsB6sFtQBYxiRyK5PDCAKho39xcstiHuM0hTgMPbLQKiQcXKviUAbloaURunQQWcCkJA58I2IHOZnB3_67_YiNO0l3uFtIYygQglkKcRxuoCEYajYSrZgzEBREJR4_j6e1k-s2hzTXMFMP3gAsWr1sf1LOLKyr3VXOVxUnman1LMl8SSQXU16Ie5VdAF-NUtq-yrhLU1t210ck23pFfgSiWTknJHBPQLLncx7H7HqM8iaBWYgkieSpo9iZS0EZkR1G5Zi5ATOicXRxmDbSKmZ94otTPEquBajnRPGYRKpMW9J3AVFsqdnwIZTL3lW5YrYmAaJSSICQvYUULjHyS1j4LCQUKuGbRkbrcZRPGzAPOC305NUn-dsjboxZMPBGkREDenMf54uG4LJ3eZJUVZHeFHIFNTq2mKN8jTw9fxrPZ_rSq0UTZ29dKI5H2GRfLm2p9qKv03hsQcsjMITWdY5B2ySGZJ6dMHGz5AZ-8R_Evu-XraHLnTp7H9-704dkd_ZC_o8934__BOg2k1_tIeYj6CBs4gjgigS8fIpss7mDxChE42JafPomXDnboVq4jiWCzNfWwLeIEDKwru3u0YHtOpCUNvCIU2xv8G9tXvWHHNM1B37QGVnd4PewZeI3tTx2ra12Zvf51d9Azu73-1sB_GJMMZmeoh2UNza45kOvBD-Te7_U7ST2XVIafan0mY_sXTnw17g)

```
sequenceDiagram
    client->>+order-service: getOrderStatus(orderId)
    order-service->>+order_service_db:getOrderStatus(orderId)
    order_service_db-->>-order-service: orderStatus
    order-service-->>-client: orderStatus
```

[![](https://mermaid.ink/img/pako:eNqFkU1PwzAMhv_K5BOItKJr03Y57MSFA-KwG4o0hcSMiDUZaYIYVf87acPXEBI-WNGb97GdeABpFQKDHp8DGolXWuyc6LhZxJB7jcZn6_WFdQpd1qN70RLZYof-dlI2XvjQn8231-o8USfeL3j7IWzVPfsf_-HOYonsV3_7Tf_VcyLS7CdWINCh64RW8cHDBHLwj9ghBxaPSrgnDtyM0SeCt5ujkcC8C0ggHJTwn58D7EHs-6gehAE2wCuwps7rqi1pkTJdETgCK4o2L8tlVTS0TXkk8GZtrHCZr1JQWjdVtWwIoNLeupu0j3ktc4e72T-NMb4DMOyNzw?type=png)](https://mermaid.live/edit#pako:eNqFkU1PwzAMhv_K5BOItKJr03Y57MSFA-KwG4o0hcSMiDUZaYIYVf87acPXEBI-WNGb97GdeABpFQKDHp8DGolXWuyc6LhZxJB7jcZn6_WFdQpd1qN70RLZYof-dlI2XvjQn8231-o8USfeL3j7IWzVPfsf_-HOYonsV3_7Tf_VcyLS7CdWINCh64RW8cHDBHLwj9ghBxaPSrgnDtyM0SeCt5ujkcC8C0ggHJTwn58D7EHs-6gehAE2wCuwps7rqi1pkTJdETgCK4o2L8tlVTS0TXkk8GZtrHCZr1JQWjdVtWwIoNLeupu0j3ktc4e72T-NMb4DMOyNzw)