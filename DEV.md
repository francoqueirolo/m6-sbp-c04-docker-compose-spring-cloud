

### GENERAR TAG VERSION EN DOCKER HUB
```
docker login

docker buildx  build --platform linux/amd64  -t jgomez2z/api-gateway-dev:1.0 ./api-gateway

docker buildx  build --platform linux/amd64  -t jgomez2z/product-service-dev:1.0 ./product-service-dev

docker buildx  build --platform linux/amd64  -t jgomez2z/user-service-dev:1.0 ./user-service-dev


docker push jgomez2z/api-gateway-dev:1.0

docker push jgomez2z/user-service-dev:1.0

docker push jgomez2z/product-service-dev:1.0

```


### AWS , open 9080

```
# Update package index
sudo apt-get update

# Install prerequisites
sudo apt-get install -y  ca-certificates curl gnupg lsb-release

# Add Docker's official GPG key
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Set up repository
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Install Docker Engine
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Verify installation
docker --version
docker compose version

```

###
```
sudo usermod -aG docker $USER
```

### docker-compose.dev.yml
```
name : microservices-dev

services:
  # PostgreSQL para User Service
  postgres-user-dev:
    image: postgres:15-alpine
    container_name: postgres-user-dev
    environment:
      POSTGRES_DB: userdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_INITDB_ARGS: "--encoding=UTF8 --locale=en_US.UTF-8"
  #  ports:
  #    - "5432:5432"
    volumes:
      - postgres-user-dev-data:/var/lib/postgresql/data
    networks:
      - microservice-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres -d userdb"]
      interval: 10s
      timeout: 5s
      retries: 5
    restart: unless-stopped

  # PostgreSQL para Product Service
  postgres-product-dev:
    image: postgres:15-alpine
    container_name: postgres-product-dev
    environment:
      POSTGRES_DB: productdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_INITDB_ARGS: "--encoding=UTF8 --locale=en_US.UTF-8"
    #ports:
    #  - "5433:5432"  # Puerto externo 5433, interno 5432
    volumes:
      - postgres-product-dev-data:/var/lib/postgresql/data
    networks:
      - microservice-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres -d productdb"]
      interval: 10s
      timeout: 5s
      retries: 5
    restart: unless-stopped

  #  User Service Dev
  user-service-dev:
    image: jgomez2z/user-service-dev:1.0
    build:
      context: ./user-service
      dockerfile: Dockerfile
    container_name: user-service-dev
    # ports:
    #  - "9081:8081"
    environment:
      - SPRING_APPLICATION_NAME=user-service
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-user-dev:5432/userdb
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
    depends_on:
      postgres-user-dev:
        condition: service_healthy
    networks:
      - microservice-network
    restart: unless-stopped

#  Product Service Dev
  product-service-dev:
    image: jgomez2z/product-service-dev:1.0
    build:
      context: ./product-service
      dockerfile: Dockerfile
    container_name: product-service-dev
    # ports:
    #  - "9082:8082"
    environment:
      - SPRING_APPLICATION_NAME=product-service
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-product-dev:5432/productdb
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
    depends_on:
      postgres-product-dev:
        condition: service_healthy
    networks:
      - microservice-network
    restart: unless-stopped

#  Api Gateway Dev
  api-gateway-dev:
    image: jgomez2z/api-gateway-dev:1.0
    build:
      context: ./api-gateway
      dockerfile: Dockerfile
    container_name: api-gateway-dev
    ports:
      - "9080:8080"
    environment:
      - SPRING_APPLICATION_NAME=api-gateway
      - SPRING_PROFILES_ACTIVE=dev

    depends_on:
      user-service-dev:
        condition: service_started
      product-service-dev:
        condition: service_started
    networks:
      - microservice-network
    restart: unless-stopped


networks:
  microservice-network:
    driver: bridge

volumes:
  postgres-user-dev-data:
    driver: local
  postgres-product-dev-data:
    driver: local
```
### run docker compose
```

docker compose -f docker-compose.dev.yml up -d

```
