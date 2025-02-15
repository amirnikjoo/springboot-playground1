package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PersonalInfo;
import com.mycompany.myapp.repository.PersonalInfoRepository;
import com.mycompany.myapp.service.criteria.PersonalInfoCriteria;
import com.mycompany.myapp.service.dto.PersonalInfoDTO;
import com.mycompany.myapp.service.mapper.PersonalInfoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PersonalInfo} entities in the database.
 * The main input is a {@link PersonalInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonalInfoDTO} or a {@link Page} of {@link PersonalInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonalInfoQueryService extends QueryService<PersonalInfo> {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoQueryService.class);

    private final PersonalInfoRepository personalInfoRepository;

    private final PersonalInfoMapper personalInfoMapper;

    public PersonalInfoQueryService(PersonalInfoRepository personalInfoRepository, PersonalInfoMapper personalInfoMapper) {
        this.personalInfoRepository = personalInfoRepository;
        this.personalInfoMapper = personalInfoMapper;
    }

    /**
     * Return a {@link List} of {@link PersonalInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalInfoDTO> findByCriteria(PersonalInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonalInfo> specification = createSpecification(criteria);
        return personalInfoMapper.toDto(personalInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PersonalInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalInfoDTO> findByCriteria(PersonalInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonalInfo> specification = createSpecification(criteria);
        return personalInfoRepository.findAll(specification, page).map(personalInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonalInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonalInfo> specification = createSpecification(criteria);
        return personalInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonalInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PersonalInfo> createSpecification(PersonalInfoCriteria criteria) {
        Specification<PersonalInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PersonalInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PersonalInfo_.name));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), PersonalInfo_.lastName));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), PersonalInfo_.username));
            }
            if (criteria.getConsumerPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConsumerPhoto(), PersonalInfo_.consumerPhoto));
            }
            if (criteria.getBackgroundPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBackgroundPhoto(), PersonalInfo_.backgroundPhoto));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), PersonalInfo_.countryCode));
            }
            if (criteria.getAuthenticationType() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthenticationType(), PersonalInfo_.authenticationType));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), PersonalInfo_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), PersonalInfo_.phoneNumber));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), PersonalInfo_.address));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), PersonalInfo_.password));
            }
        }
        return specification;
    }
}
