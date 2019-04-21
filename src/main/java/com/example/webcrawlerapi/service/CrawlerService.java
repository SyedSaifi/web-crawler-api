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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@CacheConfig(cacheNames = {"PAGE"})
public class CrawlerService implements ICrawlerService {

    private static Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    @Autowired
    private AppPropertiesConfig crawlerProperties;

    /**
     *
     * @param baseUrl : The baseUrl to crawl
     * @param depth : The depth to which baseUrl should be crawled
     * @param pageInformation: The pageInformation object which store the crawling information
     * @param processedUrls: The url which is already processed
     * @return: Return pageInformation object with complete crawling metadata
     */
    @Override
//    @Cacheable(key = "#baseUrl", condition = "#baseUrl != null")
    @Cacheable(value = "pageInformation", key = "#baseUrl")
    public PageInformation crawl(final String baseUrl, final int depth, PageInformation pageInformation,
                                 final List<String> processedUrls) {
        logger.debug("Starting crawler for url {} for depth {}", baseUrl, depth);
        if (depth == 0) {
            logger.info("Maximum depth reached, backing out for url {}", baseUrl);
            return null;
        } else {
            final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
            if (!updatedProcessedUrls.contains(baseUrl)) {
                updatedProcessedUrls.add(baseUrl);
                String hostname = baseUrl.split("://")[1].split("/")[0];

                fetchLinks(baseUrl).ifPresent(elements -> {
                    logger.info("Found {} links on the web page: {}", elements.size(), baseUrl);
                    elements.parallelStream().forEach(link -> {
                        String internalLink = link.attr("abs:href");

                        if (internalLink.contains(hostname))
                            pageInformation.getInternal().incrementAndGet();
                        else
                            pageInformation.getExternal().incrementAndGet();

                        LinkInformation linkInformation = fetchLinkInformation(internalLink);
                        pageInformation.addLinkInfo(linkInformation);
                        crawl(internalLink, depth - 1, pageInformation, updatedProcessedUrls);
                    });
                });
                pageInformation.setInternalLinks(pageInformation.getInternal().get());
                pageInformation.setExternalLinks(pageInformation.getExternal().get());
                return pageInformation;
            } else {
                return null;
            }
        }
    }

    private Optional<Elements> fetchLinks(final String url) {
        logger.info("Fetching links for url: {}", url);

        try {
            final Document document = Jsoup.connect(url).timeout(crawlerProperties.getTimeOut())
                    .followRedirects(crawlerProperties.isFollowRedirects()).get();
            final Elements links = document.select("a[href]");

            return Optional.of(links);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(String.format("Error getting contents of url %s", url), e);
            return Optional.empty();
        }
    }

    private LinkInformation fetchLinkInformation(final String internalLink) {
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
                linkInformation.setRemark("Url is reachable");
            }
            return linkInformation;
        } catch (final IOException | IllegalArgumentException e) {
            logger.error(String.format("Error getting contents of url %s", internalLink), e);
            linkInformation.setReachable(false);
            linkInformation.setRemark("Url is unreachable. Cause: " + e.getMessage());
            return linkInformation;
        }
    }

}
