package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.StudyPreferencesRepository;
import com.mycompany.myapp.service.StudyPreferencesQueryService;
import com.mycompany.myapp.service.StudyPreferencesService;
import com.mycompany.myapp.service.criteria.StudyPreferencesCriteria;
import com.mycompany.myapp.service.dto.StudyPreferencesDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.StudyPreferences}.
 */
@RestController
@RequestMapping("/api")
public class StudyPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(StudyPreferencesResource.class);

    private static final String ENTITY_NAME = "jhipsterStudyPreferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudyPreferencesService studyPreferencesService;

    private final StudyPreferencesRepository studyPreferencesRepository;

    private final StudyPreferencesQueryService studyPreferencesQueryService;

    public StudyPreferencesResource(
        StudyPreferencesService studyPreferencesService,
        StudyPreferencesRepository studyPreferencesRepository,
        StudyPreferencesQueryService studyPreferencesQueryService
    ) {
        this.studyPreferencesService = studyPreferencesService;
        this.studyPreferencesRepository = studyPreferencesRepository;
        this.studyPreferencesQueryService = studyPreferencesQueryService;
    }

    /**
     * {@code POST  /study-preferences} : Create a new studyPreferences.
     *
     * @param studyPreferencesDTO the studyPreferencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studyPreferencesDTO, or with status {@code 400 (Bad Request)} if the studyPreferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/study-preferences")
    public ResponseEntity<StudyPreferencesDTO> createStudyPreferences(@RequestBody StudyPreferencesDTO studyPreferencesDTO)
        throws URISyntaxException {
        log.debug("REST request to save StudyPreferences : {}", studyPreferencesDTO);
        if (studyPreferencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new studyPreferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudyPreferencesDTO result = studyPreferencesService.save(studyPreferencesDTO);
        return ResponseEntity
            .created(new URI("/api/study-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /study-preferences/:id} : Updates an existing studyPreferences.
     *
     * @param id the id of the studyPreferencesDTO to save.
     * @param studyPreferencesDTO the studyPreferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studyPreferencesDTO,
     * or with status {@code 400 (Bad Request)} if the studyPreferencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studyPreferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/study-preferences/{id}")
    public ResponseEntity<StudyPreferencesDTO> updateStudyPreferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StudyPreferencesDTO studyPreferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StudyPreferences : {}, {}", id, studyPreferencesDTO);
        if (studyPreferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studyPreferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studyPreferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StudyPreferencesDTO result = studyPreferencesService.save(studyPreferencesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studyPreferencesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /study-preferences/:id} : Partial updates given fields of an existing studyPreferences, field will ignore if it is null
     *
     * @param id the id of the studyPreferencesDTO to save.
     * @param studyPreferencesDTO the studyPreferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studyPreferencesDTO,
     * or with status {@code 400 (Bad Request)} if the studyPreferencesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the studyPreferencesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the studyPreferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/study-preferences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StudyPreferencesDTO> partialUpdateStudyPreferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StudyPreferencesDTO studyPreferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StudyPreferences partially : {}, {}", id, studyPreferencesDTO);
        if (studyPreferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studyPreferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studyPreferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudyPreferencesDTO> result = studyPreferencesService.partialUpdate(studyPreferencesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studyPreferencesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /study-preferences} : get all the studyPreferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studyPreferences in body.
     */
    @GetMapping("/study-preferences")
    public ResponseEntity<List<StudyPreferencesDTO>> getAllStudyPreferences(StudyPreferencesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StudyPreferences by criteria: {}", criteria);
        Page<StudyPreferencesDTO> page = studyPreferencesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /study-preferences/count} : count all the studyPreferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/study-preferences/count")
    public ResponseEntity<Long> countStudyPreferences(StudyPreferencesCriteria criteria) {
        log.debug("REST request to count StudyPreferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(studyPreferencesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /study-preferences/:id} : get the "id" studyPreferences.
     *
     * @param id the id of the studyPreferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studyPreferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/study-preferences/{id}")
    public ResponseEntity<StudyPreferencesDTO> getStudyPreferences(@PathVariable Long id) {
        log.debug("REST request to get StudyPreferences : {}", id);
        Optional<StudyPreferencesDTO> studyPreferencesDTO = studyPreferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studyPreferencesDTO);
    }

    /**
     * {@code DELETE  /study-preferences/:id} : delete the "id" studyPreferences.
     *
     * @param id the id of the studyPreferencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/study-preferences/{id}")
    public ResponseEntity<Void> deleteStudyPreferences(@PathVariable Long id) {
        log.debug("REST request to delete StudyPreferences : {}", id);
        studyPreferencesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
