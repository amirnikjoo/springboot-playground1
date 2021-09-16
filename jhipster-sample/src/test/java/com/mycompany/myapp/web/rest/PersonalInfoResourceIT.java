package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PersonalInfo;
import com.mycompany.myapp.domain.enumeration.AuthenticationType;
import com.mycompany.myapp.repository.PersonalInfoRepository;
import com.mycompany.myapp.service.criteria.PersonalInfoCriteria;
import com.mycompany.myapp.service.dto.PersonalInfoDTO;
import com.mycompany.myapp.service.mapper.PersonalInfoMapper;
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
 * Integration tests for the {@link PersonalInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonalInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONSUMER_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_CONSUMER_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final AuthenticationType DEFAULT_AUTHENTICATION_TYPE = AuthenticationType.EMAIL;
    private static final AuthenticationType UPDATED_AUTHENTICATION_TYPE = AuthenticationType.MOBILE;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/personal-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private PersonalInfoMapper personalInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalInfoMockMvc;

    private PersonalInfo personalInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalInfo createEntity(EntityManager em) {
        PersonalInfo personalInfo = new PersonalInfo()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .username(DEFAULT_USERNAME)
            .consumerPhoto(DEFAULT_CONSUMER_PHOTO)
            .backgroundPhoto(DEFAULT_BACKGROUND_PHOTO)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .authenticationType(DEFAULT_AUTHENTICATION_TYPE)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .password(DEFAULT_PASSWORD);
        return personalInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalInfo createUpdatedEntity(EntityManager em) {
        PersonalInfo personalInfo = new PersonalInfo()
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .username(UPDATED_USERNAME)
            .consumerPhoto(UPDATED_CONSUMER_PHOTO)
            .backgroundPhoto(UPDATED_BACKGROUND_PHOTO)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authenticationType(UPDATED_AUTHENTICATION_TYPE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .password(UPDATED_PASSWORD);
        return personalInfo;
    }

    @BeforeEach
    public void initTest() {
        personalInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonalInfo() throws Exception {
        int databaseSizeBeforeCreate = personalInfoRepository.findAll().size();
        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);
        restPersonalInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonalInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonalInfo.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testPersonalInfo.getConsumerPhoto()).isEqualTo(DEFAULT_CONSUMER_PHOTO);
        assertThat(testPersonalInfo.getBackgroundPhoto()).isEqualTo(DEFAULT_BACKGROUND_PHOTO);
        assertThat(testPersonalInfo.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testPersonalInfo.getAuthenticationType()).isEqualTo(DEFAULT_AUTHENTICATION_TYPE);
        assertThat(testPersonalInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonalInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonalInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPersonalInfo.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void createPersonalInfoWithExistingId() throws Exception {
        // Create the PersonalInfo with an existing ID
        personalInfo.setId(1L);
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        int databaseSizeBeforeCreate = personalInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonalInfos() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList
        restPersonalInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].consumerPhoto").value(hasItem(DEFAULT_CONSUMER_PHOTO)))
            .andExpect(jsonPath("$.[*].backgroundPhoto").value(hasItem(DEFAULT_BACKGROUND_PHOTO)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].authenticationType").value(hasItem(DEFAULT_AUTHENTICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getPersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get the personalInfo
        restPersonalInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, personalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personalInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.consumerPhoto").value(DEFAULT_CONSUMER_PHOTO))
            .andExpect(jsonPath("$.backgroundPhoto").value(DEFAULT_BACKGROUND_PHOTO))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.authenticationType").value(DEFAULT_AUTHENTICATION_TYPE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getPersonalInfosByIdFiltering() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        Long id = personalInfo.getId();

        defaultPersonalInfoShouldBeFound("id.equals=" + id);
        defaultPersonalInfoShouldNotBeFound("id.notEquals=" + id);

        defaultPersonalInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonalInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonalInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonalInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name equals to DEFAULT_NAME
        defaultPersonalInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the personalInfoList where name equals to UPDATED_NAME
        defaultPersonalInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name not equals to DEFAULT_NAME
        defaultPersonalInfoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the personalInfoList where name not equals to UPDATED_NAME
        defaultPersonalInfoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPersonalInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the personalInfoList where name equals to UPDATED_NAME
        defaultPersonalInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name is not null
        defaultPersonalInfoShouldBeFound("name.specified=true");

        // Get all the personalInfoList where name is null
        defaultPersonalInfoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name contains DEFAULT_NAME
        defaultPersonalInfoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the personalInfoList where name contains UPDATED_NAME
        defaultPersonalInfoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name does not contain DEFAULT_NAME
        defaultPersonalInfoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the personalInfoList where name does not contain UPDATED_NAME
        defaultPersonalInfoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where lastName equals to DEFAULT_LAST_NAME
        defaultPersonalInfoShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the personalInfoList where lastName equals to UPDATED_LAST_NAME
        defaultPersonalInfoShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where lastName not equals to DEFAULT_LAST_NAME
        defaultPersonalInfoShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the personalInfoList where lastName not equals to UPDATED_LAST_NAME
        defaultPersonalInfoShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultPersonalInfoShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the personalInfoList where lastName equals to UPDATED_LAST_NAME
        defaultPersonalInfoShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where lastName is not null
        defaultPersonalInfoShouldBeFound("lastName.specified=true");

        // Get all the personalInfoList where lastName is null
        defaultPersonalInfoShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByLastNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where lastName contains DEFAULT_LAST_NAME
        defaultPersonalInfoShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the personalInfoList where lastName contains UPDATED_LAST_NAME
        defaultPersonalInfoShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where lastName does not contain DEFAULT_LAST_NAME
        defaultPersonalInfoShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the personalInfoList where lastName does not contain UPDATED_LAST_NAME
        defaultPersonalInfoShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where username equals to DEFAULT_USERNAME
        defaultPersonalInfoShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the personalInfoList where username equals to UPDATED_USERNAME
        defaultPersonalInfoShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where username not equals to DEFAULT_USERNAME
        defaultPersonalInfoShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the personalInfoList where username not equals to UPDATED_USERNAME
        defaultPersonalInfoShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultPersonalInfoShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the personalInfoList where username equals to UPDATED_USERNAME
        defaultPersonalInfoShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where username is not null
        defaultPersonalInfoShouldBeFound("username.specified=true");

        // Get all the personalInfoList where username is null
        defaultPersonalInfoShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByUsernameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where username contains DEFAULT_USERNAME
        defaultPersonalInfoShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the personalInfoList where username contains UPDATED_USERNAME
        defaultPersonalInfoShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where username does not contain DEFAULT_USERNAME
        defaultPersonalInfoShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the personalInfoList where username does not contain UPDATED_USERNAME
        defaultPersonalInfoShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByConsumerPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where consumerPhoto equals to DEFAULT_CONSUMER_PHOTO
        defaultPersonalInfoShouldBeFound("consumerPhoto.equals=" + DEFAULT_CONSUMER_PHOTO);

        // Get all the personalInfoList where consumerPhoto equals to UPDATED_CONSUMER_PHOTO
        defaultPersonalInfoShouldNotBeFound("consumerPhoto.equals=" + UPDATED_CONSUMER_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByConsumerPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where consumerPhoto not equals to DEFAULT_CONSUMER_PHOTO
        defaultPersonalInfoShouldNotBeFound("consumerPhoto.notEquals=" + DEFAULT_CONSUMER_PHOTO);

        // Get all the personalInfoList where consumerPhoto not equals to UPDATED_CONSUMER_PHOTO
        defaultPersonalInfoShouldBeFound("consumerPhoto.notEquals=" + UPDATED_CONSUMER_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByConsumerPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where consumerPhoto in DEFAULT_CONSUMER_PHOTO or UPDATED_CONSUMER_PHOTO
        defaultPersonalInfoShouldBeFound("consumerPhoto.in=" + DEFAULT_CONSUMER_PHOTO + "," + UPDATED_CONSUMER_PHOTO);

        // Get all the personalInfoList where consumerPhoto equals to UPDATED_CONSUMER_PHOTO
        defaultPersonalInfoShouldNotBeFound("consumerPhoto.in=" + UPDATED_CONSUMER_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByConsumerPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where consumerPhoto is not null
        defaultPersonalInfoShouldBeFound("consumerPhoto.specified=true");

        // Get all the personalInfoList where consumerPhoto is null
        defaultPersonalInfoShouldNotBeFound("consumerPhoto.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByConsumerPhotoContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where consumerPhoto contains DEFAULT_CONSUMER_PHOTO
        defaultPersonalInfoShouldBeFound("consumerPhoto.contains=" + DEFAULT_CONSUMER_PHOTO);

        // Get all the personalInfoList where consumerPhoto contains UPDATED_CONSUMER_PHOTO
        defaultPersonalInfoShouldNotBeFound("consumerPhoto.contains=" + UPDATED_CONSUMER_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByConsumerPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where consumerPhoto does not contain DEFAULT_CONSUMER_PHOTO
        defaultPersonalInfoShouldNotBeFound("consumerPhoto.doesNotContain=" + DEFAULT_CONSUMER_PHOTO);

        // Get all the personalInfoList where consumerPhoto does not contain UPDATED_CONSUMER_PHOTO
        defaultPersonalInfoShouldBeFound("consumerPhoto.doesNotContain=" + UPDATED_CONSUMER_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByBackgroundPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where backgroundPhoto equals to DEFAULT_BACKGROUND_PHOTO
        defaultPersonalInfoShouldBeFound("backgroundPhoto.equals=" + DEFAULT_BACKGROUND_PHOTO);

        // Get all the personalInfoList where backgroundPhoto equals to UPDATED_BACKGROUND_PHOTO
        defaultPersonalInfoShouldNotBeFound("backgroundPhoto.equals=" + UPDATED_BACKGROUND_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByBackgroundPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where backgroundPhoto not equals to DEFAULT_BACKGROUND_PHOTO
        defaultPersonalInfoShouldNotBeFound("backgroundPhoto.notEquals=" + DEFAULT_BACKGROUND_PHOTO);

        // Get all the personalInfoList where backgroundPhoto not equals to UPDATED_BACKGROUND_PHOTO
        defaultPersonalInfoShouldBeFound("backgroundPhoto.notEquals=" + UPDATED_BACKGROUND_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByBackgroundPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where backgroundPhoto in DEFAULT_BACKGROUND_PHOTO or UPDATED_BACKGROUND_PHOTO
        defaultPersonalInfoShouldBeFound("backgroundPhoto.in=" + DEFAULT_BACKGROUND_PHOTO + "," + UPDATED_BACKGROUND_PHOTO);

        // Get all the personalInfoList where backgroundPhoto equals to UPDATED_BACKGROUND_PHOTO
        defaultPersonalInfoShouldNotBeFound("backgroundPhoto.in=" + UPDATED_BACKGROUND_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByBackgroundPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where backgroundPhoto is not null
        defaultPersonalInfoShouldBeFound("backgroundPhoto.specified=true");

        // Get all the personalInfoList where backgroundPhoto is null
        defaultPersonalInfoShouldNotBeFound("backgroundPhoto.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByBackgroundPhotoContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where backgroundPhoto contains DEFAULT_BACKGROUND_PHOTO
        defaultPersonalInfoShouldBeFound("backgroundPhoto.contains=" + DEFAULT_BACKGROUND_PHOTO);

        // Get all the personalInfoList where backgroundPhoto contains UPDATED_BACKGROUND_PHOTO
        defaultPersonalInfoShouldNotBeFound("backgroundPhoto.contains=" + UPDATED_BACKGROUND_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByBackgroundPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where backgroundPhoto does not contain DEFAULT_BACKGROUND_PHOTO
        defaultPersonalInfoShouldNotBeFound("backgroundPhoto.doesNotContain=" + DEFAULT_BACKGROUND_PHOTO);

        // Get all the personalInfoList where backgroundPhoto does not contain UPDATED_BACKGROUND_PHOTO
        defaultPersonalInfoShouldBeFound("backgroundPhoto.doesNotContain=" + UPDATED_BACKGROUND_PHOTO);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByCountryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where countryCode equals to DEFAULT_COUNTRY_CODE
        defaultPersonalInfoShouldBeFound("countryCode.equals=" + DEFAULT_COUNTRY_CODE);

        // Get all the personalInfoList where countryCode equals to UPDATED_COUNTRY_CODE
        defaultPersonalInfoShouldNotBeFound("countryCode.equals=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByCountryCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where countryCode not equals to DEFAULT_COUNTRY_CODE
        defaultPersonalInfoShouldNotBeFound("countryCode.notEquals=" + DEFAULT_COUNTRY_CODE);

        // Get all the personalInfoList where countryCode not equals to UPDATED_COUNTRY_CODE
        defaultPersonalInfoShouldBeFound("countryCode.notEquals=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByCountryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where countryCode in DEFAULT_COUNTRY_CODE or UPDATED_COUNTRY_CODE
        defaultPersonalInfoShouldBeFound("countryCode.in=" + DEFAULT_COUNTRY_CODE + "," + UPDATED_COUNTRY_CODE);

        // Get all the personalInfoList where countryCode equals to UPDATED_COUNTRY_CODE
        defaultPersonalInfoShouldNotBeFound("countryCode.in=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByCountryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where countryCode is not null
        defaultPersonalInfoShouldBeFound("countryCode.specified=true");

        // Get all the personalInfoList where countryCode is null
        defaultPersonalInfoShouldNotBeFound("countryCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByCountryCodeContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where countryCode contains DEFAULT_COUNTRY_CODE
        defaultPersonalInfoShouldBeFound("countryCode.contains=" + DEFAULT_COUNTRY_CODE);

        // Get all the personalInfoList where countryCode contains UPDATED_COUNTRY_CODE
        defaultPersonalInfoShouldNotBeFound("countryCode.contains=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByCountryCodeNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where countryCode does not contain DEFAULT_COUNTRY_CODE
        defaultPersonalInfoShouldNotBeFound("countryCode.doesNotContain=" + DEFAULT_COUNTRY_CODE);

        // Get all the personalInfoList where countryCode does not contain UPDATED_COUNTRY_CODE
        defaultPersonalInfoShouldBeFound("countryCode.doesNotContain=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAuthenticationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where authenticationType equals to DEFAULT_AUTHENTICATION_TYPE
        defaultPersonalInfoShouldBeFound("authenticationType.equals=" + DEFAULT_AUTHENTICATION_TYPE);

        // Get all the personalInfoList where authenticationType equals to UPDATED_AUTHENTICATION_TYPE
        defaultPersonalInfoShouldNotBeFound("authenticationType.equals=" + UPDATED_AUTHENTICATION_TYPE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAuthenticationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where authenticationType not equals to DEFAULT_AUTHENTICATION_TYPE
        defaultPersonalInfoShouldNotBeFound("authenticationType.notEquals=" + DEFAULT_AUTHENTICATION_TYPE);

        // Get all the personalInfoList where authenticationType not equals to UPDATED_AUTHENTICATION_TYPE
        defaultPersonalInfoShouldBeFound("authenticationType.notEquals=" + UPDATED_AUTHENTICATION_TYPE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAuthenticationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where authenticationType in DEFAULT_AUTHENTICATION_TYPE or UPDATED_AUTHENTICATION_TYPE
        defaultPersonalInfoShouldBeFound("authenticationType.in=" + DEFAULT_AUTHENTICATION_TYPE + "," + UPDATED_AUTHENTICATION_TYPE);

        // Get all the personalInfoList where authenticationType equals to UPDATED_AUTHENTICATION_TYPE
        defaultPersonalInfoShouldNotBeFound("authenticationType.in=" + UPDATED_AUTHENTICATION_TYPE);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAuthenticationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where authenticationType is not null
        defaultPersonalInfoShouldBeFound("authenticationType.specified=true");

        // Get all the personalInfoList where authenticationType is null
        defaultPersonalInfoShouldNotBeFound("authenticationType.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where email equals to DEFAULT_EMAIL
        defaultPersonalInfoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the personalInfoList where email equals to UPDATED_EMAIL
        defaultPersonalInfoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where email not equals to DEFAULT_EMAIL
        defaultPersonalInfoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the personalInfoList where email not equals to UPDATED_EMAIL
        defaultPersonalInfoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPersonalInfoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the personalInfoList where email equals to UPDATED_EMAIL
        defaultPersonalInfoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where email is not null
        defaultPersonalInfoShouldBeFound("email.specified=true");

        // Get all the personalInfoList where email is null
        defaultPersonalInfoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByEmailContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where email contains DEFAULT_EMAIL
        defaultPersonalInfoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the personalInfoList where email contains UPDATED_EMAIL
        defaultPersonalInfoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where email does not contain DEFAULT_EMAIL
        defaultPersonalInfoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the personalInfoList where email does not contain UPDATED_EMAIL
        defaultPersonalInfoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultPersonalInfoShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the personalInfoList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPersonalInfoShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultPersonalInfoShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the personalInfoList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultPersonalInfoShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultPersonalInfoShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the personalInfoList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPersonalInfoShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where phoneNumber is not null
        defaultPersonalInfoShouldBeFound("phoneNumber.specified=true");

        // Get all the personalInfoList where phoneNumber is null
        defaultPersonalInfoShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultPersonalInfoShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the personalInfoList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultPersonalInfoShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultPersonalInfoShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the personalInfoList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultPersonalInfoShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where address equals to DEFAULT_ADDRESS
        defaultPersonalInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the personalInfoList where address equals to UPDATED_ADDRESS
        defaultPersonalInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where address not equals to DEFAULT_ADDRESS
        defaultPersonalInfoShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the personalInfoList where address not equals to UPDATED_ADDRESS
        defaultPersonalInfoShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultPersonalInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the personalInfoList where address equals to UPDATED_ADDRESS
        defaultPersonalInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where address is not null
        defaultPersonalInfoShouldBeFound("address.specified=true");

        // Get all the personalInfoList where address is null
        defaultPersonalInfoShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where address contains DEFAULT_ADDRESS
        defaultPersonalInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the personalInfoList where address contains UPDATED_ADDRESS
        defaultPersonalInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where address does not contain DEFAULT_ADDRESS
        defaultPersonalInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the personalInfoList where address does not contain UPDATED_ADDRESS
        defaultPersonalInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where password equals to DEFAULT_PASSWORD
        defaultPersonalInfoShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the personalInfoList where password equals to UPDATED_PASSWORD
        defaultPersonalInfoShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where password not equals to DEFAULT_PASSWORD
        defaultPersonalInfoShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the personalInfoList where password not equals to UPDATED_PASSWORD
        defaultPersonalInfoShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultPersonalInfoShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the personalInfoList where password equals to UPDATED_PASSWORD
        defaultPersonalInfoShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where password is not null
        defaultPersonalInfoShouldBeFound("password.specified=true");

        // Get all the personalInfoList where password is null
        defaultPersonalInfoShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPasswordContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where password contains DEFAULT_PASSWORD
        defaultPersonalInfoShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the personalInfoList where password contains UPDATED_PASSWORD
        defaultPersonalInfoShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPersonalInfosByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where password does not contain DEFAULT_PASSWORD
        defaultPersonalInfoShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the personalInfoList where password does not contain UPDATED_PASSWORD
        defaultPersonalInfoShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonalInfoShouldBeFound(String filter) throws Exception {
        restPersonalInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].consumerPhoto").value(hasItem(DEFAULT_CONSUMER_PHOTO)))
            .andExpect(jsonPath("$.[*].backgroundPhoto").value(hasItem(DEFAULT_BACKGROUND_PHOTO)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].authenticationType").value(hasItem(DEFAULT_AUTHENTICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));

        // Check, that the count call also returns 1
        restPersonalInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonalInfoShouldNotBeFound(String filter) throws Exception {
        restPersonalInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonalInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPersonalInfo() throws Exception {
        // Get the personalInfo
        restPersonalInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // Update the personalInfo
        PersonalInfo updatedPersonalInfo = personalInfoRepository.findById(personalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalInfo are not directly saved in db
        em.detach(updatedPersonalInfo);
        updatedPersonalInfo
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .username(UPDATED_USERNAME)
            .consumerPhoto(UPDATED_CONSUMER_PHOTO)
            .backgroundPhoto(UPDATED_BACKGROUND_PHOTO)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authenticationType(UPDATED_AUTHENTICATION_TYPE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .password(UPDATED_PASSWORD);
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(updatedPersonalInfo);

        restPersonalInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonalInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonalInfo.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPersonalInfo.getConsumerPhoto()).isEqualTo(UPDATED_CONSUMER_PHOTO);
        assertThat(testPersonalInfo.getBackgroundPhoto()).isEqualTo(UPDATED_BACKGROUND_PHOTO);
        assertThat(testPersonalInfo.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testPersonalInfo.getAuthenticationType()).isEqualTo(UPDATED_AUTHENTICATION_TYPE);
        assertThat(testPersonalInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonalInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonalInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void putNonExistingPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();
        personalInfo.setId(count.incrementAndGet());

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();
        personalInfo.setId(count.incrementAndGet());

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();
        personalInfo.setId(count.incrementAndGet());

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonalInfoWithPatch() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // Update the personalInfo using partial update
        PersonalInfo partialUpdatedPersonalInfo = new PersonalInfo();
        partialUpdatedPersonalInfo.setId(personalInfo.getId());

        partialUpdatedPersonalInfo
            .username(UPDATED_USERNAME)
            .consumerPhoto(UPDATED_CONSUMER_PHOTO)
            .countryCode(UPDATED_COUNTRY_CODE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .password(UPDATED_PASSWORD);

        restPersonalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalInfo))
            )
            .andExpect(status().isOk());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonalInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonalInfo.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPersonalInfo.getConsumerPhoto()).isEqualTo(UPDATED_CONSUMER_PHOTO);
        assertThat(testPersonalInfo.getBackgroundPhoto()).isEqualTo(DEFAULT_BACKGROUND_PHOTO);
        assertThat(testPersonalInfo.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testPersonalInfo.getAuthenticationType()).isEqualTo(DEFAULT_AUTHENTICATION_TYPE);
        assertThat(testPersonalInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonalInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonalInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void fullUpdatePersonalInfoWithPatch() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // Update the personalInfo using partial update
        PersonalInfo partialUpdatedPersonalInfo = new PersonalInfo();
        partialUpdatedPersonalInfo.setId(personalInfo.getId());

        partialUpdatedPersonalInfo
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .username(UPDATED_USERNAME)
            .consumerPhoto(UPDATED_CONSUMER_PHOTO)
            .backgroundPhoto(UPDATED_BACKGROUND_PHOTO)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authenticationType(UPDATED_AUTHENTICATION_TYPE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .password(UPDATED_PASSWORD);

        restPersonalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalInfo))
            )
            .andExpect(status().isOk());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonalInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonalInfo.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPersonalInfo.getConsumerPhoto()).isEqualTo(UPDATED_CONSUMER_PHOTO);
        assertThat(testPersonalInfo.getBackgroundPhoto()).isEqualTo(UPDATED_BACKGROUND_PHOTO);
        assertThat(testPersonalInfo.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testPersonalInfo.getAuthenticationType()).isEqualTo(UPDATED_AUTHENTICATION_TYPE);
        assertThat(testPersonalInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonalInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonalInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void patchNonExistingPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();
        personalInfo.setId(count.incrementAndGet());

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personalInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();
        personalInfo.setId(count.incrementAndGet());

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();
        personalInfo.setId(count.incrementAndGet());

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        int databaseSizeBeforeDelete = personalInfoRepository.findAll().size();

        // Delete the personalInfo
        restPersonalInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, personalInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
