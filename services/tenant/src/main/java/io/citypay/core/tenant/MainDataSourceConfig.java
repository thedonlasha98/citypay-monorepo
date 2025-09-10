//package io.citypay.core.tenant;
//
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableConfigurationProperties(TenantManagementProperties.class)
//@EnableJpaRepositories(
//    basePackages = "io.citypay.core.tenant",
//    entityManagerFactoryRef = "mainEntityManagerFactory",
//    transactionManagerRef = "mainTx"
//)
//@RequiredArgsConstructor
//public class MainDataSourceConfig {
//
//    private final TenantManagementProperties properties;
//
//    @Bean
//    @Primary
//    public DataSource mainDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(properties.getMainJdbcUrl());
//        dataSource.setUsername(properties.getMainUsername());
//        dataSource.setPassword(properties.getMainPassword());
//        dataSource.setPoolName("main-pool");
//        dataSource.setMaximumPoolSize(10);
//        return dataSource;
//    }
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            DataSource mainDataSource) {
//        return builder
//                .dataSource(mainDataSource)
//                .packages("io.citypay.core.tenant")
//                .persistenceUnit("main")
//                .build();
//    }
//
//    @Bean
//    @Primary
//    public PlatformTransactionManager mainTx(
//            LocalContainerEntityManagerFactoryBean mainEntityManagerFactory) {
//        return new JpaTransactionManager(mainEntityManagerFactory.getObject());
//    }
//}
