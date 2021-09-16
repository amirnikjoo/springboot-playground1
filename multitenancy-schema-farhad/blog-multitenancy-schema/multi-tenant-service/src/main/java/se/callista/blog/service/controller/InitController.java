package se.callista.blog.service.controller;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.callista.blog.service.domain.dto.TenantDto;
import se.callista.blog.service.config.SchemaBasedMultiTenantConnectionProvider;
import se.callista.blog.service.services.RunLiquibaseException;

import javax.sql.DataSource;
import javax.validation.Valid;

@RestController
public class InitController {

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;
    private final LiquibaseProperties liquibaseProperties;
    private final SchemaBasedMultiTenantConnectionProvider schemaBasedMultiTenantConnectionProvider;

    public InitController(ResourceLoader resourceLoader,
                          DataSource dataSource,
                          @Qualifier("tenantLiquibaseProperties")
                                  LiquibaseProperties liquibaseProperties, SchemaBasedMultiTenantConnectionProvider schemaBasedMultiTenantConnectionProvider) {

        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.liquibaseProperties = liquibaseProperties;
        this.schemaBasedMultiTenantConnectionProvider = schemaBasedMultiTenantConnectionProvider;
    }

    @PostMapping("/init")
    public void init(@Valid @RequestBody TenantDto tenantDto) {
        schemaBasedMultiTenantConnectionProvider.createCache();
        try {
            runLiquibase(dataSource, tenantDto.getSchema());
        } catch (LiquibaseException e) {
            throw new RunLiquibaseException("Error when run liquibase schema: ", e);
        }
    }

    private void runLiquibase(DataSource dataSource, String schema) throws LiquibaseException {
        SpringLiquibase liquibase = getSpringLiquibase(dataSource, schema);
        liquibase.afterPropertiesSet();
    }

    protected SpringLiquibase getSpringLiquibase(DataSource dataSource, String schema) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        liquibase.setLabels(liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        return liquibase;
    }
}
