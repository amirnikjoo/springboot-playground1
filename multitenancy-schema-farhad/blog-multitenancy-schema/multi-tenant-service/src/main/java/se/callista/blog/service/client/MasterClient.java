package se.callista.blog.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.callista.blog.service.domain.dto.TenantDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MasterClient {
    private final String tenantManagementUri;

    public MasterClient(@Value("${tenantManagement.uri}") String tenantManagementUri) {
        this.tenantManagementUri = tenantManagementUri;
    }

    public List<TenantDto> getTenants() {

        try {
            String url = tenantManagementUri.trim() + "/tenant";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TenantDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, TenantDto[].class);

            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Could not get tenants and catch exception {} ", e.getMessage());
        }

        return new ArrayList<>();
    }

    public TenantDto getTenantById(String tenantId) {

        log.info("getTenants Called. tenantId: {}", tenantId);
        try {
            String url = tenantManagementUri.trim() + "/tenant/" + tenantId;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TenantDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, TenantDto.class);

            return response.getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Could not get tenants and catch exception {} ", e.getMessage());
        }

        return null;
    }
}
