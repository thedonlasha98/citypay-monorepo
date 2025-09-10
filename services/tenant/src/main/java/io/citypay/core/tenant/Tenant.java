package io.citypay.core.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tenant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {
  @Id private String id;

  @Column(name = "jdbc_url")
  private String jdbcUrl;

  private String username;
  private String password;
  private boolean active = true;
}
