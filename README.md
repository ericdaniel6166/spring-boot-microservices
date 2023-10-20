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
├── payment-service
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

##### Swagger

http://localhost:8181/swagger-ui.html
![img.png](img.png)
![img_1.png](img_1.png)

## Sequence diagrams (Updating...)

- Using mermaid.live

```
sequenceDiagram
    client->>+order-service: place(orderRequest)
    order-service->>order-service: validateRequest(orderRequest)
    order-service->>order_service_db: saveOrder() 
    note over order-service, order_service_db : save t_order, status PENDING

    order-service->>order_service_db: saveOrderStatusHistory()
    note over order-service, order_service_db : save order_status_history, status PENDING
   
    order-service->>kafka: send(orderPendingEvent)
    order-service-->>-client: orderStatus
    note over order-service, client: orderStatus PENDING

    kafka->>+inventory-service: consume(orderPendingEvent)
    inventory-service->>inventory-service: validateEvent(orderPendingEvent)
    inventory-service->>+inventory_service_db: getInventoryInfo()
    note over inventory-service, inventory_service_db: get from inventory, product
    inventory_service_db-->>-inventory-service: inventoryInfo

    inventory-service->>inventory-service: validateItemAvailable(orderPendingEvent, inventoryInfo)

    alt is valid
        inventory-service->>kafka: send(orderProcessingEvent)
        kafka->>order-service:consume(orderProcessingEvent)
        activate order-service
        order-service->>+order_service_db:updateOrder()
        note over order-service, order_service_db : update t_order, status PROCESSING
        
        order-service->>+order_service_db:saveOrderStatusHistory()
        note over order-service, order_service_db:save order_status_history, status PROCESSING
        
        deactivate order-service


    else is not valid
        inventory-service->>-kafka: send(orderItemNotAvailableEvent)
        kafka->>order-service:consume(orderItemNotAvailableEvent)
        activate order-service
        order-service->>+order_service_db:updateOrder()
        note over order-service, order_service_db : update t_order, status ITEM_NOT_AVAILABLE
        
        order-service->>+order_service_db:saveOrderStatusHistory()
        note over order-service, order_service_db:save order_status_history, status ITEM_NOT_AVAILABLE
        
        deactivate order-service
    end 
```

[![](https://mermaid.ink/img/pako:eNrVVtFu2jAU_RXLT6AGRBIYIQ-V2Io2pBaqUu1hioTc5EItEpslTjSG-Pc5NhmEQEv6tOXJufY5Pr73-Mpb7PMAsIsT-JkC8-GOkmVMIo8h-fkhBSZat7c3PA4gbiUQZ9QHF61D4kNDBZ9yYCKaGlFaJ4EnuIyENCAC9qCrGeb7_3nw4qKEZDDNo40m0hjGBSCeQVxGG-gUjDQaibmaMVAiiEgT9Dia3I0nXz1WW8NMEXyjieDxptH8oJ59XHHNXzVZVZxkPq9vRRYrIqmABTqlj3JE2XKUyfKdzasEtXR1XT2jz_GO_DOI09QpKbljKMs3l-c4VN_nLEkjuCixApE8Z2gKEyloLbKDqFIxlyDGxcSYLXi1jBUyA12kQouYR4dpA61jHqS-OJF1BFPVOHNQeiyqSHDNJI0FRMOM0JC8hGcyb5Q3aRa7kFAgmmgWHbm0d9V7MfchSU7KceyNclMou-IimPiCZkRA2ZOH-dM7cVO5tOk6T8i-cxyAdS6rpqi2j6fpl9FsVlxS9dVR9nY3qSXSvaKfvKk2gEuZLrwBYQK5OaSmawzSqjgk9-SEi7-2_IBP3qP4l90yfh49zCfT5_nw-3B8P_x8P_ofXHOd6svuUc5hAcIGjiCOCA3kq2Obxz0sXiECD7tyGJB45WGP7eQ6kgo-2zAfuyJOwcA6n_sXCnYXRBrRwGvCsLvFv7Dbsqxu23IGjmk6nZ5t2n3LwBsZt-1u2-45jj1wela_Z9o7A__mXHKY7UHfdD51zU7X6ncGds_AEFB58Af9LFKvI7XHD7U-F7L7A0ZoLxc?type=png)](https://mermaid.live/edit#pako:eNrVVtFu2jAU_RXLT6AGRBIYIQ-V2Io2pBaqUu1hioTc5EItEpslTjSG-Pc5NhmEQEv6tOXJufY5Pr73-Mpb7PMAsIsT-JkC8-GOkmVMIo8h-fkhBSZat7c3PA4gbiUQZ9QHF61D4kNDBZ9yYCKaGlFaJ4EnuIyENCAC9qCrGeb7_3nw4qKEZDDNo40m0hjGBSCeQVxGG-gUjDQaibmaMVAiiEgT9Dia3I0nXz1WW8NMEXyjieDxptH8oJ59XHHNXzVZVZxkPq9vRRYrIqmABTqlj3JE2XKUyfKdzasEtXR1XT2jz_GO_DOI09QpKbljKMs3l-c4VN_nLEkjuCixApE8Z2gKEyloLbKDqFIxlyDGxcSYLXi1jBUyA12kQouYR4dpA61jHqS-OJF1BFPVOHNQeiyqSHDNJI0FRMOM0JC8hGcyb5Q3aRa7kFAgmmgWHbm0d9V7MfchSU7KceyNclMou-IimPiCZkRA2ZOH-dM7cVO5tOk6T8i-cxyAdS6rpqi2j6fpl9FsVlxS9dVR9nY3qSXSvaKfvKk2gEuZLrwBYQK5OaSmawzSqjgk9-SEi7-2_IBP3qP4l90yfh49zCfT5_nw-3B8P_x8P_ofXHOd6svuUc5hAcIGjiCOCA3kq2Obxz0sXiECD7tyGJB45WGP7eQ6kgo-2zAfuyJOwcA6n_sXCnYXRBrRwGvCsLvFv7Dbsqxu23IGjmk6nZ5t2n3LwBsZt-1u2-45jj1wela_Z9o7A__mXHKY7UHfdD51zU7X6ncGds_AEFB58Af9LFKvI7XHD7U-F7L7A0ZoLxc)

```
sequenceDiagram
    kafka ->>+payment-service:consume(orderProcessingEvent)
    payment-service->>payment-service:validateEvent(orderProcessingEvent)
    payment-service->>payment-service:calculateOrder()
    payment-service->>payment_service_db:savePayment()
    note over payment-service, payment_service_db : save payment, status PAYMENT_PROCESSING
    payment-service->>payment_service_db:savePaymentStatusHistory()
    note over payment-service, payment_service_db : save payment_status_history, status PAYMENT_PROCESSING
    payment-service->>-kafka: send(orderPaymentProcessingEvent)


```

[![](https://mermaid.ink/img/pako:eNqtk8FOg0AQhl-FzEkjbYBSaPfQxGijHtoS8aIhIStMW1LYrbsLEZu-uwu0FzyYVOe0mZ3_2z87MwdIeIpAQOJHiSzB-4xuBC0iZujY0fWOGoPZ7GZP6wKZGkgUVZYgSTiTZYFXXKQoAsETlDJjm3mli647cU-iKX1IRfMspQpb1d9QCc2TMtesVUO5-k0XnzJx-k4krTDo0mcd4woNXqHoE0zjJ8AgRoM435iGVFSV0ghuXxfz5UscPK_u5mH4tHy4zFTY4h4zqbio_8Nh3BmMtx3yAsODdjA0FVl66ltX028fmFCgKGiW6hE7NLgI1BYLjIDoY0rFLoKIHXUdLRUPa5YAUaJEE8p9MxqncQSyprnU2T1lQA7wCWQ6Htoj23LHjmtPrKnnmVADsZ2h4_ve1LYt33Ndxx8dTfjiXBOs4bSL8cS1bN_WAkwz_QOLbgPaRWhfeGvrGxvHbyBgFnU?type=png)](https://mermaid.live/edit#pako:eNqtk8FOg0AQhl-FzEkjbYBSaPfQxGijHtoS8aIhIStMW1LYrbsLEZu-uwu0FzyYVOe0mZ3_2z87MwdIeIpAQOJHiSzB-4xuBC0iZujY0fWOGoPZ7GZP6wKZGkgUVZYgSTiTZYFXXKQoAsETlDJjm3mli647cU-iKX1IRfMspQpb1d9QCc2TMtesVUO5-k0XnzJx-k4krTDo0mcd4woNXqHoE0zjJ8AgRoM435iGVFSV0ghuXxfz5UscPK_u5mH4tHy4zFTY4h4zqbio_8Nh3BmMtx3yAsODdjA0FVl66ltX028fmFCgKGiW6hE7NLgI1BYLjIDoY0rFLoKIHXUdLRUPa5YAUaJEE8p9MxqncQSyprnU2T1lQA7wCWQ6Htoj23LHjmtPrKnnmVADsZ2h4_ve1LYt33Ndxx8dTfjiXBOs4bSL8cS1bN_WAkwz_QOLbgPaRWhfeGvrGxvHbyBgFnU)

```
sequenceDiagram
    client->>+order-service: getOrderStatus()
    order-service->>+order_service_db:getOrderStatus()
    order_service_db-->>-order-service: orderStatus
    order-service-->>-client: orderStatus
```

[![](https://mermaid.ink/img/pako:eNp9kc1OwzAQhF-l2hOIJLITO8E-9MQVcegNRaqMvZSIxi6OgyhR3h0n4a8VYk-r0Xw7a-8A2hkECR2-9Gg13jRq51Vb21UsvW_QhnS9vnLeoE879K-NRrnaYbiblE1Qoe8uLhf7iemb2n4KW_Mg_-F-2dLIpmeJ7gf7K2wilm1PrJBAi75VjYlPHCawhvCELdYgY2uUf66htmP0qT64zdFqkMH3mEB_MCp8fQfIR7XvonpQFuQAbyAFz2hBCeM5o9dElGUCR5A0z_KqKgWlpCoZy6tiTODduTiBZGIpRgrBC87zBNA0wfnb5QTzJeaI-xmY9hg_AKxjiFc?type=png)](https://mermaid.live/edit#pako:eNp9kc1OwzAQhF-l2hOIJLITO8E-9MQVcegNRaqMvZSIxi6OgyhR3h0n4a8VYk-r0Xw7a-8A2hkECR2-9Gg13jRq51Vb21UsvW_QhnS9vnLeoE879K-NRrnaYbiblE1Qoe8uLhf7iemb2n4KW_Mg_-F-2dLIpmeJ7gf7K2wilm1PrJBAi75VjYlPHCawhvCELdYgY2uUf66htmP0qT64zdFqkMH3mEB_MCp8fQfIR7XvonpQFuQAbyAFz2hBCeM5o9dElGUCR5A0z_KqKgWlpCoZy6tiTODduTiBZGIpRgrBC87zBNA0wfnb5QTzJeaI-xmY9hg_AKxjiFc)