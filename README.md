# spring-boot-microservices

## List what has been used

- [Spring Boot](https://spring.io/projects/spring-boot) web framework, makes it easy to create stand-alone,
  production-grade Spring based Applications
- [Apache Kafka](https://kafka.apache.org/) distributed and fault-tolerant stream processing system.
- [Spring Cloud Netflix Eureka](https://spring.io/projects/spring-cloud-netflix) service discovery, 
allows services to find and communicate with each other without hard-coding the hostname and port
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) api gateway, provide a simple, yet effective
  way to route to APIs and provide cross-cutting concerns to them such as: security, monitoring/metrics, and resiliency.
- [Resilience4j](https://github.com/resilience4j/resilience4j) library, helps prevent cascading failures
  and provides mechanisms for graceful degradation and self-healing when external services experience issues
- [Zipkin](https://zipkin.io/) distributed tracing system, provides end-to-end visibility into how requests flow through the system, 
helping troubleshoot issues in distributed architectures
- ~~Spring Cloud Sleuth, autoconfiguration for distributed tracing~~
- [Micrometer Tracing](https://micrometer.io/docs/tracing) with Brave, library for distributed tracing (update to Spring Boot 3.x)
- [Docker](https://www.docker.com/) and docker-compose, for containerization
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) provides repository support for the Jakarta Persistence API
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
    - order-service : https://github.com/ericdaniel6166/order-service
    - inventory-service : https://github.com/ericdaniel6166/inventory-service
    - payment-service : https://github.com/ericdaniel6166/payment-service
    - notification-service : https://github.com/ericdaniel6166/notification-service

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
  [![](https://mermaid.ink/img/pako:eNrVV11v2jAU_SuWn0ANFQFKaR4qsRVtSOVDBU3ahBS5yYVaJDZLnGgU8d_nxKGQL760hy4PCGyfk3PvPdc2G2xxG7CBffgdALPgiZKFR9wZQ_KxHApM1B4fb7hng1fzwQupBQZaOcSCSjz4EgF9UVWI1DoJzOBC4lCbCEhAZzOYyW_TfjWQT0IYRaOVKlIYxgUgHoKXRmsoC0YKjYQZz2jIF0QEPhr3hk_94bcZu1jDJCb4Tn3BvXWleqWeZDzmMt8UWV6cZC7WtyTzJZFUwGyV0rH8RtmiF8ryFeZVgmqquoaaUXGckF-AyKYulhI5hrLo5TKOffUtzvzAhVKJ6jMHTLGlqrAA0d9N9Nmc5_OfI9NQKRWae9zdT2to5XE7sERG1gEsTmNBoPRQ1C4zRXEVYHct0hfgdkNCHfLqQBSYYiGOQNRXq9RIGXfeFB63wPczST8sWrpb0-UqBRNL0JAISJtlP581602um4JVFHDS0nvgJV2kKPJ9_TL62ptMdt2D9i47T9nxNr9IpHFGox9Va0NZptUacHyIrCEVnWOPWs4fkeOGXHyY7gqXnKL4zF7pT3sDcziamt0f3f5z98tz73_wzHmqy70TO4fZKLV_oyiwFVm70el_wXaQgUiWHAlxrMDJVrAUl83FWA3n9_kMg4byBLuzNpnZd13356A3nJrZ7rtU1ImbwOUKS7eJswXnezzRWly-j9ofOweOERwz2VWt_S-OgJJsfbI7XonKssbFGnbBcwm15d19E62dYfEGLsywIb_axFvO8Ixt5ToSCD5ZMwsbwgtAwypPyT1_N7giDBsb_AcbjVb7tl5v6Pd6vaPrD52WhtfYaN629PtGR-_U283mw11T32r4nXOJ1-Vq9dy1W512vSkBYFMZ3UD9s5D2mdMFjt_xK4ZEr9z-BaOLWqc?type=png)](https://mermaid.live/edit#pako:eNrVV11v2jAU_SuWn0ANFQFKaR4qsRVtSOVDBU3ahBS5yYVaJDZLnGgU8d_nxKGQL760hy4PCGyfk3PvPdc2G2xxG7CBffgdALPgiZKFR9wZQ_KxHApM1B4fb7hng1fzwQupBQZaOcSCSjz4EgF9UVWI1DoJzOBC4lCbCEhAZzOYyW_TfjWQT0IYRaOVKlIYxgUgHoKXRmsoC0YKjYQZz2jIF0QEPhr3hk_94bcZu1jDJCb4Tn3BvXWleqWeZDzmMt8UWV6cZC7WtyTzJZFUwGyV0rH8RtmiF8ryFeZVgmqquoaaUXGckF-AyKYulhI5hrLo5TKOffUtzvzAhVKJ6jMHTLGlqrAA0d9N9Nmc5_OfI9NQKRWae9zdT2to5XE7sERG1gEsTmNBoPRQ1C4zRXEVYHct0hfgdkNCHfLqQBSYYiGOQNRXq9RIGXfeFB63wPczST8sWrpb0-UqBRNL0JAISJtlP581602um4JVFHDS0nvgJV2kKPJ9_TL62ptMdt2D9i47T9nxNr9IpHFGox9Va0NZptUacHyIrCEVnWOPWs4fkeOGXHyY7gqXnKL4zF7pT3sDcziamt0f3f5z98tz73_wzHmqy70TO4fZKLV_oyiwFVm70el_wXaQgUiWHAlxrMDJVrAUl83FWA3n9_kMg4byBLuzNpnZd13356A3nJrZ7rtU1ImbwOUKS7eJswXnezzRWly-j9ofOweOERwz2VWt_S-OgJJsfbI7XonKssbFGnbBcwm15d19E62dYfEGLsywIb_axFvO8Ixt5ToSCD5ZMwsbwgtAwypPyT1_N7giDBsb_AcbjVb7tl5v6Pd6vaPrD52WhtfYaN629PtGR-_U283mw11T32r4nXOJ1-Vq9dy1W512vSkBYFMZ3UD9s5D2mdMFjt_xK4ZEr9z-BaOLWqc)

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
    
    inventory-service->>+inventory_service_db: getInventoryInfo()
    note over inventory-service, inventory_service_db: get from inventory, product
    inventory_service_db-->>-inventory-service: inventoryInfo

    inventory-service->>inventory-service: validateItemAvailable()

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

    kafka ->>+payment-service:consume(orderProcessingEvent)
    payment-service->>payment-service:calculateOrder()
    payment-service->>payment_service_db:savePayment()
    note over payment-service, payment_service_db : save payment, status PAYMENT_PROCESSING
    payment-service->>payment_service_db:savePaymentStatusHistory()
    note over payment-service, payment_service_db : save payment_status_history, status PAYMENT_PROCESSING
    payment-service->>-kafka: send(orderPaymentProcessingEvent)
    kafka ->>order-service:consume(orderPaymentProcessingEvent)
    activate order-service
    order-service->>+order_service_db:updateOrder()
    note over order-service, order_service_db : update t_order, status PAYMENT_PROCESSING
    order-service->>order_service_db: saveOrderStatusHistory()
    note over order-service, order_service_db : save order_status_history, status PAYMENT_PROCESSING
    deactivate order-service
```

[![](https://mermaid.ink/img/pako:eNp9kc1OwzAQhF-l2hOIJLITO8E-9MQVcegNRaqMvZSIxi6OgyhR3h0n4a8VYk-r0Xw7a-8A2hkECR2-9Gg13jRq51Vb21UsvW_QhnS9vnLeoE879K-NRrnaYbiblE1Qoe8uLhf7iemb2n4KW_Mg_-F-2dLIpmeJ7gf7K2wilm1PrJBAi75VjYlPHCawhvCELdYgY2uUf66htmP0qT64zdFqkMH3mEB_MCp8fQfIR7XvonpQFuQAbyAFz2hBCeM5o9dElGUCR5A0z_KqKgWlpCoZy6tiTODduTiBZGIpRgrBC87zBNA0wfnb5QTzJeaI-xmY9hg_AKxjiFc?type=png)](https://mermaid.live/edit#pako:eNp9kc1OwzAQhF-l2hOIJLITO8E-9MQVcegNRaqMvZSIxi6OgyhR3h0n4a8VYk-r0Xw7a-8A2hkECR2-9Gg13jRq51Vb21UsvW_QhnS9vnLeoE879K-NRrnaYbiblE1Qoe8uLhf7iemb2n4KW_Mg_-F-2dLIpmeJ7gf7K2wilm1PrJBAi75VjYlPHCawhvCELdYgY2uUf66htmP0qT64zdFqkMH3mEB_MCp8fQfIR7XvonpQFuQAbyAFz2hBCeM5o9dElGUCR5A0z_KqKgWlpCoZy6tiTODduTiBZGIpRgrBC87zBNA0wfnb5QTzJeaI-xmY9hg_AKxjiFc)
```
sequenceDiagram
    client->>+order-service: getOrderStatus()
    order-service->>+order_service_db:getOrderStatus()
    order_service_db-->>-order-service: orderStatus
    order-service-->>-client: orderStatus
```

