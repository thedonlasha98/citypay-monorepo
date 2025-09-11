package io.citypay.core.tenant;

import com.zaxxer.hikari.HikariDataSource;
import io.citypay.core.multitenancy.TenantDataSourceManager;
import io.citypay.core.multitenancy.TenantFlywayService;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantProvisionService {
  private final TenantManagementProperties props;
  private final TenantRepository tenantRepo; // Uses default Spring Boot datasource
  private final TenantDataSourceManager dsManager;
  private final TenantFlywayService flywayService;

  @Transactional // Uses default transaction manager
  public void createTenant(String tenantId) {
    // 1. Create the database using admin connection
    createTenantDatabase(tenantId);

    // 2. Save tenant metadata to main database (default datasource)
    Tenant tenant = saveTenantMetadata(tenantId);

    // 3. Run migrations on new tenant database
    migrateTenantDatabase(tenantId);

    log.info("Tenant {} provisioned successfully", tenantId);
  }

  private void createTenantDatabase(String tenantId) {
    try (HikariDataSource adminDs = createAdminDataSource()) {
      JdbcTemplate jdbc = new JdbcTemplate(adminDs);
      String dbName = "tenant_" + tenantId;

      // Create database
      jdbc.execute("CREATE DATABASE " + dbName);

      // Optionally create user and grant permissions
      if (props.getDefaultTenantUsername() != null) {
        String createUser =
            String.format(
                "CREATE USER %s WITH PASSWORD '%s'",
                props.getDefaultTenantUsername(), props.getDefaultTenantPassword());
        jdbc.execute(createUser);

        String grantPermissions =
            String.format(
                "GRANT ALL PRIVILEGES ON DATABASE %s TO %s",
                dbName, props.getDefaultTenantUsername());
        jdbc.execute(grantPermissions);
      }

      log.info("Created database: {}", dbName);
    }
  }

  private Tenant saveTenantMetadata(String tenantId) {
    String jdbcUrl = props.getTenantUrlTemplate().replace("{tenantId}", tenantId);

    Tenant tenant =
        Tenant.builder()
            .id(tenantId)
            .jdbcUrl(jdbcUrl)
            .username(props.getDefaultTenantUsername())
            .password(props.getDefaultTenantPassword())
            .active(true)
            .build();

    return tenantRepo.save(tenant); // Uses default datasource
  }

  private void migrateTenantDatabase(String tenantId) {
    DataSource tenantDs = dsManager.getOrCreate(tenantId);
    flywayService.migrateTenant(tenantDs);
    log.info("Migrated tenant database: {}", tenantId);
  }

  private HikariDataSource createAdminDataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(props.getAdminJdbcUrl());
    dataSource.setUsername(props.getAdminUsername());
    dataSource.setPassword(props.getAdminPassword());
    dataSource.setPoolName("admin-temp");
    dataSource.setMaximumPoolSize(2);
    return dataSource;
  }

  public Tenant getTenant(String tenantId) {
    return tenantRepo
        .findById(tenantId)
        .orElseThrow(() -> new IllegalArgumentException("Tenant not found: " + tenantId));
  }

  @Transactional
  public void deleteTenant(String tenantId) {
    // 1. Remove from registry (uses default datasource)
    tenantRepo.deleteById(tenantId);

    // 2. Evict from cache
    dsManager.evict(tenantId);

    // 3. Optionally drop database
    try (HikariDataSource adminDs = createAdminDataSource()) {
      JdbcTemplate jdbc = new JdbcTemplate(adminDs);
      String dbName = "tenant_" + tenantId;
      jdbc.execute("DROP DATABASE IF EXISTS " + dbName);
      log.info("Dropped database: {}", dbName);
    }
  }
}
