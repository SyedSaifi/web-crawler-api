package com.example.webcrawlerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PageInformation {

    private String baseUrl;

    private AtomicInteger internalLinks;

    private AtomicInteger externalLinks;

    private List<LinkInformation> links;

    public PageInformation(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @JsonIgnore
    public void addLinkInfo(LinkInformation linkInformation){
        if(links == null){
            links = new ArrayList<>();
        }
        if(linkInformation != null){
            links.add(linkInformation);
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public AtomicInteger getInternalLinks() {
        return internalLinks;
    }

    public void setInternalLinks(AtomicInteger internalLinks) {
        this.internalLinks = internalLinks;
    }

    public AtomicInteger getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(AtomicInteger externalLinks) {
        this.externalLinks = externalLinks;
    }

    public List<LinkInformation> getLinks() {
        return links;
    }

    public void setLinks(List<LinkInformation> links) {
        this.links = links;
    }
}
