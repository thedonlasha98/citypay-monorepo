package io.citypay.core.multitenancy;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

@Service
public class TenantFlywayService {
  private final MultitenancyProperties props;

  public TenantFlywayService(MultitenancyProperties props) {
    this.props = props;
  }

  public void migrateTenant(DataSource tenantDs) {
    Flyway flyway =
        Flyway.configure()
            .dataSource(tenantDs)
            .locations(props.getTenantFlywayLocations())
            .baselineOnMigrate(true)
            .load();
    flyway.migrate();
  }
}
