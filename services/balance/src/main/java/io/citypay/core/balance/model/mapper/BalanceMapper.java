package io.citypay.core.balance.model.mapper;

import org.mapstruct.Mapper;

import io.citypay.core.balance.domain.Balance;
import io.citypay.core.balance.model.BalanceRO;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

  //    Balance toEntity(BalanceRO balanceRO);

  BalanceRO toDTO(Balance balance);
}
