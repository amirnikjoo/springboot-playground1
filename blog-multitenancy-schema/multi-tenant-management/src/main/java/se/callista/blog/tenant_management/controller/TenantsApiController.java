package se.callista.blog.tenant_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.callista.blog.tenant_management.domain.dto.TenantDto;
import se.callista.blog.tenant_management.domain.entity.Tenant;
import se.callista.blog.tenant_management.service.TenantManagementService;

import java.util.List;

@Controller
@RequestMapping("/tenant")
public class TenantsApiController {

    @Autowired
    private TenantManagementService tenantManagementService;

    @PostMapping
    public ResponseEntity<Void> createTenant(@RequestParam String tenantId, @RequestParam String schema) {
        this.tenantManagementService.createTenant(tenantId, schema);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getAll() {
        return new ResponseEntity<>(tenantManagementService.getAll(),HttpStatus.OK);
    }
}
