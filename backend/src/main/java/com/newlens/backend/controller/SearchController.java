package com.newlens.backend.controller;

import com.newlens.backend.dto.ArticleDto;
import com.newlens.backend.dto.GuardianRawResponse;
import com.newlens.backend.service.NewsClient;
import com.newlens.backend.service.SearchService;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class SearchController {

    private final NewsClient newsClient;
    private final SearchService searchService;

    public SearchController(NewsClient newsClient, SearchService searchService) {
        this.newsClient = newsClient;
        this.searchService = searchService;
    }

    @GetMapping("/search-raw")
    public GuardianRawResponse searchRaw(@RequestParam String q) {
        return newsClient.searchRaw(q);
    }

    @GetMapping("/search")
    public List<ArticleDto> search(@RequestParam @NotBlank String q) {
        return searchService.search(q);
    }
}
