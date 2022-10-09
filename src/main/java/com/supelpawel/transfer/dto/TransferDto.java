package com.supelpawel.transfer.dto;

import com.supelpawel.account.model.Account;
import com.supelpawel.transfer.model.Transfer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {

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

  public static TransferDto from(Transfer transfer) {
    TransferDto transferDto = new TransferDto();

    transferDto.setId(transfer.getId());
    transferDto.setFromAccount(transfer.getFromAccount());
    transferDto.setToAccount(transfer.getToAccount());
    transferDto.setOriginalAmount(transfer.getOriginalAmount());
    transferDto.setFinalAmount(transfer.getFinalAmount());
    transferDto.setTransferDate(transfer.getTransferDate());
    return transferDto;
  }

  @Override
  public String toString() {
    return "TransferDto{" +
        "id=" + id +
        ", originalAmount=" + originalAmount +
        ", finalAmount=" + finalAmount +
        ", transferDate=" + transferDate +
        '}';
  }
}