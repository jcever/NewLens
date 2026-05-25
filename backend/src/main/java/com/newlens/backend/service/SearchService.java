package com.newlens.backend.service;

import com.newlens.backend.dto.ArticleDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final NewsClient newsClient;

    public SearchService(NewsClient newsClient) {
        this.newsClient = newsClient;
    }

    public List<ArticleDto> search(String query) {
        var raw = newsClient.searchRaw(query);
        return GuardianArticleMapper.toArticles(raw);
    }
}
