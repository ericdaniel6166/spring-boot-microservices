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
        - kafka producer
    - inventory-service : https://github.com/ericdaniel6166/inventory-service
    - notification-service : https://github.com/ericdaniel6166/notification-service
        - kafka consumer

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

[![](https://mermaid.ink/img/pako:eNqdVV1vmzAU_SuWnxqVRJBBmvFQKVOzLVKbVWu1hwkJuXCTWgE7sw1aGuW_z-B8EUhTxpOxOeeee8_leo0jHgP2sYQ_GbAI7iiZC5IGDOknSigw1b29veYiBtGVIHIagY-WCYngqtz8WQCl6hhE5TsNPMHlJKExUbAFfZgh3L6H8YuPJMm3sTvIYBhXgHgOooq20CkYGTRSYXliIamIyiR6HE_vJtNvAWuv4alk-E6l4mLV-U892_2SKnw1XHVxmrlZ34LMFkRTAYuNqEe9omw-zrV9jXXVoK5x10dHaVyQ34A4LV0ppegYyorgOo-D-xFnMkvhrMQaRPM00OyaqIS2IjuIqpg5BzVhM361P7XQUvA4i1TNzxqrhc5yopngKaqTnug7gpW2NGS83ypk7irdsloTBekoJzQhL0mDBVY1SGcXhSQKUWlYzM652PUmFDwCKY98MQSQSCg4dVU_wtutERepTLnaZ3Ns-3EPVodPpfsuUZBI0ZwoqP4Bh_PTP_C6NiKyZVH17Zw6ANuMBkNRG1ZfR5P7cPI8fginP57D0S_9OvpyPz7EaCPz_UHWSrF_eZS1kB7Dex7oXkDYwimIlNBY31_rYj_A6hVSCLCvlzERiwAHbKO_I5niTysWYV-JDCxsKru967A_I7olLbwkDPtr_Bf7ztDteUN30Lc9z76xPcez8Ar7Xq8_9Nz-wB3YN47z2f7kbSz8xrmmcHq2efqu69mO4w4sDDHV2T-YC7a8Z8sYv0tAIWTzD7FDmtg?type=png)](https://mermaid.live/edit#pako:eNqdVV1vmzAU_SuWnxqVRJBBmvFQKVOzLVKbVWu1hwkJuXCTWgE7sw1aGuW_z-B8EUhTxpOxOeeee8_leo0jHgP2sYQ_GbAI7iiZC5IGDOknSigw1b29veYiBtGVIHIagY-WCYngqtz8WQCl6hhE5TsNPMHlJKExUbAFfZgh3L6H8YuPJMm3sTvIYBhXgHgOooq20CkYGTRSYXliIamIyiR6HE_vJtNvAWuv4alk-E6l4mLV-U892_2SKnw1XHVxmrlZ34LMFkRTAYuNqEe9omw-zrV9jXXVoK5x10dHaVyQ34A4LV0ppegYyorgOo-D-xFnMkvhrMQaRPM00OyaqIS2IjuIqpg5BzVhM361P7XQUvA4i1TNzxqrhc5yopngKaqTnug7gpW2NGS83ypk7irdsloTBekoJzQhL0mDBVY1SGcXhSQKUWlYzM652PUmFDwCKY98MQSQSCg4dVU_wtutERepTLnaZ3Ns-3EPVodPpfsuUZBI0ZwoqP4Bh_PTP_C6NiKyZVH17Zw6ANuMBkNRG1ZfR5P7cPI8fginP57D0S_9OvpyPz7EaCPz_UHWSrF_eZS1kB7Dex7oXkDYwimIlNBY31_rYj_A6hVSCLCvlzERiwAHbKO_I5niTysWYV-JDCxsKru967A_I7olLbwkDPtr_Bf7ztDteUN30Lc9z76xPcez8Ar7Xq8_9Nz-wB3YN47z2f7kbSz8xrmmcHq2efqu69mO4w4sDDHV2T-YC7a8Z8sYv0tAIWTzD7FDmtg)

```
sequenceDiagram
    client->>+order-service: getOrderStatus(orderId)
    order-service->>+order_service_db:getOrderStatus(orderId)
    order_service_db-->>-order-service: orderStatus
    order-service-->>-client: orderStatus
```

[![](https://mermaid.ink/img/pako:eNqFkU1PwzAMhv_K5BOItKJr03Y57MSFA-KwG4o0hcSMiDUZaYIYVf87acPXEBI-WNGb97GdeABpFQKDHp8DGolXWuyc6LhZxJB7jcZn6_WFdQpd1qN70RLZYof-dlI2XvjQn8231-o8USfeL3j7IWzVPfsf_-HOYonsV3_7Tf_VcyLS7CdWINCh64RW8cHDBHLwj9ghBxaPSrgnDtyM0SeCt5ujkcC8C0ggHJTwn58D7EHs-6gehAE2wCuwps7rqi1pkTJdETgCK4o2L8tlVTS0TXkk8GZtrHCZr1JQWjdVtWwIoNLeupu0j3ktc4e72T-NMb4DMOyNzw?type=png)](https://mermaid.live/edit#pako:eNqFkU1PwzAMhv_K5BOItKJr03Y57MSFA-KwG4o0hcSMiDUZaYIYVf87acPXEBI-WNGb97GdeABpFQKDHp8DGolXWuyc6LhZxJB7jcZn6_WFdQpd1qN70RLZYof-dlI2XvjQn8231-o8USfeL3j7IWzVPfsf_-HOYonsV3_7Tf_VcyLS7CdWINCh64RW8cHDBHLwj9ghBxaPSrgnDtyM0SeCt5ujkcC8C0ggHJTwn58D7EHs-6gehAE2wCuwps7rqi1pkTJdETgCK4o2L8tlVTS0TXkk8GZtrHCZr1JQWjdVtWwIoNLeupu0j3ktc4e72T-NMb4DMOyNzw)