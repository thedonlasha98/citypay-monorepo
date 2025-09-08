package io.citypay.core.balance.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import io.citypay.core.shared.enums.Asset;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String address;

  @Enumerated(value = EnumType.STRING)
  private Asset asset;

  private BigDecimal balance;
}
