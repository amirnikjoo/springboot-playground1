package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudyPreferencesMapperTest {

    private StudyPreferencesMapper studyPreferencesMapper;

    @BeforeEach
    public void setUp() {
        studyPreferencesMapper = new StudyPreferencesMapperImpl();
    }
}
