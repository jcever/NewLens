package com.newlens.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "search_runs")
public class SearchRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "saved_search_id", nullable = false)
    private SavedSearch savedSearch;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt = Instant.now();

    @Column(name = "completed_at")
    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SearchStatus status = SearchStatus.RUNNING;

    @OneToMany(mappedBy = "searchRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @OneToOne(mappedBy = "searchRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private Summary summary;

    protected SearchRun() {}

    public SearchRun(SavedSearch savedSearch) {
        this.savedSearch = savedSearch;
    }

    public Long getId() { return id; }
    public SavedSearch getSavedSearch() { return savedSearch; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
    public SearchStatus getStatus() { return status; }
    public List<Article> getArticles() { return articles; }
    public Summary getSummary() { return summary; }

    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
    public void setStatus(SearchStatus status) { this.status = status; }
    public void setSummary(Summary summary) { this.summary = summary; }
}
