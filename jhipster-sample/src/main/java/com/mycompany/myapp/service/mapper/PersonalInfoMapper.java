package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PersonalInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalInfo} and its DTO {@link PersonalInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonalInfoMapper extends EntityMapper<PersonalInfoDTO, PersonalInfo> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalInfoDTO toDtoId(PersonalInfo personalInfo);
}
