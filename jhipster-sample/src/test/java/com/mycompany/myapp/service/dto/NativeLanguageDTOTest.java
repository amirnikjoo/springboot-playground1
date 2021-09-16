package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NativeLanguageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NativeLanguageDTO.class);
        NativeLanguageDTO nativeLanguageDTO1 = new NativeLanguageDTO();
        nativeLanguageDTO1.setId(1L);
        NativeLanguageDTO nativeLanguageDTO2 = new NativeLanguageDTO();
        assertThat(nativeLanguageDTO1).isNotEqualTo(nativeLanguageDTO2);
        nativeLanguageDTO2.setId(nativeLanguageDTO1.getId());
        assertThat(nativeLanguageDTO1).isEqualTo(nativeLanguageDTO2);
        nativeLanguageDTO2.setId(2L);
        assertThat(nativeLanguageDTO1).isNotEqualTo(nativeLanguageDTO2);
        nativeLanguageDTO1.setId(null);
        assertThat(nativeLanguageDTO1).isNotEqualTo(nativeLanguageDTO2);
    }
}
