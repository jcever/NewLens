package com.newlens.backend.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.newlens.backend.dto.ArticleDto;
import com.newlens.backend.dto.GuardianRawResponse;
import com.newlens.backend.exception.GlobalExceptionHandler;
import com.newlens.backend.service.NewsClient;
import com.newlens.backend.service.SearchService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SearchController.class)
@Import(GlobalExceptionHandler.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsClient newsClient;

    @MockBean
    private SearchService searchService;

    @Test
    void searchRawDelegatesToNewsClientAndReturnsItsResponse() throws Exception {
        GuardianRawResponse fake = new GuardianRawResponse(
                new GuardianRawResponse.GuardianResponseBody(
                        "ok", 1, 10, 1, 1,
                        List.of(new GuardianRawResponse.GuardianResult(
                                "id1", "article", "tech", "Technology",
                                "2026-05-01T10:00:00Z", "Fake Title", "https://example.com"))));
        when(newsClient.searchRaw("openai")).thenReturn(fake);

        mockMvc.perform(get("/api/search-raw").param("q", "openai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.status").value("ok"))
                .andExpect(jsonPath("$.response.total").value(1))
                .andExpect(jsonPath("$.response.results[0].webTitle").value("Fake Title"));

        verify(newsClient).searchRaw(eq("openai"));
    }

    @Test
    void searchRawWithoutQueryParamReturns400() throws Exception {
        mockMvc.perform(get("/api/search-raw"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchReturnsMappedArticleDtoList() throws Exception {
        ArticleDto article = new ArticleDto(
                "OpenAI ships new model",
                "https://example.com/openai",
                "The Guardian",
                "Technology",
                Instant.parse("2026-05-01T10:00:00Z"));
        when(searchService.search("openai")).thenReturn(List.of(article));

        mockMvc.perform(get("/api/search").param("q", "openai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("OpenAI ships new model"))
                .andExpect(jsonPath("$[0].url").value("https://example.com/openai"))
                .andExpect(jsonPath("$[0].source").value("The Guardian"))
                .andExpect(jsonPath("$[0].section").value("Technology"))
                .andExpect(jsonPath("$[0].publishedAt").value("2026-05-01T10:00:00Z"));

        verify(searchService).search(eq("openai"));
    }

    @Test
    void searchWithoutQueryParamReturns400WithMissingParameterCode() throws Exception {
        mockMvc.perform(get("/api/search"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("missing_parameter"));
    }

    @Test
    void searchWithBlankQueryReturns400WithValidationErrorCode() throws Exception {
        mockMvc.perform(get("/api/search").param("q", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("validation_error"));
    }
}
