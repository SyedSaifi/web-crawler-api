## Overview
This application is a web crawler API to go upto provided depth (default depth=1). Crawling service is exposed as a REST endpoint and secured using basicAuth. It also uses caching (EHCache implementation) mechanism to improve performance for repeated URLs.

## Implementation
The solution delivered here is a Java project implemented as a Spring Boot / Maven project.

## Build
In order to build the project, the following is required,

- Java 8 JDK
- Maven 3.x.x

Ways to build and run the project:
```
From the top level project directory run,

$ mvn clean package
$ mvn spring-boot:run

Alternatively, you can also run the application using below command,
java -jar target/web-crawler-api-0.0.1-SNAPSHOT.jar

Note: I have built and uploaded the jar file under target folder.
```

## Testing the application
Now the service is available at:
```
http://localhost:8090/crawler?url=https://jsoup.org/
```

Below url launches the Swagger-UI where you can test the api,
```
http://localhost:8090/swagger-ui.html
```

Alternatively, it can also be tested over PostMan or any rest client:
```
http://localhost:8090/crawler?url=https://jsoup.org/
```

Please use below credentials for Basic Auth,
```
Username: user
Password: password
```

Sample response from the api
```
{
   "baseUrl":"https://jsoup.org/,
   "internalLinks":0,
   "externalLinks":2,
   "links":[
      {
         "link":"http://xyz.com/",
         "protocol":"http",
         "reachable":true,
         "remarks":"Link is reachable"
      },
      {
         "link":"https://xyz1.com/",
         "protocol":"https",
         "reachable":false,
         "remarks":"Link is unreachable. Unknown hostname"
      }
   ]
}
```

## Assumption and design decisions
- https://start.spring.io/ has been used for spring boot template structure.
- JSoup is used for fetching web contents. I have made the use of timeout and followRedirects configuration while connecting to a link.
- API is developed using test driven development approach.
- I have used the EHCache for caching my web page data. The time to live is set as 60s.
- Parallel streams have been used to fetch the information about the underneath links for better performance.
- The application takes care of not processing the duplicate links multiple times. This avoids looping links to be processed more than once.
- The API is secured using Spring security with Basic Auth. For simplicity, I have used hardcoded username/password.
- I could not see the use of implementing custom exception as I am not rethrowing exception anywhere in my code. Instead, it is caught and handled in the same method itself.
- I could not find the usecase of implementing HATEOAS and hence have skipped it.

## Limitations
- The solution works well for depth=1. In an ideal scenario for depth=n we would be running the crawler as a job, where a job from one domain name can be sent to a worker. This way we would be able to scale out our the application.
- The API currently works with only hardcoded username and password and basic auth.
- For better caching, the cache providers like Redis, Caffeine, Couchbase, etc. could have been configured. However, to keep things simple and in the interest of time, I have used EHCache.
- While using Swagger UI if you don't pass username/password, the browser shows up a pop-up instead of returning 401- unauthorized. There is already an open bug in the github for this,
  https://github.com/springfox/springfox/issues/2403 (I have added my observations as well).