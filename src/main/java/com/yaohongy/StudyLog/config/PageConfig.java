package com.yaohongy.StudyLog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "page.config")
public class PageConfig {
    
    private int DefaultPageSize = 20;

    public int getDefaultPageSize() {
        return DefaultPageSize;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        DefaultPageSize = defaultPageSize;
    }
}
