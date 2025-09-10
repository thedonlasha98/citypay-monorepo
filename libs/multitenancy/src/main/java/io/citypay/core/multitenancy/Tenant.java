package io.citypay.core.multitenancy;

public record Tenant(
        String id,
        String jdbcUrl,
        String username,
        String password,
        Boolean active
) {
}
