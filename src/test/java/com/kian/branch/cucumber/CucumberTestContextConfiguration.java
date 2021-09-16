package com.kian.branch.cucumber;

import com.kian.branch.BranchApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = BranchApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
