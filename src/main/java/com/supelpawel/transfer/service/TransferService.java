package com.supelpawel.transfer.service;

import com.supelpawel.account.model.Account;
import com.supelpawel.account.service.AccountService;
import com.supelpawel.exchange.service.ExchangeRateService;
import com.supelpawel.transfer.dto.TransferDto;
import com.supelpawel.transfer.model.Transfer;
import com.supelpawel.transfer.repository.TransferRepository;
import com.supelpawel.user.model.CurrentUser;
import com.supelpawel.user.model.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class TransferService {

  public static final int SCALE = 5;
  private final TransferRepository transferRepository;
  private final ExchangeRateService exchangeRateService;
  private final AccountService accountService;

  public String showMakeTransferForm(Model model, CurrentUser currentUser) {
    Transfer transfer = new Transfer();
    User user = currentUser.getUser();
    List<Account> accountList = user.getAccounts();

    model.addAttribute("transfer", transfer);
    model.addAttribute("accounts", accountList);
    return "transfer/make";
  }

  @Transactional
  public String processMakeTransferForm(Model model, CurrentUser currentUser, Transfer transfer,
      BindingResult result) {
    if (result.hasErrors()) {
      User user = currentUser.getUser();
      List<Account> accountList = user.getAccounts();

      model.addAttribute("accounts", accountList);
      return "transfer/make";
    }
    BigDecimal transferOriginalAmount = transfer.getOriginalAmount();
    BigDecimal currentBalance = transfer.getFromAccount().getBalance();

    if (transferOriginalAmount.compareTo(currentBalance) > 0) {
      return "transfer/warning";
    }
    Account fromAccount = transfer.getFromAccount();
    Account toAccount = transfer.getToAccount();
    String fromCurrency = fromAccount.getCurrency();
    String toCurrency = toAccount.getCurrency();
    BigDecimal originalAmount = transfer.getOriginalAmount();
    BigDecimal finalAmount = calculateFinalAmount(originalAmount, fromCurrency, toCurrency);
    LocalDateTime transferDate = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));

    transfer.setFinalAmount(finalAmount);
    save(transfer);
    fromAccount.setBalance(fromAccount.getBalance().subtract(originalAmount));
    toAccount.setBalance(toAccount.getBalance().add(finalAmount));
    transfer.setTransferDate(transferDate);
    accountService.update(fromAccount);
    accountService.update(toAccount);
    return "redirect:/account/list";
  }

  public String showTransferHistory(Model model) {
    List<Transfer> transferList = findAll();
    List<TransferDto> transferDtoList = transferList.stream()
        .map(TransferDto::from).toList();

    model.addAttribute("transfers", transferDtoList);
    return "transfer/history";
  }

  @Transactional
  public BigDecimal calculateFinalAmount(BigDecimal originalAmount, String fromCurrency,
      String toCurrency) {
    BigDecimal finalAmount;
    BigDecimal temporaryAmount;
    BigDecimal fromCurrencyExchangeRate;
    BigDecimal toCurrencyExchangeRate;
    BigDecimal temporaryExchangeRate;

    fromCurrencyExchangeRate = exchangeRateService.getCurrentExchangeRate(fromCurrency);
    toCurrencyExchangeRate = exchangeRateService.getCurrentExchangeRate(toCurrency);
    temporaryAmount = originalAmount.multiply(fromCurrencyExchangeRate);
    temporaryExchangeRate = fromCurrencyExchangeRate.multiply(temporaryAmount);

    if (temporaryExchangeRate.compareTo(toCurrencyExchangeRate) > 0) {
      finalAmount = temporaryAmount.divide(toCurrencyExchangeRate, SCALE, RoundingMode.HALF_UP);

    } else {

      finalAmount = temporaryAmount.multiply(toCurrencyExchangeRate);
    }
    return finalAmount;
  }

  @Transactional
  public void save(Transfer transfer) {
    transferRepository.save(transfer);
  }

  public List<Transfer> findAll() {
    return transferRepository.findAll();
  }
}