package se.callista.blog.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.callista.blog.service.multi_tenancy.interceptor.TenantInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Autowired
    public WebConfiguration(TenantInterceptor tenantInterceptor) {
        System.out.println(">>>>>>> inside web config...");
        this.tenantInterceptor = tenantInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println(">>>>>>> inside web config (addInterceptors)...");
        registry.addWebRequestInterceptor(tenantInterceptor);
    }

}