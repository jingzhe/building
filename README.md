# Buildings backend

## How to run the service
Use IntelliJ IDEA to open the project and run the service, or use command line bellow
### Compile
```
mvn compile
```
### Start the service
```
mvn spring-boot:run
```
### Run the test cases
```
mvn test
```

### OpenApi documentation
http://localhost:8081/webjars/swagger-ui/index.html

## How to run the service with Docker
### Create package jar
```
mvn package
```
### Build Docker image
```
docker build -t jingzheyu/building .
```
### Run the Docker container with docker-compose together with Authentication service
```
docker-compose up
```

## Feature
Circuit breaker support
Bearer token used in security
MongoDB hosted in Atlas
Cached JWKs, don't need to fetch the key everytime from authentication server