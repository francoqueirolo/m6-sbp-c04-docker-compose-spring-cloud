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

# Crear imagenes para la cuenta jgomez2z
docker build -t jgomez2z/user-service-dev:1.0  ./user-service
docker build -t jgomez2z/product-service-dev:1.0  ./product-service
docker build -t jgomez2z/api-gateway-dev:1.0  ./api-gateway
```

### Paso 3 : Subir las imágenes al Docker Hub
```
# Subir imagenes para la cuenta jgomez2z
docker push jgomez2z/user-service-dev:1.0 
docker push jgomez2z/product-service-dev:1.0
docker push jgomez2z/api-gateway-dev:1.0  
```
