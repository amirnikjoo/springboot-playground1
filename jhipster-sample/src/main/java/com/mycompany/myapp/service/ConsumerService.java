package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ConsumerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Consumer}.
 */
public interface ConsumerService {
    /**
     * Save a consumer.
     *
     * @param consumerDTO the entity to save.
     * @return the persisted entity.
     */
    ConsumerDTO save(ConsumerDTO consumerDTO);

    /**
     * Partially updates a consumer.
     *
     * @param consumerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConsumerDTO> partialUpdate(ConsumerDTO consumerDTO);

    /**
     * Get all the consumers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsumerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" consumer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsumerDTO> findOne(Long id);

    /**
     * Delete the "id" consumer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
