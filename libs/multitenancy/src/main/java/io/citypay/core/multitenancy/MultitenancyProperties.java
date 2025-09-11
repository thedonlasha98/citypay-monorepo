package io.citypay.core.multitenancy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "multitenancy")
public class MultitenancyProperties {
  private String resolver = "HEADER";
  private String header = "X-Tenant-Id";

  //  private String mainJdbcUrl;
  //  private String mainUsername;
  //  private String mainPassword;
  //
  //  private String adminJdbcUrl;
  //  private String adminUsername;
  //  private String adminPassword;

  private String tenantFlywayLocations = "classpath:db/tenant/migration";
}
