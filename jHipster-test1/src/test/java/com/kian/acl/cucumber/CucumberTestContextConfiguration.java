package com.kian.acl.cucumber;

import com.kian.acl.AclApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = AclApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
