package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.StudyPreferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudyPreferences} and its DTO {@link StudyPreferencesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudyPreferencesMapper extends EntityMapper<StudyPreferencesDTO, StudyPreferences> {}
