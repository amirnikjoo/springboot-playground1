package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Consumer;
import com.mycompany.myapp.repository.ConsumerRepository;
import com.mycompany.myapp.service.criteria.ConsumerCriteria;
import com.mycompany.myapp.service.dto.ConsumerDTO;
import com.mycompany.myapp.service.mapper.ConsumerMapper;
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
 * Service for executing complex queries for {@link Consumer} entities in the database.
 * The main input is a {@link ConsumerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsumerDTO} or a {@link Page} of {@link ConsumerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsumerQueryService extends QueryService<Consumer> {

    private final Logger log = LoggerFactory.getLogger(ConsumerQueryService.class);

    private final ConsumerRepository consumerRepository;

    private final ConsumerMapper consumerMapper;

    public ConsumerQueryService(ConsumerRepository consumerRepository, ConsumerMapper consumerMapper) {
        this.consumerRepository = consumerRepository;
        this.consumerMapper = consumerMapper;
    }

    /**
     * Return a {@link List} of {@link ConsumerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsumerDTO> findByCriteria(ConsumerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Consumer> specification = createSpecification(criteria);
        return consumerMapper.toDto(consumerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConsumerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumerDTO> findByCriteria(ConsumerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Consumer> specification = createSpecification(criteria);
        return consumerRepository.findAll(specification, page).map(consumerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsumerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Consumer> specification = createSpecification(criteria);
        return consumerRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsumerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Consumer> createSpecification(ConsumerCriteria criteria) {
        Specification<Consumer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Consumer_.id));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), Consumer_.ip));
            }
            if (criteria.getMacAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMacAddress(), Consumer_.macAddress));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeviceId(), Consumer_.deviceId));
            }
            if (criteria.getUserType() != null) {
                specification = specification.and(buildSpecification(criteria.getUserType(), Consumer_.userType));
            }
            if (criteria.getPersonalInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPersonalInfoId(),
                            root -> root.join(Consumer_.personalInfo, JoinType.LEFT).get(PersonalInfo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
