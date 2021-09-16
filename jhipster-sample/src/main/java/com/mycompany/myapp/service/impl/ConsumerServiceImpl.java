package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Consumer;
import com.mycompany.myapp.repository.ConsumerRepository;
import com.mycompany.myapp.service.ConsumerService;
import com.mycompany.myapp.service.dto.ConsumerDTO;
import com.mycompany.myapp.service.mapper.ConsumerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Consumer}.
 */
@Service
@Transactional
public class ConsumerServiceImpl implements ConsumerService {

    private final Logger log = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    private final ConsumerRepository consumerRepository;

    private final ConsumerMapper consumerMapper;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository, ConsumerMapper consumerMapper) {
        this.consumerRepository = consumerRepository;
        this.consumerMapper = consumerMapper;
    }

    @Override
    public ConsumerDTO save(ConsumerDTO consumerDTO) {
        log.debug("Request to save Consumer : {}", consumerDTO);
        Consumer consumer = consumerMapper.toEntity(consumerDTO);
        consumer = consumerRepository.save(consumer);
        return consumerMapper.toDto(consumer);
    }

    @Override
    public Optional<ConsumerDTO> partialUpdate(ConsumerDTO consumerDTO) {
        log.debug("Request to partially update Consumer : {}", consumerDTO);

        return consumerRepository
            .findById(consumerDTO.getId())
            .map(
                existingConsumer -> {
                    consumerMapper.partialUpdate(existingConsumer, consumerDTO);

                    return existingConsumer;
                }
            )
            .map(consumerRepository::save)
            .map(consumerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsumerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consumers");
        return consumerRepository.findAll(pageable).map(consumerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumerDTO> findOne(Long id) {
        log.debug("Request to get Consumer : {}", id);
        return consumerRepository.findById(id).map(consumerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consumer : {}", id);
        consumerRepository.deleteById(id);
    }
}
