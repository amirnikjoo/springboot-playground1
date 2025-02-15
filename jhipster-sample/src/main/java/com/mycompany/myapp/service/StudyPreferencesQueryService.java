package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.StudyPreferences;
import com.mycompany.myapp.repository.StudyPreferencesRepository;
import com.mycompany.myapp.service.criteria.StudyPreferencesCriteria;
import com.mycompany.myapp.service.dto.StudyPreferencesDTO;
import com.mycompany.myapp.service.mapper.StudyPreferencesMapper;
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
 * Service for executing complex queries for {@link StudyPreferences} entities in the database.
 * The main input is a {@link StudyPreferencesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudyPreferencesDTO} or a {@link Page} of {@link StudyPreferencesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudyPreferencesQueryService extends QueryService<StudyPreferences> {

    private final Logger log = LoggerFactory.getLogger(StudyPreferencesQueryService.class);

    private final StudyPreferencesRepository studyPreferencesRepository;

    private final StudyPreferencesMapper studyPreferencesMapper;

    public StudyPreferencesQueryService(
        StudyPreferencesRepository studyPreferencesRepository,
        StudyPreferencesMapper studyPreferencesMapper
    ) {
        this.studyPreferencesRepository = studyPreferencesRepository;
        this.studyPreferencesMapper = studyPreferencesMapper;
    }

    /**
     * Return a {@link List} of {@link StudyPreferencesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudyPreferencesDTO> findByCriteria(StudyPreferencesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudyPreferences> specification = createSpecification(criteria);
        return studyPreferencesMapper.toDto(studyPreferencesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StudyPreferencesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudyPreferencesDTO> findByCriteria(StudyPreferencesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudyPreferences> specification = createSpecification(criteria);
        return studyPreferencesRepository.findAll(specification, page).map(studyPreferencesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudyPreferencesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudyPreferences> specification = createSpecification(criteria);
        return studyPreferencesRepository.count(specification);
    }

    /**
     * Function to convert {@link StudyPreferencesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudyPreferences> createSpecification(StudyPreferencesCriteria criteria) {
        Specification<StudyPreferences> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudyPreferences_.id));
            }
            if (criteria.getSpacedRepetition() != null) {
                specification = specification.and(buildSpecification(criteria.getSpacedRepetition(), StudyPreferences_.spacedRepetition));
            }
            if (criteria.getCourseIntensity() != null) {
                specification = specification.and(buildSpecification(criteria.getCourseIntensity(), StudyPreferences_.courseIntensity));
            }
            if (criteria.getLearningType() != null) {
                specification = specification.and(buildSpecification(criteria.getLearningType(), StudyPreferences_.learningType));
            }
        }
        return specification;
    }
}
