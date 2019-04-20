package com.example.webcrawlerapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jsoup.select.Elements;

public class LinkInformation {

    private String link;

    private String protocol;

    private boolean reachable;

    @JsonIgnore
    private Elements childLinks;

    private String remark;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Elements getChildLinks() {
        return childLinks;
    }

    public void setChildLinks(Elements childLinks) {
        this.childLinks = childLinks;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

