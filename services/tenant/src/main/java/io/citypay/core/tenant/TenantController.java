package io.citypay.core.tenant;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
  private final TenantProvisionService provision;

  @GetMapping("/{tenantId}")
  public Tenant get(@PathVariable String tenantId) {
      return provision.getTenant(tenantId);
  }

  @PostMapping("/{tenantId}")
  public ResponseEntity<?> create(@PathVariable String tenantId) {
    provision.createTenant(tenantId);
    return ResponseEntity.ok(Map.of("tenantId", tenantId, "status", "created"));
  }
}
