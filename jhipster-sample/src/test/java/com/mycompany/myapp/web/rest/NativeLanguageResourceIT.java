package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NativeLanguage;
import com.mycompany.myapp.repository.NativeLanguageRepository;
import com.mycompany.myapp.service.criteria.NativeLanguageCriteria;
import com.mycompany.myapp.service.dto.NativeLanguageDTO;
import com.mycompany.myapp.service.mapper.NativeLanguageMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NativeLanguageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NativeLanguageResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/native-languages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NativeLanguageRepository nativeLanguageRepository;

    @Autowired
    private NativeLanguageMapper nativeLanguageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNativeLanguageMockMvc;

    private NativeLanguage nativeLanguage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NativeLanguage createEntity(EntityManager em) {
        NativeLanguage nativeLanguage = new NativeLanguage().title(DEFAULT_TITLE);
        return nativeLanguage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NativeLanguage createUpdatedEntity(EntityManager em) {
        NativeLanguage nativeLanguage = new NativeLanguage().title(UPDATED_TITLE);
        return nativeLanguage;
    }

    @BeforeEach
    public void initTest() {
        nativeLanguage = createEntity(em);
    }

    @Test
    @Transactional
    void createNativeLanguage() throws Exception {
        int databaseSizeBeforeCreate = nativeLanguageRepository.findAll().size();
        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);
        restNativeLanguageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeCreate + 1);
        NativeLanguage testNativeLanguage = nativeLanguageList.get(nativeLanguageList.size() - 1);
        assertThat(testNativeLanguage.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createNativeLanguageWithExistingId() throws Exception {
        // Create the NativeLanguage with an existing ID
        nativeLanguage.setId(1L);
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        int databaseSizeBeforeCreate = nativeLanguageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNativeLanguageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNativeLanguages() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList
        restNativeLanguageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nativeLanguage.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getNativeLanguage() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get the nativeLanguage
        restNativeLanguageMockMvc
            .perform(get(ENTITY_API_URL_ID, nativeLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nativeLanguage.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getNativeLanguagesByIdFiltering() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        Long id = nativeLanguage.getId();

        defaultNativeLanguageShouldBeFound("id.equals=" + id);
        defaultNativeLanguageShouldNotBeFound("id.notEquals=" + id);

        defaultNativeLanguageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNativeLanguageShouldNotBeFound("id.greaterThan=" + id);

        defaultNativeLanguageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNativeLanguageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNativeLanguagesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList where title equals to DEFAULT_TITLE
        defaultNativeLanguageShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the nativeLanguageList where title equals to UPDATED_TITLE
        defaultNativeLanguageShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNativeLanguagesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList where title not equals to DEFAULT_TITLE
        defaultNativeLanguageShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the nativeLanguageList where title not equals to UPDATED_TITLE
        defaultNativeLanguageShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNativeLanguagesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNativeLanguageShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the nativeLanguageList where title equals to UPDATED_TITLE
        defaultNativeLanguageShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNativeLanguagesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList where title is not null
        defaultNativeLanguageShouldBeFound("title.specified=true");

        // Get all the nativeLanguageList where title is null
        defaultNativeLanguageShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllNativeLanguagesByTitleContainsSomething() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList where title contains DEFAULT_TITLE
        defaultNativeLanguageShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the nativeLanguageList where title contains UPDATED_TITLE
        defaultNativeLanguageShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNativeLanguagesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        // Get all the nativeLanguageList where title does not contain DEFAULT_TITLE
        defaultNativeLanguageShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the nativeLanguageList where title does not contain UPDATED_TITLE
        defaultNativeLanguageShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNativeLanguageShouldBeFound(String filter) throws Exception {
        restNativeLanguageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nativeLanguage.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restNativeLanguageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNativeLanguageShouldNotBeFound(String filter) throws Exception {
        restNativeLanguageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNativeLanguageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNativeLanguage() throws Exception {
        // Get the nativeLanguage
        restNativeLanguageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNativeLanguage() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();

        // Update the nativeLanguage
        NativeLanguage updatedNativeLanguage = nativeLanguageRepository.findById(nativeLanguage.getId()).get();
        // Disconnect from session so that the updates on updatedNativeLanguage are not directly saved in db
        em.detach(updatedNativeLanguage);
        updatedNativeLanguage.title(UPDATED_TITLE);
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(updatedNativeLanguage);

        restNativeLanguageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nativeLanguageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isOk());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
        NativeLanguage testNativeLanguage = nativeLanguageList.get(nativeLanguageList.size() - 1);
        assertThat(testNativeLanguage.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingNativeLanguage() throws Exception {
        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();
        nativeLanguage.setId(count.incrementAndGet());

        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNativeLanguageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nativeLanguageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNativeLanguage() throws Exception {
        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();
        nativeLanguage.setId(count.incrementAndGet());

        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNativeLanguageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNativeLanguage() throws Exception {
        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();
        nativeLanguage.setId(count.incrementAndGet());

        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNativeLanguageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNativeLanguageWithPatch() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();

        // Update the nativeLanguage using partial update
        NativeLanguage partialUpdatedNativeLanguage = new NativeLanguage();
        partialUpdatedNativeLanguage.setId(nativeLanguage.getId());

        partialUpdatedNativeLanguage.title(UPDATED_TITLE);

        restNativeLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNativeLanguage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNativeLanguage))
            )
            .andExpect(status().isOk());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
        NativeLanguage testNativeLanguage = nativeLanguageList.get(nativeLanguageList.size() - 1);
        assertThat(testNativeLanguage.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateNativeLanguageWithPatch() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();

        // Update the nativeLanguage using partial update
        NativeLanguage partialUpdatedNativeLanguage = new NativeLanguage();
        partialUpdatedNativeLanguage.setId(nativeLanguage.getId());

        partialUpdatedNativeLanguage.title(UPDATED_TITLE);

        restNativeLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNativeLanguage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNativeLanguage))
            )
            .andExpect(status().isOk());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
        NativeLanguage testNativeLanguage = nativeLanguageList.get(nativeLanguageList.size() - 1);
        assertThat(testNativeLanguage.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingNativeLanguage() throws Exception {
        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();
        nativeLanguage.setId(count.incrementAndGet());

        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNativeLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nativeLanguageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNativeLanguage() throws Exception {
        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();
        nativeLanguage.setId(count.incrementAndGet());

        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNativeLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNativeLanguage() throws Exception {
        int databaseSizeBeforeUpdate = nativeLanguageRepository.findAll().size();
        nativeLanguage.setId(count.incrementAndGet());

        // Create the NativeLanguage
        NativeLanguageDTO nativeLanguageDTO = nativeLanguageMapper.toDto(nativeLanguage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNativeLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nativeLanguageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NativeLanguage in the database
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNativeLanguage() throws Exception {
        // Initialize the database
        nativeLanguageRepository.saveAndFlush(nativeLanguage);

        int databaseSizeBeforeDelete = nativeLanguageRepository.findAll().size();

        // Delete the nativeLanguage
        restNativeLanguageMockMvc
            .perform(delete(ENTITY_API_URL_ID, nativeLanguage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NativeLanguage> nativeLanguageList = nativeLanguageRepository.findAll();
        assertThat(nativeLanguageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
