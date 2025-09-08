package io.citypay.core.balance.model;

import io.citypay.core.shared.enums.Asset;

import java.math.BigDecimal;

public record BalanceRO(
        BigDecimal balance,
        Asset asset

) {
}
