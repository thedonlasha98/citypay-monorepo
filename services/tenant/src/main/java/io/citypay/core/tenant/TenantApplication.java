package io.citypay.core.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.citypay.core")
public class TenantApplication {

  public static void main(String[] args) {
    SpringApplication.run(TenantApplication.class, args);
  }
}
