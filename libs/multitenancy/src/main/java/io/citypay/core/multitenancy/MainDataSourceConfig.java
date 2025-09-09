package io.citypay.core.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = TenantRepository.class,
    entityManagerFactoryRef = "mainEmf",
    transactionManagerRef = "mainTx")
public class MainDataSourceConfig {

  @Bean
  @Primary
  public DataSource mainDataSource(MultitenancyProperties props) {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl(props.getMainJdbcUrl());
    ds.setUsername(props.getMainUsername());
    ds.setPassword(props.getMainPassword());
    ds.setPoolName("main-ds");
    return ds;
  }

  @Bean
  public Flyway mainFlyway(DataSource mainDataSource, MultitenancyProperties props) {
    Flyway flyway =
        Flyway.configure()
            .dataSource(mainDataSource)
            .locations(props.getMainFlywayLocations())
            .baselineOnMigrate(true)
            .load();
    flyway.migrate();
    return flyway;
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean mainEmf(
      EntityManagerFactoryBuilder builder, DataSource mainDataSource) {
    return builder
        .dataSource(mainDataSource)
        .packages(Tenant.class.getPackageName())
        .persistenceUnit("main")
        .build();
  }

  @Bean
  @Primary
  public PlatformTransactionManager mainTx(
      @Qualifier("mainEmf") LocalContainerEntityManagerFactoryBean emf) {
    return new JpaTransactionManager(emf.getObject());
  }

  /** Make sure Hibernate doesn't try to touch schemas for the main EMF */
  @Bean
  HibernatePropertiesCustomizer mainCustomizer() {
    return (props) -> props.put("hibernate.hbm2ddl.auto", "none");
  }
}
