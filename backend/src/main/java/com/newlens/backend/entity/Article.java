package com.newlens.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(
        name = "articles",
        indexes = {
                @Index(name = "idx_article_search_run", columnList = "search_run_id"),
                @Index(name = "idx_article_published_at", columnList = "published_at")
        }
)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "search_run_id", nullable = false)
    private SearchRun searchRun;

    @Column(nullable = false, length = 50)
    private String source;

    @Column(nullable = false, length = 1024)
    private String url;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 200)
    private String section;

    @Column(name = "sentiment_score")
    private Double sentimentScore;

    @Column(name = "published_at")
    private Instant publishedAt;

    protected Article() {}

    public Article(SearchRun searchRun, String source, String url, String title,
                   String section, Instant publishedAt) {
        this.searchRun = searchRun;
        this.source = source;
        this.url = url;
        this.title = title;
        this.section = section;
        this.publishedAt = publishedAt;
    }

    public Long getId() { return id; }
    public SearchRun getSearchRun() { return searchRun; }
    public String getSource() { return source; }
    public String getUrl() { return url; }
    public String getTitle() { return title; }
    public String getSection() { return section; }
    public Double getSentimentScore() { return sentimentScore; }
    public Instant getPublishedAt() { return publishedAt; }

    public void setSentimentScore(Double sentimentScore) { this.sentimentScore = sentimentScore; }
}
