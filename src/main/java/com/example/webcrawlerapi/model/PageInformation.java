package com.example.webcrawlerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PageInformation {

    @ApiModelProperty(notes = "input baseUrl")
    private String baseUrl;

    @ApiModelProperty(notes = "number of links from same domain as baseUrl")
    private int internalLinks;

    @ApiModelProperty(notes = "number of links from different domain than baseUrl")
    private int externalLinks;

    @JsonIgnore
    private AtomicInteger internal;

    @JsonIgnore
    private AtomicInteger external;

    @ApiModelProperty(notes = "link metedata")
    private List<LinkInformation> links;

    public PageInformation(String baseUrl) {
        this.baseUrl = baseUrl;
        this.internal = new AtomicInteger();
        this.external = new AtomicInteger();
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

    public int getInternalLinks() {
        return internalLinks;
    }

    public void setInternalLinks(int internalLinks) {
        this.internalLinks = internalLinks;
    }

    public AtomicInteger getInternal() {
        return internal;
    }

    public void setInternal(AtomicInteger internal) {
        this.internal = internal;
    }

    public int getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(int externalLinks) {
        this.externalLinks = externalLinks;
    }

    public AtomicInteger getExternal() {
        return external;
    }

    public void setExternal(AtomicInteger external) {
        this.external = external;
    }

    public List<LinkInformation> getLinks() {
        return links;
    }

    public void setLinks(List<LinkInformation> links) {
        this.links = links;
    }
}
