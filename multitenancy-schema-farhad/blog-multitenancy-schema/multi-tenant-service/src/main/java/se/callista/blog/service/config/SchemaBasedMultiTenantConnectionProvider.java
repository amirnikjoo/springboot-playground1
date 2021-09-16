package se.callista.blog.service.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.callista.blog.service.client.MasterClient;
import se.callista.blog.service.domain.dto.TenantDto;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final String masterSchema;
    private final transient DataSource datasource;
    private final MasterClient masterClient;

    private Map<String, String> schemaMap = new TreeMap<>();

    @PostConstruct
    public void createCache() {
        List<TenantDto> tenants = masterClient.getTenants();
        tenants.forEach(tenantDto -> schemaMap.put(tenantDto.getTenantId(), tenantDto.getSchema()));
    }

    @Autowired
    public SchemaBasedMultiTenantConnectionProvider(
            @Value("${multitenancy.master.schema:#{null}}")
                    String masterSchema,
            DataSource datasource,
            MasterClient masterClient) {
        this.masterSchema = masterSchema;
        this.datasource = datasource;
        this.masterClient = masterClient;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        log.info("Get connection for tenant {}", tenantIdentifier);
        String tenantSchema = schemaMap.get(tenantIdentifier);
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantSchema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        log.info("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(masterSchema);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType) ) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
}
