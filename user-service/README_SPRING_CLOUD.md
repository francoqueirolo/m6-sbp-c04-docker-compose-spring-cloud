### Procedimiento

- 1.- Cambiar el archivo application.properties a application.yml
- 2.- Copiar application.yml en el directorio config-repo  
- 3.- Agregar dependencia Spring Cloud Config Client
```
        <spring-cloud.version>2025.0.0</spring-cloud.version>

```
```
        <!-- Spring Cloud Config Client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

```
```
    <!-- Spring Cloud Dependencies -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
- 4.- Agregar dependencia Spring Cloud Bootstrap
```
   <!-- Spring Cloud Boostrap -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>
```
- 5.- Agregar el archivo bootstrap.yml en el directorio src/main/resources
```
spring:
  application:
    name: user-service
  cloud:
    config:
      uri: http://localhost:8084
      fail-fast: true
```
NO  - 6.- Agregar la anotación @EnableConfigServer en la clase principal