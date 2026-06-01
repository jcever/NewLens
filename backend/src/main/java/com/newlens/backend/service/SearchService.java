package com.newlens.backend.service;

import com.newlens.backend.dto.ArticleDto;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final NewsClient newsClient;

    public SearchService(NewsClient newsClient) {
        this.newsClient = newsClient;
    }

    public List<ArticleDto> search(String query) {
        log.info("search_start query='{}' source=guardian", query);
        var raw = newsClient.searchRaw(query);
        List<ArticleDto> articles = GuardianArticleMapper.toArticles(raw);
        log.info("search_complete query='{}' source=guardian result_count={}", query, articles.size());
        return articles;
    }
}
