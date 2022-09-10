package pl.coderslab.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.account.model.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @ManyToOne
  private Account fromAccount;
  @NotNull
  @ManyToOne
  private Account toAccount;
  @NotNull
  @Min(1)
  private BigDecimal originalAmount;
  private BigDecimal finalAmount;
  private LocalDateTime transferDate;
}
