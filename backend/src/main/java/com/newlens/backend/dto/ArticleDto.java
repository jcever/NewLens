package com.newlens.backend.dto;

import java.time.Instant;

public record ArticleDto(
        String title,
        String url,
        String source,
        String section,
        Instant publishedAt) {}
