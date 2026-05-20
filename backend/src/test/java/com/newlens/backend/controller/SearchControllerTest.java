package com.newlens.backend.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.newlens.backend.dto.GuardianRawResponse;
import com.newlens.backend.service.NewsClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsClient newsClient;

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
}
