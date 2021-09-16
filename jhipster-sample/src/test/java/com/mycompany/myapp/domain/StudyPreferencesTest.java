package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudyPreferencesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyPreferences.class);
        StudyPreferences studyPreferences1 = new StudyPreferences();
        studyPreferences1.setId(1L);
        StudyPreferences studyPreferences2 = new StudyPreferences();
        studyPreferences2.setId(studyPreferences1.getId());
        assertThat(studyPreferences1).isEqualTo(studyPreferences2);
        studyPreferences2.setId(2L);
        assertThat(studyPreferences1).isNotEqualTo(studyPreferences2);
        studyPreferences1.setId(null);
        assertThat(studyPreferences1).isNotEqualTo(studyPreferences2);
    }
}
