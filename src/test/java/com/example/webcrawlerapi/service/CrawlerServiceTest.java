package com.example.webcrawlerapi.service;

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

    @InjectMocks
    private CrawlerService crawlerService;

    private PageInformation pageInformation;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCrawl() {
        pageInformation = new PageInformation("https://jsoup.org/");
        PageInformation pageInfomation = crawlerService.crawl("https://jsoup.org/", 1,
                pageInformation, null);
        Assert.assertEquals("https://jsoup.org/", pageInfomation.getBaseUrl());
        Assert.assertTrue(pageInfomation.getLinks().size() > 0);
        Assert.assertTrue(pageInfomation.getInternalLinks().get() > 0);
        Assert.assertTrue(pageInfomation.getExternalLinks().get() > 0);
    }
}