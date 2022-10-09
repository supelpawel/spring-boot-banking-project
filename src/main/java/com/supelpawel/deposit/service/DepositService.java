package com.supelpawel.deposit.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.supelpawel.account.model.Account;
import com.supelpawel.account.service.AccountService;
import com.supelpawel.deposit.model.Deposit;
import com.supelpawel.deposit.repository.DepositRepository;

@Service
@AllArgsConstructor
public class DepositService {

  private final DepositRepository depositRepository;
  private final AccountService accountService;

  @Transactional
  public void save(Deposit deposit) {
    depositRepository.save(deposit);
  }

  public String showMakeDepositForm(Model model, String accountId) {
    Deposit deposit = new Deposit();

    model.addAttribute("deposit", deposit);
    return "deposit/make";
  }

  @Transactional
  public String processMakeDepositForm(Deposit deposit, BindingResult result, String accountId) {
    Optional<Account> optAccount = accountService.findById(Long.valueOf(accountId));

    if (result.hasErrors() || optAccount.isEmpty()) {
      return "deposit/make";
    }
    Account account = optAccount.get();
    LocalDateTime depositDate = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));

    deposit.setAccount(account);
    BigDecimal currentAmount = account.getBalance();
    BigDecimal depositAmount = deposit.getAmount();
    BigDecimal resultBalance = currentAmount.add(depositAmount);
    account.setBalance(resultBalance);
    deposit.setDepositDate(depositDate);
    save(deposit);
    accountService.update(account);
    return "redirect:/account/list";
  }
}