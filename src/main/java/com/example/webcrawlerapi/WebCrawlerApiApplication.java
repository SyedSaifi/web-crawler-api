package com.example.webcrawlerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WebCrawlerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApiApplication.class, args);
	}

}
