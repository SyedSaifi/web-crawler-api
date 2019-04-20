package com.example.webcrawlerapi.service;

import com.example.webcrawlerapi.model.PageInformation;

import java.util.List;

public interface ICrawlerService {

    PageInformation crawl(String url, int depth, PageInformation pageInfomation, List<String> processedUrls);
}
