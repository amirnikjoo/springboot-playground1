package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.StudyPreferences;
import com.mycompany.myapp.domain.enumeration.CourseIntensity;
import com.mycompany.myapp.domain.enumeration.LearningType;
import com.mycompany.myapp.domain.enumeration.SpacedRepetition;
import com.mycompany.myapp.repository.StudyPreferencesRepository;
import com.mycompany.myapp.service.criteria.StudyPreferencesCriteria;
import com.mycompany.myapp.service.dto.StudyPreferencesDTO;
import com.mycompany.myapp.service.mapper.StudyPreferencesMapper;
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
 * Integration tests for the {@link StudyPreferencesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudyPreferencesResourceIT {

    private static final SpacedRepetition DEFAULT_SPACED_REPETITION = SpacedRepetition.REPEAT_THREE_TIMES;
    private static final SpacedRepetition UPDATED_SPACED_REPETITION = SpacedRepetition.REPEAT_FIVE_TIMES;

    private static final CourseIntensity DEFAULT_COURSE_INTENSITY = CourseIntensity.REGULAR;
    private static final CourseIntensity UPDATED_COURSE_INTENSITY = CourseIntensity.INTENSIVE;

    private static final LearningType DEFAULT_LEARNING_TYPE = LearningType.TYPE_A;
    private static final LearningType UPDATED_LEARNING_TYPE = LearningType.TYPE_B;

    private static final String ENTITY_API_URL = "/api/study-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudyPreferencesRepository studyPreferencesRepository;

    @Autowired
    private StudyPreferencesMapper studyPreferencesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudyPreferencesMockMvc;

    private StudyPreferences studyPreferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyPreferences createEntity(EntityManager em) {
        StudyPreferences studyPreferences = new StudyPreferences()
            .spacedRepetition(DEFAULT_SPACED_REPETITION)
            .courseIntensity(DEFAULT_COURSE_INTENSITY)
            .learningType(DEFAULT_LEARNING_TYPE);
        return studyPreferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyPreferences createUpdatedEntity(EntityManager em) {
        StudyPreferences studyPreferences = new StudyPreferences()
            .spacedRepetition(UPDATED_SPACED_REPETITION)
            .courseIntensity(UPDATED_COURSE_INTENSITY)
            .learningType(UPDATED_LEARNING_TYPE);
        return studyPreferences;
    }

    @BeforeEach
    public void initTest() {
        studyPreferences = createEntity(em);
    }

    @Test
    @Transactional
    void createStudyPreferences() throws Exception {
        int databaseSizeBeforeCreate = studyPreferencesRepository.findAll().size();
        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);
        restStudyPreferencesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeCreate + 1);
        StudyPreferences testStudyPreferences = studyPreferencesList.get(studyPreferencesList.size() - 1);
        assertThat(testStudyPreferences.getSpacedRepetition()).isEqualTo(DEFAULT_SPACED_REPETITION);
        assertThat(testStudyPreferences.getCourseIntensity()).isEqualTo(DEFAULT_COURSE_INTENSITY);
        assertThat(testStudyPreferences.getLearningType()).isEqualTo(DEFAULT_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void createStudyPreferencesWithExistingId() throws Exception {
        // Create the StudyPreferences with an existing ID
        studyPreferences.setId(1L);
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        int databaseSizeBeforeCreate = studyPreferencesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyPreferencesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudyPreferences() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList
        restStudyPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyPreferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].spacedRepetition").value(hasItem(DEFAULT_SPACED_REPETITION.toString())))
            .andExpect(jsonPath("$.[*].courseIntensity").value(hasItem(DEFAULT_COURSE_INTENSITY.toString())))
            .andExpect(jsonPath("$.[*].learningType").value(hasItem(DEFAULT_LEARNING_TYPE.toString())));
    }

    @Test
    @Transactional
    void getStudyPreferences() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get the studyPreferences
        restStudyPreferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, studyPreferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studyPreferences.getId().intValue()))
            .andExpect(jsonPath("$.spacedRepetition").value(DEFAULT_SPACED_REPETITION.toString()))
            .andExpect(jsonPath("$.courseIntensity").value(DEFAULT_COURSE_INTENSITY.toString()))
            .andExpect(jsonPath("$.learningType").value(DEFAULT_LEARNING_TYPE.toString()));
    }

    @Test
    @Transactional
    void getStudyPreferencesByIdFiltering() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        Long id = studyPreferences.getId();

        defaultStudyPreferencesShouldBeFound("id.equals=" + id);
        defaultStudyPreferencesShouldNotBeFound("id.notEquals=" + id);

        defaultStudyPreferencesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudyPreferencesShouldNotBeFound("id.greaterThan=" + id);

        defaultStudyPreferencesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudyPreferencesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesBySpacedRepetitionIsEqualToSomething() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where spacedRepetition equals to DEFAULT_SPACED_REPETITION
        defaultStudyPreferencesShouldBeFound("spacedRepetition.equals=" + DEFAULT_SPACED_REPETITION);

        // Get all the studyPreferencesList where spacedRepetition equals to UPDATED_SPACED_REPETITION
        defaultStudyPreferencesShouldNotBeFound("spacedRepetition.equals=" + UPDATED_SPACED_REPETITION);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesBySpacedRepetitionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where spacedRepetition not equals to DEFAULT_SPACED_REPETITION
        defaultStudyPreferencesShouldNotBeFound("spacedRepetition.notEquals=" + DEFAULT_SPACED_REPETITION);

        // Get all the studyPreferencesList where spacedRepetition not equals to UPDATED_SPACED_REPETITION
        defaultStudyPreferencesShouldBeFound("spacedRepetition.notEquals=" + UPDATED_SPACED_REPETITION);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesBySpacedRepetitionIsInShouldWork() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where spacedRepetition in DEFAULT_SPACED_REPETITION or UPDATED_SPACED_REPETITION
        defaultStudyPreferencesShouldBeFound("spacedRepetition.in=" + DEFAULT_SPACED_REPETITION + "," + UPDATED_SPACED_REPETITION);

        // Get all the studyPreferencesList where spacedRepetition equals to UPDATED_SPACED_REPETITION
        defaultStudyPreferencesShouldNotBeFound("spacedRepetition.in=" + UPDATED_SPACED_REPETITION);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesBySpacedRepetitionIsNullOrNotNull() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where spacedRepetition is not null
        defaultStudyPreferencesShouldBeFound("spacedRepetition.specified=true");

        // Get all the studyPreferencesList where spacedRepetition is null
        defaultStudyPreferencesShouldNotBeFound("spacedRepetition.specified=false");
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByCourseIntensityIsEqualToSomething() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where courseIntensity equals to DEFAULT_COURSE_INTENSITY
        defaultStudyPreferencesShouldBeFound("courseIntensity.equals=" + DEFAULT_COURSE_INTENSITY);

        // Get all the studyPreferencesList where courseIntensity equals to UPDATED_COURSE_INTENSITY
        defaultStudyPreferencesShouldNotBeFound("courseIntensity.equals=" + UPDATED_COURSE_INTENSITY);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByCourseIntensityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where courseIntensity not equals to DEFAULT_COURSE_INTENSITY
        defaultStudyPreferencesShouldNotBeFound("courseIntensity.notEquals=" + DEFAULT_COURSE_INTENSITY);

        // Get all the studyPreferencesList where courseIntensity not equals to UPDATED_COURSE_INTENSITY
        defaultStudyPreferencesShouldBeFound("courseIntensity.notEquals=" + UPDATED_COURSE_INTENSITY);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByCourseIntensityIsInShouldWork() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where courseIntensity in DEFAULT_COURSE_INTENSITY or UPDATED_COURSE_INTENSITY
        defaultStudyPreferencesShouldBeFound("courseIntensity.in=" + DEFAULT_COURSE_INTENSITY + "," + UPDATED_COURSE_INTENSITY);

        // Get all the studyPreferencesList where courseIntensity equals to UPDATED_COURSE_INTENSITY
        defaultStudyPreferencesShouldNotBeFound("courseIntensity.in=" + UPDATED_COURSE_INTENSITY);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByCourseIntensityIsNullOrNotNull() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where courseIntensity is not null
        defaultStudyPreferencesShouldBeFound("courseIntensity.specified=true");

        // Get all the studyPreferencesList where courseIntensity is null
        defaultStudyPreferencesShouldNotBeFound("courseIntensity.specified=false");
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByLearningTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where learningType equals to DEFAULT_LEARNING_TYPE
        defaultStudyPreferencesShouldBeFound("learningType.equals=" + DEFAULT_LEARNING_TYPE);

        // Get all the studyPreferencesList where learningType equals to UPDATED_LEARNING_TYPE
        defaultStudyPreferencesShouldNotBeFound("learningType.equals=" + UPDATED_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByLearningTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where learningType not equals to DEFAULT_LEARNING_TYPE
        defaultStudyPreferencesShouldNotBeFound("learningType.notEquals=" + DEFAULT_LEARNING_TYPE);

        // Get all the studyPreferencesList where learningType not equals to UPDATED_LEARNING_TYPE
        defaultStudyPreferencesShouldBeFound("learningType.notEquals=" + UPDATED_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByLearningTypeIsInShouldWork() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where learningType in DEFAULT_LEARNING_TYPE or UPDATED_LEARNING_TYPE
        defaultStudyPreferencesShouldBeFound("learningType.in=" + DEFAULT_LEARNING_TYPE + "," + UPDATED_LEARNING_TYPE);

        // Get all the studyPreferencesList where learningType equals to UPDATED_LEARNING_TYPE
        defaultStudyPreferencesShouldNotBeFound("learningType.in=" + UPDATED_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void getAllStudyPreferencesByLearningTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        // Get all the studyPreferencesList where learningType is not null
        defaultStudyPreferencesShouldBeFound("learningType.specified=true");

        // Get all the studyPreferencesList where learningType is null
        defaultStudyPreferencesShouldNotBeFound("learningType.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudyPreferencesShouldBeFound(String filter) throws Exception {
        restStudyPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyPreferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].spacedRepetition").value(hasItem(DEFAULT_SPACED_REPETITION.toString())))
            .andExpect(jsonPath("$.[*].courseIntensity").value(hasItem(DEFAULT_COURSE_INTENSITY.toString())))
            .andExpect(jsonPath("$.[*].learningType").value(hasItem(DEFAULT_LEARNING_TYPE.toString())));

        // Check, that the count call also returns 1
        restStudyPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudyPreferencesShouldNotBeFound(String filter) throws Exception {
        restStudyPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudyPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStudyPreferences() throws Exception {
        // Get the studyPreferences
        restStudyPreferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudyPreferences() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();

        // Update the studyPreferences
        StudyPreferences updatedStudyPreferences = studyPreferencesRepository.findById(studyPreferences.getId()).get();
        // Disconnect from session so that the updates on updatedStudyPreferences are not directly saved in db
        em.detach(updatedStudyPreferences);
        updatedStudyPreferences
            .spacedRepetition(UPDATED_SPACED_REPETITION)
            .courseIntensity(UPDATED_COURSE_INTENSITY)
            .learningType(UPDATED_LEARNING_TYPE);
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(updatedStudyPreferences);

        restStudyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studyPreferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isOk());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
        StudyPreferences testStudyPreferences = studyPreferencesList.get(studyPreferencesList.size() - 1);
        assertThat(testStudyPreferences.getSpacedRepetition()).isEqualTo(UPDATED_SPACED_REPETITION);
        assertThat(testStudyPreferences.getCourseIntensity()).isEqualTo(UPDATED_COURSE_INTENSITY);
        assertThat(testStudyPreferences.getLearningType()).isEqualTo(UPDATED_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingStudyPreferences() throws Exception {
        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();
        studyPreferences.setId(count.incrementAndGet());

        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studyPreferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudyPreferences() throws Exception {
        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();
        studyPreferences.setId(count.incrementAndGet());

        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudyPreferences() throws Exception {
        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();
        studyPreferences.setId(count.incrementAndGet());

        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudyPreferencesWithPatch() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();

        // Update the studyPreferences using partial update
        StudyPreferences partialUpdatedStudyPreferences = new StudyPreferences();
        partialUpdatedStudyPreferences.setId(studyPreferences.getId());

        partialUpdatedStudyPreferences.courseIntensity(UPDATED_COURSE_INTENSITY).learningType(UPDATED_LEARNING_TYPE);

        restStudyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudyPreferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudyPreferences))
            )
            .andExpect(status().isOk());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
        StudyPreferences testStudyPreferences = studyPreferencesList.get(studyPreferencesList.size() - 1);
        assertThat(testStudyPreferences.getSpacedRepetition()).isEqualTo(DEFAULT_SPACED_REPETITION);
        assertThat(testStudyPreferences.getCourseIntensity()).isEqualTo(UPDATED_COURSE_INTENSITY);
        assertThat(testStudyPreferences.getLearningType()).isEqualTo(UPDATED_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateStudyPreferencesWithPatch() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();

        // Update the studyPreferences using partial update
        StudyPreferences partialUpdatedStudyPreferences = new StudyPreferences();
        partialUpdatedStudyPreferences.setId(studyPreferences.getId());

        partialUpdatedStudyPreferences
            .spacedRepetition(UPDATED_SPACED_REPETITION)
            .courseIntensity(UPDATED_COURSE_INTENSITY)
            .learningType(UPDATED_LEARNING_TYPE);

        restStudyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudyPreferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudyPreferences))
            )
            .andExpect(status().isOk());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
        StudyPreferences testStudyPreferences = studyPreferencesList.get(studyPreferencesList.size() - 1);
        assertThat(testStudyPreferences.getSpacedRepetition()).isEqualTo(UPDATED_SPACED_REPETITION);
        assertThat(testStudyPreferences.getCourseIntensity()).isEqualTo(UPDATED_COURSE_INTENSITY);
        assertThat(testStudyPreferences.getLearningType()).isEqualTo(UPDATED_LEARNING_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingStudyPreferences() throws Exception {
        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();
        studyPreferences.setId(count.incrementAndGet());

        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studyPreferencesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudyPreferences() throws Exception {
        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();
        studyPreferences.setId(count.incrementAndGet());

        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudyPreferences() throws Exception {
        int databaseSizeBeforeUpdate = studyPreferencesRepository.findAll().size();
        studyPreferences.setId(count.incrementAndGet());

        // Create the StudyPreferences
        StudyPreferencesDTO studyPreferencesDTO = studyPreferencesMapper.toDto(studyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studyPreferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudyPreferences in the database
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudyPreferences() throws Exception {
        // Initialize the database
        studyPreferencesRepository.saveAndFlush(studyPreferences);

        int databaseSizeBeforeDelete = studyPreferencesRepository.findAll().size();

        // Delete the studyPreferences
        restStudyPreferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, studyPreferences.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudyPreferences> studyPreferencesList = studyPreferencesRepository.findAll();
        assertThat(studyPreferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
