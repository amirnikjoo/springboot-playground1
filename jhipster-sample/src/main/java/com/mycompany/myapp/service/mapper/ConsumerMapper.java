package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ConsumerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consumer} and its DTO {@link ConsumerDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonalInfoMapper.class })
public interface ConsumerMapper extends EntityMapper<ConsumerDTO, Consumer> {
    @Mapping(target = "personalInfo", source = "personalInfo", qualifiedByName = "id")
    ConsumerDTO toDto(Consumer s);
}
