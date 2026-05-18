package com.newlens.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.news")
public record NewsProperties(Guardian guardian) {

    public record Guardian(String baseUrl, String apiKey, int timeoutSeconds) {}
}
