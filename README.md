# Generar Docker Compose

## Parte 1

### user-service : generar jar
```
cd user-service
mvn clean package -DskipTests
cd ..
```
### product-service : generar jar
```
cd product -service
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
