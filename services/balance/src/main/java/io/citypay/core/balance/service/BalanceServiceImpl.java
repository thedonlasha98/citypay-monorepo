package io.citypay.core.balance.service;

import org.springframework.stereotype.Service;

import io.citypay.core.balance.model.BalanceRO;
import io.citypay.core.balance.model.mapper.BalanceMapper;
import io.citypay.core.balance.repository.BalanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

  private final BalanceMapper balanceMapper;
  private final BalanceRepository balanceRepository;

  @Override
  public BalanceRO getBalance(String address) {
    return balanceRepository.findByAddress(address).map(balanceMapper::toDTO).orElse(null);
  }
}
