package se.callista.blog.tenant_management.annotation;

import com.github.database.rider.junit5.api.DBRider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootIntegrationTest
@DBRider
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringBootDbIntegrationTest {
}
