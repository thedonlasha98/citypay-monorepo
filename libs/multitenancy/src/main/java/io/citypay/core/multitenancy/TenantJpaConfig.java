package io.citypay.core.multitenancy;

import javax.sql.DataSource;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = {"io.citypay.core.multitenancy"},
    entityManagerFactoryRef = "tenantEmf",
    transactionManagerRef = "tenantTx")
public class TenantJpaConfig {

  @Bean
  public DataSource tenantRoutingDataSource(TenantDataSourceManager manager) {
    return new DynamicLookupDataSource(manager);
  }

  static class DynamicLookupDataSource extends TenantRoutingDataSource {
    private final TenantDataSourceManager manager;

    DynamicLookupDataSource(TenantDataSourceManager manager) {
      this.manager = manager;
      setLenientFallback(true);
      afterPropertiesSet();
    }

    @Override
    protected DataSource determineTargetDataSource() {
      String tenantId = TenantContext.getTenantId();
      if (tenantId == null) {
          return super.determineTargetDataSource();
      }
      return (DataSource) manager.getOrCreate(tenantId);
    }
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean tenantEmf(
      EntityManagerFactoryBuilder builder, DataSource tenantRoutingDataSource) {
    return builder
        .dataSource(tenantRoutingDataSource)
        .packages("io.citypay.core") // include all domain packages
        .persistenceUnit("tenant")
        .build();
  }

  @Bean
  public PlatformTransactionManager tenantTx(LocalContainerEntityManagerFactoryBean tenantEmf) {
    return new JpaTransactionManager(tenantEmf.getObject());
  }
}
