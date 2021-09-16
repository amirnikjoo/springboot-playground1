package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.NativeLanguageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.NativeLanguage}.
 */
public interface NativeLanguageService {
    /**
     * Save a nativeLanguage.
     *
     * @param nativeLanguageDTO the entity to save.
     * @return the persisted entity.
     */
    NativeLanguageDTO save(NativeLanguageDTO nativeLanguageDTO);

    /**
     * Partially updates a nativeLanguage.
     *
     * @param nativeLanguageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NativeLanguageDTO> partialUpdate(NativeLanguageDTO nativeLanguageDTO);

    /**
     * Get all the nativeLanguages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NativeLanguageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" nativeLanguage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NativeLanguageDTO> findOne(Long id);

    /**
     * Delete the "id" nativeLanguage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
