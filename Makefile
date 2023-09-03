.PHONY: docker-up maven-install maven-package maven-clean up down

down: docker-down
	echo "down"

up: maven-install docker-up
	echo "up"

docker-up:
	echo "Preparing DB, keycloak, zipkin, kafka"
	docker compose up -d

docker-down:
	echo "Removing DB, keycloak, zipkin, kafka"
	docker compose down -v

maven-install:
	echo "Maven install"
	mvn clean install -Dmaven.test.skip
	echo "api-gateway"
	cd ../api-gateway; \
	mvn clean install -Dmaven.test.skip
	echo "discovery-server"
	cd ../discovery-server; \
	mvn clean install -Dmaven.test.skip
	echo "inventory-service"
	cd ../inventory-service; \
	mvn clean install -Dmaven.test.skip
	echo "notification-service"
	cd ../notification-service; \
	mvn clean install -Dmaven.test.skip
	echo "order-service"
	cd ../order-service; \
	mvn clean install -Dmaven.test.skip
	echo "product-service"
	cd ../product-service; \
	mvn clean install -Dmaven.test.skip

maven-package:
	echo "Maven package"
	mvn clean package -Dmaven.test.skip
	echo "api-gateway"
	cd ../api-gateway; \
	mvn clean package -Dmaven.test.skip
	echo "discovery-server"
	cd ../discovery-server; \
	mvn clean package -Dmaven.test.skip
	echo "inventory-service"
	cd ../inventory-service; \
	mvn clean package -Dmaven.test.skip
	echo "notification-service"
	cd ../notification-service; \
	mvn clean package -Dmaven.test.skip
	echo "order-service"
	cd ../order-service; \
	mvn clean package -Dmaven.test.skip
	echo "product-service"
	cd ../product-service; \
	mvn clean package -Dmaven.test.skip

maven-clean:
	echo "Maven clean"
	mvn clean -Dmaven.test.skip
	echo "api-gateway"
	cd ../api-gateway; \
	mvn clean -Dmaven.test.skip
	echo "discovery-server"
	cd ../discovery-server; \
	mvn clean -Dmaven.test.skip
	echo "inventory-service"
	cd ../inventory-service; \
	mvn clean -Dmaven.test.skip
	echo "notification-service"
	cd ../notification-service; \
	mvn clean -Dmaven.test.skip
	echo "order-service"
	cd ../order-service; \
	mvn clean -Dmaven.test.skip
	echo "product-service"
	cd ../product-service; \
	mvn clean -Dmaven.test.skip





