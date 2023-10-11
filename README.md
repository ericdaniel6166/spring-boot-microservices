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

[![](https://mermaid.ink/img/pako:eNrVVl1v2jAU_SuWn4oaEAECIQ-V2Mq2SC2tSrWHCSlykwu1SGwWO9EY4r_P-RokgZb0actTcp1z7vG9x1feYZd7gC0s4GcEzIVbSlYhCRYMqcf1KTDZvrm55qEHYVtAGFMXLLTxiQtXafApAQrZyhCl_xSwgouJTz0iIQddzODk3473YiFB4jx3C2UYxiUgHkNYRmuoCkYZGkknXdGQkERGAj1OZ7f27OuCNdcwTxm-USF5uG19UE8eT6mc14yrLk4xn9a3Jss1UVTAvEzUo3qjbDWNVftO1lWB2ll3LXS0jXfkn0BUS5dKSRxDWZJc7ePQfZczEQVwVmINonhO0BQmSqGNyA6iSs1cgbSLBZst-dUm5F7kStur9bPGqqGznGgZ8uCwrKGctqLvCJa25cSO6bG6otINq2VLCCYxoT558U-0QCsnaRVZiC8RFRlLFjmXu27CkLsgRKUvxyYpT4eyPc6CiStpTCSUzXlYrx6O69rpjTZJQfIRcgA2ObUZRX2OPD18ns7nxWlNnybK3h4rjURaFwyWN9V6cK7ShTfAF5CYQ2m6xCDtmkMST864_GvLD_jkPYp_2S1fJvadYz9P753Zw7Mz-a4-J5_upv-DdRpIP--j1EPMQ1jDAYQBoZ66iOyS-ALLVwhggS316pFwvcALtlf_kUjy-Za52JJhBBrOKptfWrC1JMqSGt4Qhq0d_oWtdm_Y6ZnjkdHXB91eb9Qda3irwqYx7PQN0xzo_VG3a3R7ew3_5lxR6J3xSDeHA93QDbOv97qmhsGjavf32U0pvTClOX6kgETI_g8-MzZz?type=png)](https://mermaid.live/edit#pako:eNrVVl1v2jAU_SuWn4oaEAECIQ-V2Mq2SC2tSrWHCSlykwu1SGwWO9EY4r_P-RokgZb0actTcp1z7vG9x1feYZd7gC0s4GcEzIVbSlYhCRYMqcf1KTDZvrm55qEHYVtAGFMXLLTxiQtXafApAQrZyhCl_xSwgouJTz0iIQddzODk3473YiFB4jx3C2UYxiUgHkNYRmuoCkYZGkknXdGQkERGAj1OZ7f27OuCNdcwTxm-USF5uG19UE8eT6mc14yrLk4xn9a3Jss1UVTAvEzUo3qjbDWNVftO1lWB2ll3LXS0jXfkn0BUS5dKSRxDWZJc7ePQfZczEQVwVmINonhO0BQmSqGNyA6iSs1cgbSLBZst-dUm5F7kStur9bPGqqGznGgZ8uCwrKGctqLvCJa25cSO6bG6otINq2VLCCYxoT558U-0QCsnaRVZiC8RFRlLFjmXu27CkLsgRKUvxyYpT4eyPc6CiStpTCSUzXlYrx6O69rpjTZJQfIRcgA2ObUZRX2OPD18ns7nxWlNnybK3h4rjURaFwyWN9V6cK7ShTfAF5CYQ2m6xCDtmkMST864_GvLD_jkPYp_2S1fJvadYz9P753Zw7Mz-a4-J5_upv-DdRpIP--j1EPMQ1jDAYQBoZ66iOyS-ALLVwhggS316pFwvcALtlf_kUjy-Za52JJhBBrOKptfWrC1JMqSGt4Qhq0d_oWtdm_Y6ZnjkdHXB91eb9Qda3irwqYx7PQN0xzo_VG3a3R7ew3_5lxR6J3xSDeHA93QDbOv97qmhsGjavf32U0pvTClOX6kgETI_g8-MzZz)

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

[![](https://mermaid.ink/img/pako:eNqtk8FOg0AQhl-FzEkjbYBSkD00Mdqoh7ZEvGhIyArTlhR26-5CxKbv7gLtpT2YVOe0mZ3_2z87MztIeYZAQOJnhSzFh5yuBC1jZujY0OWGGoPJ5GZLmxKZGkgUdZ4iSTmTVYlXXGQoQsFTlDJnq2mti6578YlEU04hNS3yjCrsVH9DpbRIq0KzFi3l6jddcsgk2QeRtMawTx91jCs0eI3ilGAa5wCDGC3ieGMaUlFVSSO8e5tN569J-LK4n0bR8_zxMlNRh3vKpeKi-Q-HSW8wWffICwwPusHQVGTZoW99zXn7wIQSRUnzTA_ZrgXGoNZYYgxEHzMqNjHEbK_raKV41LAUiBIVmlBt2-E4DCSQJS2kzm4pA7KDLyDBeGiPbMsdO659awWeZ0IDxHaGju97gW1bvue6jj_am_DNuSZYw6APf-QEvucHWoFZrj9h1i9BtwvdE--doPWx_wGKhxeH?type=png)](https://mermaid.live/edit#pako:eNqtk8FOg0AQhl-FzEkjbYBSkD00Mdqoh7ZEvGhIyArTlhR26-5CxKbv7gLtpT2YVOe0mZ3_2z87MztIeYZAQOJnhSzFh5yuBC1jZujY0OWGGoPJ5GZLmxKZGkgUdZ4iSTmTVYlXXGQoQsFTlDJnq2mti6578YlEU04hNS3yjCrsVH9DpbRIq0KzFi3l6jddcsgk2QeRtMawTx91jCs0eI3ilGAa5wCDGC3ieGMaUlFVSSO8e5tN569J-LK4n0bR8_zxMlNRh3vKpeKi-Q-HSW8wWffICwwPusHQVGTZoW99zXn7wIQSRUnzTA_ZrgXGoNZYYgxEHzMqNjHEbK_raKV41LAUiBIVmlBt2-E4DCSQJS2kzm4pA7KDLyDBeGiPbMsdO659awWeZ0IDxHaGju97gW1bvue6jj_am_DNuSZYw6APf-QEvucHWoFZrj9h1i9BtwvdE--doPWx_wGKhxeH)

```
sequenceDiagram
    client->>+order-service: getOrderStatus()
    order-service->>+order_service_db:getOrderStatus()
    order_service_db-->>-order-service: orderStatus
    order-service-->>-client: orderStatus
```

[![](https://mermaid.ink/img/pako:eNqFkU1PwzAMhv_K5BOItKJr03Y57MSFA-KwG4o0hcSMiDUZaYIYVf87acPXEBI-WNGb97GdeABpFQKDHp8DGolXWuyc6LhZxJB7jcZn6_WFdQpd1qN70RLZYof-dlI2XvjQn8231-o8USfeL3j7IWzVPfsf_-HOYonsV3_7Tf_VcyLS7CdWINCh64RW8cHDBHLwj9ghBxaPSrgnDtyM0SeCt5ujkcC8C0ggHJTwn58D7EHs-6gehAE2wCuwps7rqi1pkTJdETgCK4o2L8tlVTS0TXkk8GZtrHCZr1JQWjdVtWwIoNLeupu0j3ktc4e72T-NMb4DMOyNzw?type=png)](https://mermaid.live/edit#pako:eNqFkU1PwzAMhv_K5BOItKJr03Y57MSFA-KwG4o0hcSMiDUZaYIYVf87acPXEBI-WNGb97GdeABpFQKDHp8DGolXWuyc6LhZxJB7jcZn6_WFdQpd1qN70RLZYof-dlI2XvjQn8231-o8USfeL3j7IWzVPfsf_-HOYonsV3_7Tf_VcyLS7CdWINCh64RW8cHDBHLwj9ghBxaPSrgnDtyM0SeCt5ujkcC8C0ggHJTwn58D7EHs-6gehAE2wCuwps7rqi1pkTJdETgCK4o2L8tlVTS0TXkk8GZtrHCZr1JQWjdVtWwIoNLeupu0j3ktc4e72T-NMb4DMOyNzw)