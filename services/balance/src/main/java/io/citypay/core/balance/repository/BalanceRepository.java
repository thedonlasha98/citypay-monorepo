package io.citypay.core.balance.repository;

import io.citypay.core.balance.domain.Balance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

  Optional<Balance> findByAddress(String address);
}
