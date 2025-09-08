package io.citypay.core.balance.repository;

import io.citypay.core.balance.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByAddress(String address);

}
