package se.callista.blog.tenant_management.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Service;
import se.callista.blog.tenant_management.client.TenantClient;
import se.callista.blog.tenant_management.domain.entity.Tenant;
import se.callista.blog.tenant_management.domain.dto.TenantDto;
import se.callista.blog.tenant_management.repository.TenantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TenantManagementServiceImpl implements TenantManagementService {

    private final TenantRepository tenantRepository;
    private final TenantClient tenantClient;
    private final ModelMapper modelMapper;
    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public TenantManagementServiceImpl(TenantRepository tenantRepository, TenantClient tenantClient,
                                       ModelMapper modelMapper, JdbcTemplate jdbcTemplate) {
        this.tenantRepository = tenantRepository;
        this.tenantClient = tenantClient;
        this.modelMapper = modelMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String VALID_SCHEMA_NAME_REGEXP = "[A-Za-z0-9_]*";

    @Override
    public void createTenant(String tenantId, String schema) {

        // Verify schema string to prevent SQL injection
        if (!schema.matches(VALID_SCHEMA_NAME_REGEXP)) {
            throw new TenantCreationException("Invalid schema name: " + schema);
        }

        Tenant tenant = Tenant.builder()
                .tenantId(tenantId)
                .schema(schema)
                .build();
        tenantRepository.save(tenant);

        try {
            createSchema(schema);
        } catch (DataAccessException e) {
            throw new TenantCreationException("Error when creating schema: " + schema, e);
        }

        tenantClient.init(TenantDto.builder().tenantId(tenantId).schema(schema).build());

    }

    @Override
    public List<TenantDto> getAll() {
        Iterable<Tenant> all = tenantRepository.findAll();
        List<TenantDto> tenantDtoList = new ArrayList<>();

        all.forEach(tenant -> {
            tenantDtoList.add(modelMapper.map(tenant, TenantDto.class));
        });
        return tenantDtoList;
    }

    @Override
    public TenantDto findByTenantId(String tenantId) {
        return modelMapper.map(tenantRepository.findByTenantId(tenantId),TenantDto.class);
    }

    private void createSchema(String schema) {
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA " + schema));
    }
}
