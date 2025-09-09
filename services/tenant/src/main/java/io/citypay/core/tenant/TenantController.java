package io.citypay.core.tenant;

import io.citypay.core.multitenancy.TenantProvisionService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
  private final TenantProvisionService provision;

  @PostMapping("/{tenantId}")
  public ResponseEntity<?> create(@PathVariable String tenantId) {
    provision.createTenant(tenantId);
    return ResponseEntity.ok(Map.of("tenantId", tenantId, "status", "created"));
  }
}
