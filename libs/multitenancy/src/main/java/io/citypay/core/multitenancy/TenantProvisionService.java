package io.citypay.core.multitenancy;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantProvisionService {

  private final MultitenancyProperties props;
  private final TenantRepository tenantRepo;
  private final TenantDataSourceManager dsManager;
  private final TenantFlywayService flywayService;

  @Transactional(transactionManager = "mainTx")
  public void createTenant(String tenantId) {
    try (var adminDs =
        DataSources.hikari(
            props.getAdminJdbcUrl(),
            props.getAdminUsername(),
            props.getAdminPassword(),
            "admin-ds")) {
      JdbcTemplate jdbc = new JdbcTemplate(adminDs);
      String dbName = "tenant_" + tenantId;
      jdbc.execute("CREATE DATABASE " + dbName);
    }

    String jdbcUrl = props.getMainJdbcUrl().replaceFirst("/[^?]+", "/" + "tenant_" + tenantId);

    Tenant t =
        Tenant.builder()
            .id(tenantId)
            .jdbcUrl(jdbcUrl)
            .username(props.getMainUsername())
            .password(props.getMainPassword())
            .active(true)
            .build();
    tenantRepo.save(t);

    DataSource tenantDs = dsManager.getOrCreate(tenantId);
    flywayService.migrateTenant(tenantDs);
    log.info("Tenant {} provisioned and migrated.", tenantId);
  }

  static class DataSources {
    static com.zaxxer.hikari.HikariDataSource hikari(
        String url, String user, String pass, String name) {
      var ds = new com.zaxxer.hikari.HikariDataSource();
      ds.setJdbcUrl(url);
      ds.setUsername(user);
      ds.setPassword(pass);
      ds.setPoolName(name);
      return ds;
    }
  }
}
