package com.kian.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class TestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);

        String version = "1.1.0";
        log.info("project starts... {}", version);
    }

    @Override
    public void run(String... args) throws Exception {
        log.warn("this is a test runner");
    }
}
