package com.newlens.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.newlens.backend.config.NewsProperties;
import com.newlens.backend.dto.GuardianRawResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(NewsClient.class)
class NewsClientTest {

    @Autowired
    private NewsClient newsClient;

    @Autowired
    private MockRestServiceServer server;

    @TestConfiguration
    static class FakePropsConfig {
        @Bean
        NewsProperties newsProperties() {
            return new NewsProperties(
                    new NewsProperties.Guardian(
                            "https://content.guardianapis.com",
                            "test-api-key",
                            5));
        }
    }

    @Test
    void searchRawCallsGuardianSearchAndDeserializesResponse() {
        String responseJson = """
                {
                  "response": {
                    "status": "ok",
                    "total": 2,
                    "pageSize": 10,
                    "currentPage": 1,
                    "pages": 1,
                    "results": [
                      {
                        "id": "tech/2026/may/01/one",
                        "type": "article",
                        "sectionId": "technology",
                        "sectionName": "Technology",
                        "webPublicationDate": "2026-05-01T10:00:00Z",
                        "webTitle": "Sample One",
                        "webUrl": "https://example.com/1"
                      },
                      {
                        "id": "tech/2026/may/02/two",
                        "type": "article",
                        "sectionId": "technology",
                        "sectionName": "Technology",
                        "webPublicationDate": "2026-05-02T10:00:00Z",
                        "webTitle": "Sample Two",
                        "webUrl": "https://example.com/2"
                      }
                    ]
                  }
                }
                """;

        server.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(
                        "https://content.guardianapis.com/search?q={q}&api-key={k}",
                        "machine learning", "test-api-key"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        GuardianRawResponse result = newsClient.searchRaw("machine learning");

        assertThat(result.response().status()).isEqualTo("ok");
        assertThat(result.response().total()).isEqualTo(2);
        assertThat(result.response().results()).hasSize(2);
        assertThat(result.response().results().get(0).webTitle()).isEqualTo("Sample One");
        assertThat(result.response().results().get(1).webUrl()).isEqualTo("https://example.com/2");

        server.verify();
    }
}
