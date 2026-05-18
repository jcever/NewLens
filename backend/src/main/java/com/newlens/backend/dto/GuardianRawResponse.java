package com.newlens.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GuardianRawResponse(GuardianResponseBody response) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GuardianResponseBody(
            String status,
            int total,
            int pageSize,
            int currentPage,
            int pages,
            List<GuardianResult> results) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GuardianResult(
            String id,
            String type,
            String sectionId,
            String sectionName,
            String webPublicationDate,
            String webTitle,
            String webUrl) {}
}
