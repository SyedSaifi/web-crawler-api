package com.example.webcrawlerapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPropertiesConfig {
    @Value("${crawler.default-depth}")
    public int defaultDepth;

    @Value("${crawler.time-out}")
    public int timeOut;

    @Value("${crawler.follow-redirects}")
    public boolean followRedirects;

    public int getDefaultDepth() {
        return defaultDepth;
    }

    public void setDefaultDepth(int defaultDepth) {
        this.defaultDepth = defaultDepth;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }
}

