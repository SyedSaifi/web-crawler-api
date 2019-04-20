package com.example.webcrawlerapi.model;

import java.util.List;

public class PageInformation {
    private String baseUrl;

    private int internalLinks;

    private int externalLinks;

    private List<LinkInformation> links;

    public PageInformation(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getInternalLinks() {
        return internalLinks;
    }

    public void setInternalLinks(int internalLinks) {
        this.internalLinks = internalLinks;
    }

    public int getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(int externalLinks) {
        this.externalLinks = externalLinks;
    }

    public List<LinkInformation> getLinks() {
        return links;
    }

    public void setLinks(List<LinkInformation> links) {
        this.links = links;
    }
}
