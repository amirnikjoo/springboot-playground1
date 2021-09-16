package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StudyPreferences;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StudyPreferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudyPreferencesRepository extends JpaRepository<StudyPreferences, Long>, JpaSpecificationExecutor<StudyPreferences> {}
