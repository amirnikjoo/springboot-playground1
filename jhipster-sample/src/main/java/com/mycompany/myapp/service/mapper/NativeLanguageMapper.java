package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.NativeLanguageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NativeLanguage} and its DTO {@link NativeLanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NativeLanguageMapper extends EntityMapper<NativeLanguageDTO, NativeLanguage> {}
