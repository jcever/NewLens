package com.newlens.backend.repository;

import com.newlens.backend.entity.SavedSearch;
import com.newlens.backend.entity.SearchRun;
import com.newlens.backend.entity.SearchStatus;
import com.newlens.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaRelationshipTest {

    @Autowired UserRepository userRepo;
    @Autowired SavedSearchRepository savedSearchRepo;
    @Autowired SearchRunRepository searchRunRepo;

    @Test
    void saveChainAndReadBack() {
        User user = userRepo.save(new User("alice@example.com", "hashed"));
        SavedSearch search = savedSearchRepo.save(new SavedSearch(user, "openai"));
        searchRunRepo.save(new SearchRun(search));

        List<SavedSearch> searches = savedSearchRepo.findByUserId(user.getId());
        assertThat(searches).hasSize(1);
        assertThat(searches.get(0).getKeyword()).isEqualTo("openai");

        List<SearchRun> runs = searchRunRepo.findBySavedSearchIdOrderByStartedAtDesc(search.getId());
        assertThat(runs).hasSize(1);
        assertThat(runs.get(0).getStatus()).isEqualTo(SearchStatus.RUNNING);
    }

    @Test
    void findByEmail_presentAndAbsent() {
        userRepo.save(new User("bob@example.com", "hashed"));

        assertThat(userRepo.findByEmail("bob@example.com")).isPresent();
        assertThat(userRepo.findByEmail("nobody@example.com")).isEmpty();
    }

    @Test
    void duplicateEmail_throwsConstraintViolation() {
        userRepo.save(new User("carol@example.com", "hashed"));
        userRepo.flush();

        org.junit.jupiter.api.Assertions.assertThrows(
            Exception.class,
            () -> {
                userRepo.saveAndFlush(new User("carol@example.com", "other-hash"));
            }
        );
    }
}
