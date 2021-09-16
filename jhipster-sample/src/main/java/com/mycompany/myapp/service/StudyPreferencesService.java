package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.StudyPreferencesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.StudyPreferences}.
 */
public interface StudyPreferencesService {
    /**
     * Save a studyPreferences.
     *
     * @param studyPreferencesDTO the entity to save.
     * @return the persisted entity.
     */
    StudyPreferencesDTO save(StudyPreferencesDTO studyPreferencesDTO);

    /**
     * Partially updates a studyPreferences.
     *
     * @param studyPreferencesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StudyPreferencesDTO> partialUpdate(StudyPreferencesDTO studyPreferencesDTO);

    /**
     * Get all the studyPreferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudyPreferencesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" studyPreferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudyPreferencesDTO> findOne(Long id);

    /**
     * Delete the "id" studyPreferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
