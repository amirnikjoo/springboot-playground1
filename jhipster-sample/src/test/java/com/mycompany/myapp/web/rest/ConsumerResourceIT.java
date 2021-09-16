package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Consumer;
import com.mycompany.myapp.domain.PersonalInfo;
import com.mycompany.myapp.domain.enumeration.ConsumerType;
import com.mycompany.myapp.repository.ConsumerRepository;
import com.mycompany.myapp.service.criteria.ConsumerCriteria;
import com.mycompany.myapp.service.dto.ConsumerDTO;
import com.mycompany.myapp.service.mapper.ConsumerMapper;
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
 * Integration tests for the {@link ConsumerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsumerResourceIT {

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_MAC_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_MAC_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final ConsumerType DEFAULT_USER_TYPE = ConsumerType.VISITOR;
    private static final ConsumerType UPDATED_USER_TYPE = ConsumerType.CUSTOMER;

    private static final String ENTITY_API_URL = "/api/consumers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsumerMockMvc;

    private Consumer consumer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consumer createEntity(EntityManager em) {
        Consumer consumer = new Consumer()
            .ip(DEFAULT_IP)
            .macAddress(DEFAULT_MAC_ADDRESS)
            .deviceId(DEFAULT_DEVICE_ID)
            .userType(DEFAULT_USER_TYPE);
        return consumer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consumer createUpdatedEntity(EntityManager em) {
        Consumer consumer = new Consumer()
            .ip(UPDATED_IP)
            .macAddress(UPDATED_MAC_ADDRESS)
            .deviceId(UPDATED_DEVICE_ID)
            .userType(UPDATED_USER_TYPE);
        return consumer;
    }

    @BeforeEach
    public void initTest() {
        consumer = createEntity(em);
    }

    @Test
    @Transactional
    void createConsumer() throws Exception {
        int databaseSizeBeforeCreate = consumerRepository.findAll().size();
        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);
        restConsumerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consumerDTO)))
            .andExpect(status().isCreated());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeCreate + 1);
        Consumer testConsumer = consumerList.get(consumerList.size() - 1);
        assertThat(testConsumer.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testConsumer.getMacAddress()).isEqualTo(DEFAULT_MAC_ADDRESS);
        assertThat(testConsumer.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testConsumer.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
    }

    @Test
    @Transactional
    void createConsumerWithExistingId() throws Exception {
        // Create the Consumer with an existing ID
        consumer.setId(1L);
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        int databaseSizeBeforeCreate = consumerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consumerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConsumers() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList
        restConsumerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumer.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].macAddress").value(hasItem(DEFAULT_MAC_ADDRESS)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())));
    }

    @Test
    @Transactional
    void getConsumer() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get the consumer
        restConsumerMockMvc
            .perform(get(ENTITY_API_URL_ID, consumer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consumer.getId().intValue()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.macAddress").value(DEFAULT_MAC_ADDRESS))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()));
    }

    @Test
    @Transactional
    void getConsumersByIdFiltering() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        Long id = consumer.getId();

        defaultConsumerShouldBeFound("id.equals=" + id);
        defaultConsumerShouldNotBeFound("id.notEquals=" + id);

        defaultConsumerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsumerShouldNotBeFound("id.greaterThan=" + id);

        defaultConsumerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsumerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConsumersByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where ip equals to DEFAULT_IP
        defaultConsumerShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the consumerList where ip equals to UPDATED_IP
        defaultConsumerShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllConsumersByIpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where ip not equals to DEFAULT_IP
        defaultConsumerShouldNotBeFound("ip.notEquals=" + DEFAULT_IP);

        // Get all the consumerList where ip not equals to UPDATED_IP
        defaultConsumerShouldBeFound("ip.notEquals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllConsumersByIpIsInShouldWork() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where ip in DEFAULT_IP or UPDATED_IP
        defaultConsumerShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the consumerList where ip equals to UPDATED_IP
        defaultConsumerShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllConsumersByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where ip is not null
        defaultConsumerShouldBeFound("ip.specified=true");

        // Get all the consumerList where ip is null
        defaultConsumerShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumersByIpContainsSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where ip contains DEFAULT_IP
        defaultConsumerShouldBeFound("ip.contains=" + DEFAULT_IP);

        // Get all the consumerList where ip contains UPDATED_IP
        defaultConsumerShouldNotBeFound("ip.contains=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllConsumersByIpNotContainsSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where ip does not contain DEFAULT_IP
        defaultConsumerShouldNotBeFound("ip.doesNotContain=" + DEFAULT_IP);

        // Get all the consumerList where ip does not contain UPDATED_IP
        defaultConsumerShouldBeFound("ip.doesNotContain=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllConsumersByMacAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where macAddress equals to DEFAULT_MAC_ADDRESS
        defaultConsumerShouldBeFound("macAddress.equals=" + DEFAULT_MAC_ADDRESS);

        // Get all the consumerList where macAddress equals to UPDATED_MAC_ADDRESS
        defaultConsumerShouldNotBeFound("macAddress.equals=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllConsumersByMacAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where macAddress not equals to DEFAULT_MAC_ADDRESS
        defaultConsumerShouldNotBeFound("macAddress.notEquals=" + DEFAULT_MAC_ADDRESS);

        // Get all the consumerList where macAddress not equals to UPDATED_MAC_ADDRESS
        defaultConsumerShouldBeFound("macAddress.notEquals=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllConsumersByMacAddressIsInShouldWork() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where macAddress in DEFAULT_MAC_ADDRESS or UPDATED_MAC_ADDRESS
        defaultConsumerShouldBeFound("macAddress.in=" + DEFAULT_MAC_ADDRESS + "," + UPDATED_MAC_ADDRESS);

        // Get all the consumerList where macAddress equals to UPDATED_MAC_ADDRESS
        defaultConsumerShouldNotBeFound("macAddress.in=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllConsumersByMacAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where macAddress is not null
        defaultConsumerShouldBeFound("macAddress.specified=true");

        // Get all the consumerList where macAddress is null
        defaultConsumerShouldNotBeFound("macAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumersByMacAddressContainsSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where macAddress contains DEFAULT_MAC_ADDRESS
        defaultConsumerShouldBeFound("macAddress.contains=" + DEFAULT_MAC_ADDRESS);

        // Get all the consumerList where macAddress contains UPDATED_MAC_ADDRESS
        defaultConsumerShouldNotBeFound("macAddress.contains=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllConsumersByMacAddressNotContainsSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where macAddress does not contain DEFAULT_MAC_ADDRESS
        defaultConsumerShouldNotBeFound("macAddress.doesNotContain=" + DEFAULT_MAC_ADDRESS);

        // Get all the consumerList where macAddress does not contain UPDATED_MAC_ADDRESS
        defaultConsumerShouldBeFound("macAddress.doesNotContain=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllConsumersByDeviceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where deviceId equals to DEFAULT_DEVICE_ID
        defaultConsumerShouldBeFound("deviceId.equals=" + DEFAULT_DEVICE_ID);

        // Get all the consumerList where deviceId equals to UPDATED_DEVICE_ID
        defaultConsumerShouldNotBeFound("deviceId.equals=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    void getAllConsumersByDeviceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where deviceId not equals to DEFAULT_DEVICE_ID
        defaultConsumerShouldNotBeFound("deviceId.notEquals=" + DEFAULT_DEVICE_ID);

        // Get all the consumerList where deviceId not equals to UPDATED_DEVICE_ID
        defaultConsumerShouldBeFound("deviceId.notEquals=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    void getAllConsumersByDeviceIdIsInShouldWork() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where deviceId in DEFAULT_DEVICE_ID or UPDATED_DEVICE_ID
        defaultConsumerShouldBeFound("deviceId.in=" + DEFAULT_DEVICE_ID + "," + UPDATED_DEVICE_ID);

        // Get all the consumerList where deviceId equals to UPDATED_DEVICE_ID
        defaultConsumerShouldNotBeFound("deviceId.in=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    void getAllConsumersByDeviceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where deviceId is not null
        defaultConsumerShouldBeFound("deviceId.specified=true");

        // Get all the consumerList where deviceId is null
        defaultConsumerShouldNotBeFound("deviceId.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumersByDeviceIdContainsSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where deviceId contains DEFAULT_DEVICE_ID
        defaultConsumerShouldBeFound("deviceId.contains=" + DEFAULT_DEVICE_ID);

        // Get all the consumerList where deviceId contains UPDATED_DEVICE_ID
        defaultConsumerShouldNotBeFound("deviceId.contains=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    void getAllConsumersByDeviceIdNotContainsSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where deviceId does not contain DEFAULT_DEVICE_ID
        defaultConsumerShouldNotBeFound("deviceId.doesNotContain=" + DEFAULT_DEVICE_ID);

        // Get all the consumerList where deviceId does not contain UPDATED_DEVICE_ID
        defaultConsumerShouldBeFound("deviceId.doesNotContain=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    void getAllConsumersByUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where userType equals to DEFAULT_USER_TYPE
        defaultConsumerShouldBeFound("userType.equals=" + DEFAULT_USER_TYPE);

        // Get all the consumerList where userType equals to UPDATED_USER_TYPE
        defaultConsumerShouldNotBeFound("userType.equals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void getAllConsumersByUserTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where userType not equals to DEFAULT_USER_TYPE
        defaultConsumerShouldNotBeFound("userType.notEquals=" + DEFAULT_USER_TYPE);

        // Get all the consumerList where userType not equals to UPDATED_USER_TYPE
        defaultConsumerShouldBeFound("userType.notEquals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void getAllConsumersByUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where userType in DEFAULT_USER_TYPE or UPDATED_USER_TYPE
        defaultConsumerShouldBeFound("userType.in=" + DEFAULT_USER_TYPE + "," + UPDATED_USER_TYPE);

        // Get all the consumerList where userType equals to UPDATED_USER_TYPE
        defaultConsumerShouldNotBeFound("userType.in=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void getAllConsumersByUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        // Get all the consumerList where userType is not null
        defaultConsumerShouldBeFound("userType.specified=true");

        // Get all the consumerList where userType is null
        defaultConsumerShouldNotBeFound("userType.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumersByPersonalInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);
        PersonalInfo personalInfo = PersonalInfoResourceIT.createEntity(em);
        em.persist(personalInfo);
        em.flush();
        consumer.setPersonalInfo(personalInfo);
        consumerRepository.saveAndFlush(consumer);
        Long personalInfoId = personalInfo.getId();

        // Get all the consumerList where personalInfo equals to personalInfoId
        defaultConsumerShouldBeFound("personalInfoId.equals=" + personalInfoId);

        // Get all the consumerList where personalInfo equals to (personalInfoId + 1)
        defaultConsumerShouldNotBeFound("personalInfoId.equals=" + (personalInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsumerShouldBeFound(String filter) throws Exception {
        restConsumerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumer.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].macAddress").value(hasItem(DEFAULT_MAC_ADDRESS)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())));

        // Check, that the count call also returns 1
        restConsumerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsumerShouldNotBeFound(String filter) throws Exception {
        restConsumerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsumerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConsumer() throws Exception {
        // Get the consumer
        restConsumerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConsumer() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();

        // Update the consumer
        Consumer updatedConsumer = consumerRepository.findById(consumer.getId()).get();
        // Disconnect from session so that the updates on updatedConsumer are not directly saved in db
        em.detach(updatedConsumer);
        updatedConsumer.ip(UPDATED_IP).macAddress(UPDATED_MAC_ADDRESS).deviceId(UPDATED_DEVICE_ID).userType(UPDATED_USER_TYPE);
        ConsumerDTO consumerDTO = consumerMapper.toDto(updatedConsumer);

        restConsumerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
        Consumer testConsumer = consumerList.get(consumerList.size() - 1);
        assertThat(testConsumer.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testConsumer.getMacAddress()).isEqualTo(UPDATED_MAC_ADDRESS);
        assertThat(testConsumer.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testConsumer.getUserType()).isEqualTo(UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingConsumer() throws Exception {
        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();
        consumer.setId(count.incrementAndGet());

        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsumer() throws Exception {
        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();
        consumer.setId(count.incrementAndGet());

        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsumer() throws Exception {
        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();
        consumer.setId(count.incrementAndGet());

        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consumerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsumerWithPatch() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();

        // Update the consumer using partial update
        Consumer partialUpdatedConsumer = new Consumer();
        partialUpdatedConsumer.setId(consumer.getId());

        partialUpdatedConsumer.ip(UPDATED_IP).macAddress(UPDATED_MAC_ADDRESS);

        restConsumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsumer))
            )
            .andExpect(status().isOk());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
        Consumer testConsumer = consumerList.get(consumerList.size() - 1);
        assertThat(testConsumer.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testConsumer.getMacAddress()).isEqualTo(UPDATED_MAC_ADDRESS);
        assertThat(testConsumer.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testConsumer.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateConsumerWithPatch() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();

        // Update the consumer using partial update
        Consumer partialUpdatedConsumer = new Consumer();
        partialUpdatedConsumer.setId(consumer.getId());

        partialUpdatedConsumer.ip(UPDATED_IP).macAddress(UPDATED_MAC_ADDRESS).deviceId(UPDATED_DEVICE_ID).userType(UPDATED_USER_TYPE);

        restConsumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsumer))
            )
            .andExpect(status().isOk());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
        Consumer testConsumer = consumerList.get(consumerList.size() - 1);
        assertThat(testConsumer.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testConsumer.getMacAddress()).isEqualTo(UPDATED_MAC_ADDRESS);
        assertThat(testConsumer.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testConsumer.getUserType()).isEqualTo(UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingConsumer() throws Exception {
        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();
        consumer.setId(count.incrementAndGet());

        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consumerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsumer() throws Exception {
        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();
        consumer.setId(count.incrementAndGet());

        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsumer() throws Exception {
        int databaseSizeBeforeUpdate = consumerRepository.findAll().size();
        consumer.setId(count.incrementAndGet());

        // Create the Consumer
        ConsumerDTO consumerDTO = consumerMapper.toDto(consumer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(consumerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consumer in the database
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsumer() throws Exception {
        // Initialize the database
        consumerRepository.saveAndFlush(consumer);

        int databaseSizeBeforeDelete = consumerRepository.findAll().size();

        // Delete the consumer
        restConsumerMockMvc
            .perform(delete(ENTITY_API_URL_ID, consumer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consumer> consumerList = consumerRepository.findAll();
        assertThat(consumerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
