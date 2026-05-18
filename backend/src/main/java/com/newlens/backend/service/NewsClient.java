package com.newlens.backend.service;

import com.newlens.backend.config.NewsProperties;
import com.newlens.backend.dto.GuardianRawResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NewsClient {

    private static final Logger log = LoggerFactory.getLogger(NewsClient.class);

    private final RestClient restClient;
    private final String apiKey;

    public NewsClient(NewsProperties properties) {
        var guardian = properties.guardian();
        this.apiKey = guardian.apiKey();
        this.restClient = RestClient.builder()
                .baseUrl(guardian.baseUrl())
                .build();
    }

    public GuardianRawResponse searchRaw(String query) {
        log.info("Guardian search: q='{}'", query);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("api-key", apiKey)
                        .build())
                .retrieve()
                .body(GuardianRawResponse.class);
    }
}
