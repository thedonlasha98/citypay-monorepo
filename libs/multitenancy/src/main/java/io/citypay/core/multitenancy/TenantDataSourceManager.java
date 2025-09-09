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
  private final TenantRepository repo;
  private final TenantFlywayService flyway;
  private final Map<String, HikariDataSource> cache = new ConcurrentHashMap<>();

  public TenantDataSourceManager(TenantRepository repo, TenantFlywayService flyway) {
    this.repo = repo;
    this.flyway = flyway;
  }

  public DataSource getOrCreate(String tenantId) {
    return cache.computeIfAbsent(
        tenantId,
        id -> {
          Tenant t =
              repo.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Unknown tenant: " + id));
          if (!t.isActive()) throw new IllegalStateException("Tenant inactive: " + id);

          HikariDataSource ds = new HikariDataSource();
          ds.setJdbcUrl(t.getJdbcUrl());
          ds.setUsername(t.getUsername());
          ds.setPassword(t.getPassword());
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
