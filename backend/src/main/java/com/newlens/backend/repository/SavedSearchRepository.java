package com.newlens.backend.repository;

import com.newlens.backend.entity.SavedSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {
    List<SavedSearch> findByUserId(Long userId);
}
