package com.newlens.backend.repository;

import com.newlens.backend.entity.SearchRun;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SearchRunRepository extends JpaRepository<SearchRun, Long> {
    List<SearchRun> findBySavedSearchIdOrderByStartedAtDesc(Long savedSearchId);
}
