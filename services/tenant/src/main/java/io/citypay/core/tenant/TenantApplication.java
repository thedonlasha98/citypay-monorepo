package io.citypay.core.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "io.citypay.core")
@EnableConfigurationProperties(TenantManagementProperties.class)
public class TenantApplication {

  public static void main(String[] args) {
    SpringApplication.run(TenantApplication.class, args);
  }
}
