package se.callista.blog.tenant_management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.callista.blog.tenant_management.domain.dto.TenantDto;

import javax.validation.Valid;

@FeignClient("tenant-service")
public interface TenantClient {

    @GetMapping("/init")
    void init(@Valid @RequestBody TenantDto tenantDto);
}
