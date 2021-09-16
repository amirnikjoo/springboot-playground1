package se.callista.blog.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import se.callista.blog.service.domain.dto.TenantDto;

import java.util.List;

@FeignClient("tenant-management")
public interface MasterClient {
    @GetMapping("/tenant")
    List<TenantDto> getAll();
}
