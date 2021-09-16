package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.StudyPreferences;
import com.mycompany.myapp.repository.StudyPreferencesRepository;
import com.mycompany.myapp.service.StudyPreferencesService;
import com.mycompany.myapp.service.dto.StudyPreferencesDTO;
import com.mycompany.myapp.service.mapper.StudyPreferencesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StudyPreferences}.
 */
@Service
@Transactional
public class StudyPreferencesServiceImpl implements StudyPreferencesService {

    private final Logger log = LoggerFactory.getLogger(StudyPreferencesServiceImpl.class);

    private final StudyPreferencesRepository studyPreferencesRepository;

    private final StudyPreferencesMapper studyPreferencesMapper;

    public StudyPreferencesServiceImpl(
        StudyPreferencesRepository studyPreferencesRepository,
        StudyPreferencesMapper studyPreferencesMapper
    ) {
        this.studyPreferencesRepository = studyPreferencesRepository;
        this.studyPreferencesMapper = studyPreferencesMapper;
    }

    @Override
    public StudyPreferencesDTO save(StudyPreferencesDTO studyPreferencesDTO) {
        log.debug("Request to save StudyPreferences : {}", studyPreferencesDTO);
        StudyPreferences studyPreferences = studyPreferencesMapper.toEntity(studyPreferencesDTO);
        studyPreferences = studyPreferencesRepository.save(studyPreferences);
        return studyPreferencesMapper.toDto(studyPreferences);
    }

    @Override
    public Optional<StudyPreferencesDTO> partialUpdate(StudyPreferencesDTO studyPreferencesDTO) {
        log.debug("Request to partially update StudyPreferences : {}", studyPreferencesDTO);

        return studyPreferencesRepository
            .findById(studyPreferencesDTO.getId())
            .map(
                existingStudyPreferences -> {
                    studyPreferencesMapper.partialUpdate(existingStudyPreferences, studyPreferencesDTO);

                    return existingStudyPreferences;
                }
            )
            .map(studyPreferencesRepository::save)
            .map(studyPreferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudyPreferencesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudyPreferences");
        return studyPreferencesRepository.findAll(pageable).map(studyPreferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudyPreferencesDTO> findOne(Long id) {
        log.debug("Request to get StudyPreferences : {}", id);
        return studyPreferencesRepository.findById(id).map(studyPreferencesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudyPreferences : {}", id);
        studyPreferencesRepository.deleteById(id);
    }
}
