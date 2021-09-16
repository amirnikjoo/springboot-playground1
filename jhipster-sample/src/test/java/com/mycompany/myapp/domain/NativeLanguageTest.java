package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NativeLanguageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NativeLanguage.class);
        NativeLanguage nativeLanguage1 = new NativeLanguage();
        nativeLanguage1.setId(1L);
        NativeLanguage nativeLanguage2 = new NativeLanguage();
        nativeLanguage2.setId(nativeLanguage1.getId());
        assertThat(nativeLanguage1).isEqualTo(nativeLanguage2);
        nativeLanguage2.setId(2L);
        assertThat(nativeLanguage1).isNotEqualTo(nativeLanguage2);
        nativeLanguage1.setId(null);
        assertThat(nativeLanguage1).isNotEqualTo(nativeLanguage2);
    }
}
