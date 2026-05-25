package com.newlens.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.newlens.backend.dto.ArticleDto;
import com.newlens.backend.dto.GuardianRawResponse;
import com.newlens.backend.dto.GuardianRawResponse.GuardianResponseBody;
import com.newlens.backend.dto.GuardianRawResponse.GuardianResult;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class GuardianArticleMapperTest {

    @Test
    void nullInputReturnsEmptyList() {
        assertThat(GuardianArticleMapper.toArticles(null)).isEmpty();
    }

    @Test
    void emptyResultsReturnsEmptyList() {
        GuardianRawResponse raw = new GuardianRawResponse(
                new GuardianResponseBody("ok", 0, 10, 1, 0, List.of()));

        assertThat(GuardianArticleMapper.toArticles(raw)).isEmpty();
    }

    @Test
    void mapsAllFieldsAndParsesPublicationDateToInstant() {
        GuardianResult result = new GuardianResult(
                "tech/2026/may/01/openai",
                "article",
                "technology",
                "Technology",
                "2026-05-01T10:00:00Z",
                "OpenAI ships new model",
                "https://www.theguardian.com/tech/2026/may/01/openai");
        GuardianRawResponse raw = new GuardianRawResponse(
                new GuardianResponseBody("ok", 1, 10, 1, 1, List.of(result)));

        List<ArticleDto> articles = GuardianArticleMapper.toArticles(raw);

        assertThat(articles).hasSize(1);
        ArticleDto article = articles.get(0);
        assertThat(article.title()).isEqualTo("OpenAI ships new model");
        assertThat(article.url()).isEqualTo("https://www.theguardian.com/tech/2026/may/01/openai");
        assertThat(article.source()).isEqualTo("The Guardian");
        assertThat(article.section()).isEqualTo("Technology");
        assertThat(article.publishedAt()).isEqualTo(Instant.parse("2026-05-01T10:00:00Z"));
    }

    @Test
    void mapsMultipleResultsPreservingOrder() {
        GuardianResult first = new GuardianResult(
                "a", "article", "tech", "Technology",
                "2026-05-01T10:00:00Z", "First", "https://example.com/a");
        GuardianResult second = new GuardianResult(
                "b", "article", "tech", "Technology",
                "2026-05-02T10:00:00Z", "Second", "https://example.com/b");
        GuardianRawResponse raw = new GuardianRawResponse(
                new GuardianResponseBody("ok", 2, 10, 1, 1, List.of(first, second)));

        List<ArticleDto> articles = GuardianArticleMapper.toArticles(raw);

        assertThat(articles).extracting(ArticleDto::title).containsExactly("First", "Second");
    }
}
