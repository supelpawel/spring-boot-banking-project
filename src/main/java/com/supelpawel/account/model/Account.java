package com.supelpawel.account.model;

import com.supelpawel.deposit.model.Deposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.supelpawel.user.model.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Override
  public String toString() {
    return "Account{" +
        "accountNumber='" + accountNumber + '\'' +
        ", currency='" + currency + '\'' +
        ", balance=" + balance +
        '}';
  }
}
