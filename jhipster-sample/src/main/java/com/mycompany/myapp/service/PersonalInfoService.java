package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PersonalInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PersonalInfo}.
 */
public interface PersonalInfoService {
    /**
     * Save a personalInfo.
     *
     * @param personalInfoDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalInfoDTO save(PersonalInfoDTO personalInfoDTO);

    /**
     * Partially updates a personalInfo.
     *
     * @param personalInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonalInfoDTO> partialUpdate(PersonalInfoDTO personalInfoDTO);

    /**
     * Get all the personalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonalInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personalInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalInfoDTO> findOne(Long id);

    /**
     * Delete the "id" personalInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
