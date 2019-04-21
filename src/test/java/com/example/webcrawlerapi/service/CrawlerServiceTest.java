package com.example.webcrawlerapi.service;

import com.example.webcrawlerapi.configuration.AppPropertiesConfig;
import com.example.webcrawlerapi.model.PageInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerServiceTest {

    @Mock
    private AppPropertiesConfig crawlerProperties;

    @InjectMocks
    private CrawlerService crawlerService;

    private PageInformation pageInformation;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCrawlIncorrectUrl() {
        pageInformation = new PageInformation("https://jsoup111.org/");
        PageInformation pageInfomation = crawlerService.crawl("https://jsoup111.org/", 1, true);
        Assert.assertEquals("https://jsoup111.org/", pageInfomation.getBaseUrl());
        Assert.assertNull(pageInfomation.getLinks());
        Assert.assertEquals(0, pageInfomation.getInternalLinks());
        Assert.assertEquals(0, pageInfomation.getExternalLinks());
    }

    @Test
    public void testCrawl() {
        pageInformation = new PageInformation("https://jsoup.org/");
        PageInformation pageInfomation = crawlerService.crawl("https://jsoup.org/", 1,true);
        Assert.assertEquals("https://jsoup.org/", pageInfomation.getBaseUrl());
        Assert.assertTrue(pageInfomation.getLinks().size() > 0);
        Assert.assertTrue(pageInfomation.getInternalLinks() > 0);
        Assert.assertTrue(pageInfomation.getExternalLinks() > 0);
    }
}