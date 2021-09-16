package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudyPreferencesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyPreferencesDTO.class);
        StudyPreferencesDTO studyPreferencesDTO1 = new StudyPreferencesDTO();
        studyPreferencesDTO1.setId(1L);
        StudyPreferencesDTO studyPreferencesDTO2 = new StudyPreferencesDTO();
        assertThat(studyPreferencesDTO1).isNotEqualTo(studyPreferencesDTO2);
        studyPreferencesDTO2.setId(studyPreferencesDTO1.getId());
        assertThat(studyPreferencesDTO1).isEqualTo(studyPreferencesDTO2);
        studyPreferencesDTO2.setId(2L);
        assertThat(studyPreferencesDTO1).isNotEqualTo(studyPreferencesDTO2);
        studyPreferencesDTO1.setId(null);
        assertThat(studyPreferencesDTO1).isNotEqualTo(studyPreferencesDTO2);
    }
}
