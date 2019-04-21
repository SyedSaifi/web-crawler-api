package com.example.webcrawlerapi.controller;

import com.example.webcrawlerapi.configuration.AppPropertiesConfig;
import com.example.webcrawlerapi.model.PageInformation;
import com.example.webcrawlerapi.service.ICrawlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Optional;

/**
 * @author Syed Talibuddin Saifi
 * @version 0.0.1-SNAPSHOT
 */
@Api(tags = {"Crawler"}, description = "All operation related to crawling a web url.")
@RestController
public class CrawlerController {

    @Autowired
    private ICrawlerService crawlerService;

    @Autowired
    private AppPropertiesConfig crawlerProperties;

    private static Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    /**
     * @param url   : The baseUrl to crawl
     * @param depth (Optional) : The depth to which baseUrl should be crawled. Default depth=1
     * @return : Returns PageInformation object as JSON
     */
    @ApiOperation(value = "This api fetches the crawled page information.",
            notes = "Default depth for crawling is 1")
    @GetMapping(value = "/crawler", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageInformation> getPageInformation(
            @NotNull @RequestParam(value = "url", required = true) final String url,
            @RequestParam(value = "depth", defaultValue = "1", required = false) final Integer depth) {

        logger.info("Request for crawling received for url {} and depth {}", url, depth);
        final int newDepth = Optional.ofNullable(depth).orElse(crawlerProperties.getDefaultDepth());
        logger.info("Crawling for depth {}", newDepth);

        return new ResponseEntity<>(crawlerService.crawl(url, newDepth, new PageInformation(url), null), HttpStatus.OK);
    }
}
