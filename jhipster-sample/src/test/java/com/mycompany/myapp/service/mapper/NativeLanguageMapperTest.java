package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NativeLanguageMapperTest {

    private NativeLanguageMapper nativeLanguageMapper;

    @BeforeEach
    public void setUp() {
        nativeLanguageMapper = new NativeLanguageMapperImpl();
    }
}
