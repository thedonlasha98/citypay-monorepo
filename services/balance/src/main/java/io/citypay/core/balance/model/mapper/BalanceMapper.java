package io.citypay.core.balance.model.mapper;

import io.citypay.core.balance.domain.Balance;
import io.citypay.core.balance.model.BalanceRO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

//    Balance toEntity(BalanceRO balanceRO);

    BalanceRO toDTO(Balance balance);
}
