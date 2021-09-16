package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.NativeLanguage;
import com.mycompany.myapp.repository.NativeLanguageRepository;
import com.mycompany.myapp.service.NativeLanguageService;
import com.mycompany.myapp.service.dto.NativeLanguageDTO;
import com.mycompany.myapp.service.mapper.NativeLanguageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NativeLanguage}.
 */
@Service
@Transactional
public class NativeLanguageServiceImpl implements NativeLanguageService {

    private final Logger log = LoggerFactory.getLogger(NativeLanguageServiceImpl.class);

    private final NativeLanguageRepository nativeLanguageRepository;

    private final NativeLanguageMapper nativeLanguageMapper;

    public NativeLanguageServiceImpl(NativeLanguageRepository nativeLanguageRepository, NativeLanguageMapper nativeLanguageMapper) {
        this.nativeLanguageRepository = nativeLanguageRepository;
        this.nativeLanguageMapper = nativeLanguageMapper;
    }

    @Override
    public NativeLanguageDTO save(NativeLanguageDTO nativeLanguageDTO) {
        log.debug("Request to save NativeLanguage : {}", nativeLanguageDTO);
        NativeLanguage nativeLanguage = nativeLanguageMapper.toEntity(nativeLanguageDTO);
        nativeLanguage = nativeLanguageRepository.save(nativeLanguage);
        return nativeLanguageMapper.toDto(nativeLanguage);
    }

    @Override
    public Optional<NativeLanguageDTO> partialUpdate(NativeLanguageDTO nativeLanguageDTO) {
        log.debug("Request to partially update NativeLanguage : {}", nativeLanguageDTO);

        return nativeLanguageRepository
            .findById(nativeLanguageDTO.getId())
            .map(
                existingNativeLanguage -> {
                    nativeLanguageMapper.partialUpdate(existingNativeLanguage, nativeLanguageDTO);

                    return existingNativeLanguage;
                }
            )
            .map(nativeLanguageRepository::save)
            .map(nativeLanguageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NativeLanguageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NativeLanguages");
        return nativeLanguageRepository.findAll(pageable).map(nativeLanguageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NativeLanguageDTO> findOne(Long id) {
        log.debug("Request to get NativeLanguage : {}", id);
        return nativeLanguageRepository.findById(id).map(nativeLanguageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NativeLanguage : {}", id);
        nativeLanguageRepository.deleteById(id);
    }
}
