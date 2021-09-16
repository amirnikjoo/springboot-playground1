package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.NativeLanguage;
import com.mycompany.myapp.repository.NativeLanguageRepository;
import com.mycompany.myapp.service.criteria.NativeLanguageCriteria;
import com.mycompany.myapp.service.dto.NativeLanguageDTO;
import com.mycompany.myapp.service.mapper.NativeLanguageMapper;
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
 * Service for executing complex queries for {@link NativeLanguage} entities in the database.
 * The main input is a {@link NativeLanguageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NativeLanguageDTO} or a {@link Page} of {@link NativeLanguageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NativeLanguageQueryService extends QueryService<NativeLanguage> {

    private final Logger log = LoggerFactory.getLogger(NativeLanguageQueryService.class);

    private final NativeLanguageRepository nativeLanguageRepository;

    private final NativeLanguageMapper nativeLanguageMapper;

    public NativeLanguageQueryService(NativeLanguageRepository nativeLanguageRepository, NativeLanguageMapper nativeLanguageMapper) {
        this.nativeLanguageRepository = nativeLanguageRepository;
        this.nativeLanguageMapper = nativeLanguageMapper;
    }

    /**
     * Return a {@link List} of {@link NativeLanguageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NativeLanguageDTO> findByCriteria(NativeLanguageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NativeLanguage> specification = createSpecification(criteria);
        return nativeLanguageMapper.toDto(nativeLanguageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NativeLanguageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NativeLanguageDTO> findByCriteria(NativeLanguageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NativeLanguage> specification = createSpecification(criteria);
        return nativeLanguageRepository.findAll(specification, page).map(nativeLanguageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NativeLanguageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NativeLanguage> specification = createSpecification(criteria);
        return nativeLanguageRepository.count(specification);
    }

    /**
     * Function to convert {@link NativeLanguageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NativeLanguage> createSpecification(NativeLanguageCriteria criteria) {
        Specification<NativeLanguage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NativeLanguage_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), NativeLanguage_.title));
            }
        }
        return specification;
    }
}
