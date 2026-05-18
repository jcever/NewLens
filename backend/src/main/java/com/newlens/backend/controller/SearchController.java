package com.newlens.backend.controller;

import com.newlens.backend.dto.GuardianRawResponse;
import com.newlens.backend.service.NewsClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final NewsClient newsClient;

    public SearchController(NewsClient newsClient) {
        this.newsClient = newsClient;
    }

    @GetMapping("/search-raw")
    public GuardianRawResponse searchRaw(@RequestParam String q) {
        return newsClient.searchRaw(q);
    }
}
