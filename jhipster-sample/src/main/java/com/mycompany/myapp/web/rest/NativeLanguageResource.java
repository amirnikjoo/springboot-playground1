package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.NativeLanguageRepository;
import com.mycompany.myapp.service.NativeLanguageQueryService;
import com.mycompany.myapp.service.NativeLanguageService;
import com.mycompany.myapp.service.criteria.NativeLanguageCriteria;
import com.mycompany.myapp.service.dto.NativeLanguageDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NativeLanguage}.
 */
@RestController
@RequestMapping("/api")
public class NativeLanguageResource {

    private final Logger log = LoggerFactory.getLogger(NativeLanguageResource.class);

    private static final String ENTITY_NAME = "jhipsterNativeLanguage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeLanguageService nativeLanguageService;

    private final NativeLanguageRepository nativeLanguageRepository;

    private final NativeLanguageQueryService nativeLanguageQueryService;

    public NativeLanguageResource(
        NativeLanguageService nativeLanguageService,
        NativeLanguageRepository nativeLanguageRepository,
        NativeLanguageQueryService nativeLanguageQueryService
    ) {
        this.nativeLanguageService = nativeLanguageService;
        this.nativeLanguageRepository = nativeLanguageRepository;
        this.nativeLanguageQueryService = nativeLanguageQueryService;
    }

    /**
     * {@code POST  /native-languages} : Create a new nativeLanguage.
     *
     * @param nativeLanguageDTO the nativeLanguageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nativeLanguageDTO, or with status {@code 400 (Bad Request)} if the nativeLanguage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/native-languages")
    public ResponseEntity<NativeLanguageDTO> createNativeLanguage(@RequestBody NativeLanguageDTO nativeLanguageDTO)
        throws URISyntaxException {
        log.debug("REST request to save NativeLanguage : {}", nativeLanguageDTO);
        if (nativeLanguageDTO.getId() != null) {
            throw new BadRequestAlertException("A new nativeLanguage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NativeLanguageDTO result = nativeLanguageService.save(nativeLanguageDTO);
        return ResponseEntity
            .created(new URI("/api/native-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /native-languages/:id} : Updates an existing nativeLanguage.
     *
     * @param id the id of the nativeLanguageDTO to save.
     * @param nativeLanguageDTO the nativeLanguageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nativeLanguageDTO,
     * or with status {@code 400 (Bad Request)} if the nativeLanguageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nativeLanguageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/native-languages/{id}")
    public ResponseEntity<NativeLanguageDTO> updateNativeLanguage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NativeLanguageDTO nativeLanguageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NativeLanguage : {}, {}", id, nativeLanguageDTO);
        if (nativeLanguageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nativeLanguageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nativeLanguageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NativeLanguageDTO result = nativeLanguageService.save(nativeLanguageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nativeLanguageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /native-languages/:id} : Partial updates given fields of an existing nativeLanguage, field will ignore if it is null
     *
     * @param id the id of the nativeLanguageDTO to save.
     * @param nativeLanguageDTO the nativeLanguageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nativeLanguageDTO,
     * or with status {@code 400 (Bad Request)} if the nativeLanguageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nativeLanguageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nativeLanguageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/native-languages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NativeLanguageDTO> partialUpdateNativeLanguage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NativeLanguageDTO nativeLanguageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NativeLanguage partially : {}, {}", id, nativeLanguageDTO);
        if (nativeLanguageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nativeLanguageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nativeLanguageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NativeLanguageDTO> result = nativeLanguageService.partialUpdate(nativeLanguageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nativeLanguageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /native-languages} : get all the nativeLanguages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nativeLanguages in body.
     */
    @GetMapping("/native-languages")
    public ResponseEntity<List<NativeLanguageDTO>> getAllNativeLanguages(NativeLanguageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NativeLanguages by criteria: {}", criteria);
        Page<NativeLanguageDTO> page = nativeLanguageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /native-languages/count} : count all the nativeLanguages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/native-languages/count")
    public ResponseEntity<Long> countNativeLanguages(NativeLanguageCriteria criteria) {
        log.debug("REST request to count NativeLanguages by criteria: {}", criteria);
        return ResponseEntity.ok().body(nativeLanguageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /native-languages/:id} : get the "id" nativeLanguage.
     *
     * @param id the id of the nativeLanguageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nativeLanguageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/native-languages/{id}")
    public ResponseEntity<NativeLanguageDTO> getNativeLanguage(@PathVariable Long id) {
        log.debug("REST request to get NativeLanguage : {}", id);
        Optional<NativeLanguageDTO> nativeLanguageDTO = nativeLanguageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nativeLanguageDTO);
    }

    /**
     * {@code DELETE  /native-languages/:id} : delete the "id" nativeLanguage.
     *
     * @param id the id of the nativeLanguageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/native-languages/{id}")
    public ResponseEntity<Void> deleteNativeLanguage(@PathVariable Long id) {
        log.debug("REST request to delete NativeLanguage : {}", id);
        nativeLanguageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
