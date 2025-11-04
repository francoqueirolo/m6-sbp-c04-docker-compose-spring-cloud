## Creación del Dockerfile

### Generar el JAR del proyecto Spring Boot
```declarative
mvn clean package -DskipTests
```
- -DskipTests : Omite la ejecución de las pruebas

### Verificar que el JAR se haya creado correctamente
```declarative  
ls target/
```
### Verificar que el JAR se ejecute correctamente
```declarative  
java -jar target/nombre-del-archivo.jar 
```

### Crea la imagen
```
docker build -t user-service:1.0 .
```

### Ejecutar el contenedor
```
docker run -d -p 8081:8081 --name user-service user-service:1.0
```

### Verificar que el contenedor falla porque no existe la base de datos
```
docker logs -f user-service
```
