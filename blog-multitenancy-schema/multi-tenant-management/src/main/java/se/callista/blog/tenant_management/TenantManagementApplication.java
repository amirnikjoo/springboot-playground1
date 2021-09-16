package se.callista.blog.tenant_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = { LiquibaseAutoConfiguration.class })
@EnableFeignClients
public class TenantManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TenantManagementApplication.class, args);
    }

}

