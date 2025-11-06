# Generar Docker Compose

### user-service : generar jar
```
cd user-service
mvn clean package -DskipTests
cd ..
```
### product-service : generar jar
```
cd product-service
mvn clean package -DskipTests
cd ..
```

### api-gateway : generar jar
```
cd api-gateway
mvn clean package -DskipTests
cd ..
```


### Generar docker compose
```
docker compose build
```

### Inicializar servicios docker compose
```
docker compose up -d
```

### Detener un servicio en  docker compose
```
docker compose stop user-service-dev 
```

### Levantar un servicio en  docker compose
```
docker compose start user-service-dev
docker compose up -d user-service-dev
```

## Parte 2 : CREACIÓN DE IMÁGENES PARA EL DESPLIEGUE

### Paso 1 : Generar los componentes jar de cada proyecto
```
cd user-service
mvn clean package -DskipTests
cd ..

cd product-service
mvn clean package -DskipTests
cd ..

cd product-service
mvn clean package -DskipTests
cd ..

```

### Paso 2 : Crear las imágenes ( se debe tener una cuenta de Docker)


```
# login
docker login

# Crear imágenes para la cuenta jgomez2z
docker buildx build --platform linux/amd64 -t jgomez2z/user-service-dev:1.0  ./user-service
docker buildx build --platform linux/amd64 -t jgomez2z/product-service-dev:1.0  ./product-service
docker buildx build --platform linux/amd64 -t jgomez2z/api-gateway-dev:1.0  ./api-gateway
```

### Paso 3 : Subir las imágenes al Docker Hub
```
# Subir imágenes para la cuenta jgomez2z
docker push jgomez2z/user-service-dev:1.0 
docker push jgomez2z/product-service-dev:1.0
docker push jgomez2z/api-gateway-dev:1.0  
```

### Paso 4 : Crear un archivo docker-compose.dev.yml que use las imágenes creadas.
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
### Paso 5 : Elliminar el docker compose microservices-dev

### Paso 6 : Construir el nuevo docker compose de docker-compose.dev.yml
```
    docker compose -f docker-compose.dev.yml build
    docker compose -f docker-compose.dev.yml up -d
```