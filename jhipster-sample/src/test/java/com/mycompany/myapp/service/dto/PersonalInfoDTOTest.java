package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonalInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalInfoDTO.class);
        PersonalInfoDTO personalInfoDTO1 = new PersonalInfoDTO();
        personalInfoDTO1.setId(1L);
        PersonalInfoDTO personalInfoDTO2 = new PersonalInfoDTO();
        assertThat(personalInfoDTO1).isNotEqualTo(personalInfoDTO2);
        personalInfoDTO2.setId(personalInfoDTO1.getId());
        assertThat(personalInfoDTO1).isEqualTo(personalInfoDTO2);
        personalInfoDTO2.setId(2L);
        assertThat(personalInfoDTO1).isNotEqualTo(personalInfoDTO2);
        personalInfoDTO1.setId(null);
        assertThat(personalInfoDTO1).isNotEqualTo(personalInfoDTO2);
    }
}
