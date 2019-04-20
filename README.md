## Overview
This application is a web crawler api to go upto provided depth (max limits apply). Crawling service is exposed as REST endpoint. It also uses caching (Springboot implementation) mechanism to improve performance for repeated urls.

## Implementation
The solution delivered here is a Java project implemented as a Spring Boot / Maven project.

## Build
In order to build the program, the following is required,

- Java 8 JDK
- Maven 3.x.x

Ways to build the project:
```
From the top project level directory run,

$ maven clean package
$ mvn spring-boot:run

Note: I have build and checked-in the jar file under target folder.

Alternatively you can also run,
java -jar target/web-crawler-api-0.0.1-SNAPSHOT.jar
```

## Running the application
Now the service is available at:
http://localhost:8090/crawler?url=<pageUrl>

Below url launches the Swagger UI where you can test the api,

http://localhost:8090/swagger-ui.html

Alternatively, it can also be tested over PostMan or any rest client:

http://localhost:8090/crawler?url=https://jsoup.org/