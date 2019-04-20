package com.example.webcrawlerapi.service;

import com.example.webcrawlerapi.model.PageInformation;

public interface ICrawlerService {

    PageInformation crawl(String url, int depth);
}
