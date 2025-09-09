package io.citypay.core.multitenancy;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MultitenancyProperties.class)
public class SharedMultitenancyAutoConfiguration {}
