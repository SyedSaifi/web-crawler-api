package com.example.webcrawlerapi.service;

import com.example.webcrawlerapi.model.PageInformation;

public class CrawlerService implements ICrawlerService {

    @Override
    public PageInformation crawl(String url, int depth) {
        return new PageInformation(url);
    }
}
