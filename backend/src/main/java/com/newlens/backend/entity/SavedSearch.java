package com.newlens.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "saved_searches")
public class SavedSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String keyword;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "savedSearch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchRun> runs = new ArrayList<>();

    protected SavedSearch() {}

    public SavedSearch(User user, String keyword) {
        this.user = user;
        this.keyword = keyword;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getKeyword() { return keyword; }
    public Instant getCreatedAt() { return createdAt; }
    public List<SearchRun> getRuns() { return runs; }
}
