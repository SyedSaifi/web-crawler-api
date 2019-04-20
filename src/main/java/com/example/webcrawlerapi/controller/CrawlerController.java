package com.example.webcrawlerapi.controller;

import com.example.webcrawlerapi.model.PageInformation;
import com.example.webcrawlerapi.service.ICrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author Syed Talibuddin Saifi
 * @version 0.0.1-SNAPSHOT
 */
@RestController
public class CrawlerController {

    @Autowired
    private ICrawlerService crawlerService;

    private static Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    /**
     * @param url : The baseUrl to crawl
     * @param depth (Optional) : The depth to which baseUrl should be crawled. Default depth=1
     * @return : Returns PageInformation object as JSON
     */
    @GetMapping(value = "/crawler", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<PageInformation> getPageInformation(
            @NotNull @RequestParam(value = "url", required = true) final String url,
            @RequestParam(value = "depth") final Integer depth) {
        logger.info("Request for crawling received for url {} and depth {}", url, depth);

        return new ResponseEntity<>(crawlerService.crawl(url, depth), HttpStatus.OK);
    }
}