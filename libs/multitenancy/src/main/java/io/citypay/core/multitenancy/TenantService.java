package io.citypay.core.multitenancy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class TenantService {

  @Value("${api.tenant.url}")
  private String tenantUrl;

  private final RestClient restClient = RestClient.create();

  public Tenant getTenant(String tenantId) {
    return restClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(tenantUrl + "/tenants/{tenantId}").build(tenantId))
        .retrieve()
        .body(Tenant.class);
  }
}
