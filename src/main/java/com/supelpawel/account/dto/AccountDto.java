package com.supelpawel.account.dto;

import com.supelpawel.account.model.Account;
import com.supelpawel.deposit.model.Deposit;
import com.supelpawel.user.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

  private Long id;
  private String accountNumber;
  @NotNull
  private String currency;
  @NotNull
  @Min(0)
  private BigDecimal balance;
  @ManyToOne
  private User user;
  @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
  private List<Deposit> deposits = new ArrayList<>();

  public static AccountDto from(Account account) {
    AccountDto accountDto = new AccountDto();

    accountDto.setId(account.getId());
    accountDto.setAccountNumber(account.getAccountNumber());
    accountDto.setCurrency(account.getCurrency());
    accountDto.setBalance(account.getBalance());
    accountDto.setUser(account.getUser());
    accountDto.setDeposits(account.getDeposits());
    return accountDto;
  }

  @Override
  public String toString() {
    return "AccountDto{" +
        "id=" + id +
        ", accountNumber='" + accountNumber + '\'' +
        ", currency='" + currency + '\'' +
        ", balance=" + balance +
        '}';
  }
}