package io.citypay.core.balance.model;

import java.math.BigDecimal;

import io.citypay.core.shared.enums.Asset;

public record BalanceRO(BigDecimal balance, Asset asset) {}
