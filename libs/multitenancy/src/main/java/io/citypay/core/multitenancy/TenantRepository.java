package io.citypay.core.multitenancy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {}
