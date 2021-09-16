package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonalInfoMapperTest {

    private PersonalInfoMapper personalInfoMapper;

    @BeforeEach
    public void setUp() {
        personalInfoMapper = new PersonalInfoMapperImpl();
    }
}
