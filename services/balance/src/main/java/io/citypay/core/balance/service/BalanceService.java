package io.citypay.core.balance.service;

import io.citypay.core.balance.model.BalanceRO;

public interface BalanceService {
  BalanceRO getBalance(String address);
}
