package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PersonalInfo;
import com.mycompany.myapp.repository.PersonalInfoRepository;
import com.mycompany.myapp.service.PersonalInfoService;
import com.mycompany.myapp.service.dto.PersonalInfoDTO;
import com.mycompany.myapp.service.mapper.PersonalInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalInfo}.
 */
@Service
@Transactional
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoServiceImpl.class);

    private final PersonalInfoRepository personalInfoRepository;

    private final PersonalInfoMapper personalInfoMapper;

    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository, PersonalInfoMapper personalInfoMapper) {
        this.personalInfoRepository = personalInfoRepository;
        this.personalInfoMapper = personalInfoMapper;
    }

    @Override
    public PersonalInfoDTO save(PersonalInfoDTO personalInfoDTO) {
        log.debug("Request to save PersonalInfo : {}", personalInfoDTO);
        PersonalInfo personalInfo = personalInfoMapper.toEntity(personalInfoDTO);
        personalInfo = personalInfoRepository.save(personalInfo);
        return personalInfoMapper.toDto(personalInfo);
    }

    @Override
    public Optional<PersonalInfoDTO> partialUpdate(PersonalInfoDTO personalInfoDTO) {
        log.debug("Request to partially update PersonalInfo : {}", personalInfoDTO);

        return personalInfoRepository
            .findById(personalInfoDTO.getId())
            .map(
                existingPersonalInfo -> {
                    personalInfoMapper.partialUpdate(existingPersonalInfo, personalInfoDTO);

                    return existingPersonalInfo;
                }
            )
            .map(personalInfoRepository::save)
            .map(personalInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonalInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalInfos");
        return personalInfoRepository.findAll(pageable).map(personalInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalInfoDTO> findOne(Long id) {
        log.debug("Request to get PersonalInfo : {}", id);
        return personalInfoRepository.findById(id).map(personalInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalInfo : {}", id);
        personalInfoRepository.deleteById(id);
    }
}
