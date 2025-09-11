package io.citypay.core.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantDataSourceManager {
  private final TenantService tenantService;
  private final TenantFlywayService flyway;
  private final Map<String, HikariDataSource> cache = new ConcurrentHashMap<>();

  public TenantDataSourceManager(TenantService tenantService, TenantFlywayService flyway) {
    this.tenantService = tenantService;
    this.flyway = flyway;
  }

  public DataSource getOrCreate(String tenantId) {
    return cache.computeIfAbsent(
        tenantId,
        id -> {
          Tenant t = tenantService.getTenant(id);
          if (!t.active()) {
            throw new IllegalStateException("Tenant inactive: " + id);
          }

          HikariDataSource ds = new HikariDataSource();
          ds.setJdbcUrl(t.jdbcUrl());
          ds.setUsername(t.username());
          ds.setPassword(t.password());
          ds.setPoolName("tenant-" + id);
          ds.setMaximumPoolSize(10);
          log.info("Created DataSource for tenant {}", id);
          // run migrations on first use
          flyway.migrateTenant(ds);
          return ds;
        });
  }

  public void evict(String tenantId) {
    HikariDataSource ds = cache.remove(tenantId);
    if (ds != null) ds.close();
  }
}
