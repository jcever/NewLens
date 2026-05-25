package com.newlens.backend.service;

import com.newlens.backend.dto.ArticleDto;
import com.newlens.backend.dto.GuardianRawResponse;
import com.newlens.backend.dto.GuardianRawResponse.GuardianResult;
import java.time.Instant;
import java.util.List;

public final class GuardianArticleMapper {

    private static final String SOURCE_NAME = "The Guardian";

    private GuardianArticleMapper() {}

    public static List<ArticleDto> toArticles(GuardianRawResponse raw) {
        if (raw == null || raw.response() == null || raw.response().results() == null) {
            return List.of();
        }
        return raw.response().results().stream()
                .map(GuardianArticleMapper::toArticle)
                .toList();
    }

    static ArticleDto toArticle(GuardianResult result) {
        return new ArticleDto(
                result.webTitle(),
                result.webUrl(),
                SOURCE_NAME,
                result.sectionName(),
                Instant.parse(result.webPublicationDate()));
    }
}
