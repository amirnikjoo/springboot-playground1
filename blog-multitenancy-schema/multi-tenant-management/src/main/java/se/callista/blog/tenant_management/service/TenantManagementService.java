package se.callista.blog.tenant_management.service;

import se.callista.blog.tenant_management.domain.dto.TenantDto;

import java.util.List;

public interface TenantManagementService {
    
    void createTenant(String tenantId, String schema);
    List<TenantDto> getAll();
    TenantDto findByTenantId(String tenantId);
}