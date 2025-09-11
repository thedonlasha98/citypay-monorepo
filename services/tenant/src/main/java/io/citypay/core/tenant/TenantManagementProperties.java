package io.citypay.core.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tenant-management")
public class TenantManagementProperties {
  // Main datasource for storing tenant metadata
  private String mainJdbcUrl;
  private String mainUsername;
  private String mainPassword;

  // Admin datasource for creating new tenant databases
  private String adminJdbcUrl;
  private String adminUsername;
  private String adminPassword;

  // Template for tenant database URLs
  private String tenantUrlTemplate = "jdbc:postgresql://localhost:5432/tenant_{tenantId}";

  // Default credentials for new tenant databases
  private String defaultTenantUsername;
  private String defaultTenantPassword;

  // Flyway settings
  private String mainFlywayLocations = "classpath:db/main/migration";
}
