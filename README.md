## Overview
This application is a web crawler API to go upto provided depth (max limits apply). Crawling service is exposed as a REST endpoint. It also uses caching (Spring boot implementation) mechanism to improve performance for repeated URLs.

## Implementation
The solution delivered here is a Java project implemented as a Spring Boot / Maven project.

## Build
In order to build the program, the following is required,

- Java 8 JDK
- Maven 3.x.x

Ways to build the project:
```
From the top project level directory run,

$ mvn clean package
$ mvn spring-boot:run

Note: I have build and checked-in the jar file under target folder.

Alternatively, you can also run,
java -jar target/web-crawler-api-0.0.1-SNAPSHOT.jar
```

## Running the application
Now the service is available at:
http://localhost:8090/crawler?url=<pageUrl>

Below url launches the Swagger UI where you can test the api,

http://localhost:8090/swagger-ui.html

Alternatively, it can also be tested over PostMan or any rest client:

http://localhost:8090/crawler?url=https://jsoup.org/

## Assumption and design decisions
- https://start.spring.io/ has been used for spring boot template structure.
- JSoup is used for fetching web contents. I have made use of timeout and followRedirects configuration while connecting to a link.
- I have used the EHCache for caching my web page data. The time to live is set as 60s.
- Parallel streams have been used to fetch the information about the underneath links for better performance.


## Limitations
- The solution work well for depth=1. In an ideal scenario for depth=n we would be running the crawler as a job, where a job from one domain name can be sent to a worker. This way we would be able to scale out of the application.
- In the interest of time, I have not secured the API. This can be simply done using Sring security with JWT, oAuth2 or OpenId.
- For better caching, the cache providers like Redis, Caffeine, Couchbase, etc could have been configured. But to keep things simple and in the interest of time, I have used EHCache.