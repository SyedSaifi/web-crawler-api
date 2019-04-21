package com.example.webcrawlerapi.service;

import com.example.webcrawlerapi.configuration.AppPropertiesConfig;
import com.example.webcrawlerapi.model.LinkInformation;
import com.example.webcrawlerapi.model.PageInformation;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CrawlerService implements ICrawlerService {

    @Autowired
    private AppPropertiesConfig crawlerProperties;

    private static final String URL_IS_REACHABLE = "Url is reachable";
    private static final String URL_IS_UNREACHABLE_CAUSE = "Url is unreachable. Cause: ";
    private static Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    private PageInformation pageInformation;
    private String domain;
    private Set<String> processedUrls;

    /**
     * @param baseUrl : The baseUrl to crawl
     * @param depth : The depth to which baseUrl should be crawled
     * @return: Return pageInformation object with complete crawling metadata
     */
    @Override
    @Cacheable(value = "pageInformation", key = "#baseUrl.concat('-').concat(#depth)")
    public PageInformation crawl(String baseUrl, int depth, boolean is_first) {
        logger.debug("Starting crawler for url {} for depth {}", baseUrl, depth);

        if(is_first){
            pageInformation = new PageInformation(baseUrl);
            domain = pageInformation.getBaseUrl().split("://")[1].split("/")[0];
            processedUrls = new HashSet<>();
        }

        if (depth == 0) {
            logger.info("Maximum depth reached, backing out for url {}", baseUrl);
            return null;
        } else {
            if (processedUrls.add(baseUrl)) {
                fetchLinks(baseUrl).ifPresent(elements -> {
                    logger.info("Found {} links on the web page: {}", elements.size(), baseUrl);
                    elements.parallelStream().forEach(link -> {
                        String internalLink = link.attr("abs:href");

                        if (internalLink.contains(domain))
                            pageInformation.getInternal().incrementAndGet();
                        else
                            pageInformation.getExternal().incrementAndGet();

                        LinkInformation linkInformation = fetchLinkInformation(internalLink);
                        pageInformation.addLinkInfo(linkInformation);
                        crawl(internalLink, depth - 1,false);
                    });
                });
                pageInformation.setInternalLinks(pageInformation.getInternal().get());
                pageInformation.setExternalLinks(pageInformation.getExternal().get());
                return pageInformation;
            } else {
                logger.info("Duplicate link found. Skipping it.");
                return null;
            }
        }
    }

    /**
     * @param url : The url for which the link elements to be fetched
     * @return : Returns Option<Elements> if there are links under the input url, or else empty
     */
    private Optional<Elements> fetchLinks(String url) {
        logger.info("Fetching links for url: {}", url);

        try {
            Document document = Jsoup.connect(url).timeout(crawlerProperties.getTimeOut())
                    .followRedirects(crawlerProperties.isFollowRedirects()).get();
            Elements links = document.select("a[href]");

            return Optional.of(links);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(String.format("Error getting contents of url %s", url), e);
            return Optional.empty();
        }
    }

    /**
     * @param internalLink : The links which needs to be validated if it is reachable
     * @return : Returns LinkInformation object which contains the metadata of the link which is validated
     */
    private LinkInformation fetchLinkInformation(String internalLink) {
        logger.info("Fetching contents for link {}", internalLink);
        LinkInformation linkInformation = new LinkInformation();

        linkInformation.setLink(internalLink);
        int index = internalLink.indexOf(":");
        if (index != -1)
            linkInformation.setProtocol(internalLink.substring(0, index));
        else
            linkInformation.setProtocol("unknown");

        try {
            Connection connection = Jsoup.connect(internalLink).timeout(crawlerProperties.getTimeOut())
                    .followRedirects(crawlerProperties.isFollowRedirects());
            connection.get();

            //HTTP OK status code indicating that everything is great.
            if (connection.response().statusCode() == 200) {
                linkInformation.setReachable(true);
                linkInformation.setRemark(URL_IS_REACHABLE);
            }
            return linkInformation;
        } catch (IOException | IllegalArgumentException e) {
            logger.error(String.format("Error getting contents of url %s", internalLink), e);
            linkInformation.setReachable(false);
            linkInformation.setRemark(URL_IS_UNREACHABLE_CAUSE + e.getMessage());
            return linkInformation;
        }
    }

}
