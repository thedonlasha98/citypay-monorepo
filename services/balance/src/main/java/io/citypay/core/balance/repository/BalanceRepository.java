package io.citypay.core.balance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.citypay.core.balance.domain.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

  Optional<Balance> findByAddress(String address);
}
