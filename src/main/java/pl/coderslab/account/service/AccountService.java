package pl.coderslab.account.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.coderslab.account.model.Account;
import pl.coderslab.account.repository.AccountRepository;
import pl.coderslab.exchange.service.ExchangeRateService;
import pl.coderslab.user.model.CurrentUser;
import pl.coderslab.user.model.User;
import pl.coderslab.user.service.UserService;

@Service
@AllArgsConstructor
public class AccountService {

  public static final int ACCOUNT_NUMBER_LENGTH = 26;
  private final AccountRepository accountRepository;
  private final ExchangeRateService exchangeRateService;
  private final UserService userService;

  public String getNewAccountNumber() {
    Random r = new Random();
    StringBuilder accountNumber = new StringBuilder();

    for (int i = 1; i <= ACCOUNT_NUMBER_LENGTH; i++) {
      accountNumber.append(r.nextInt(10));
    }
    return accountNumber.toString();
  }

  @Transactional
  public void save(Account account) {
    accountRepository.save(account);
  }

  public Optional<Account> findById(Long id) {
    return accountRepository.findById(id);
  }

  public List<Account> findByUserId(long id) {
    return accountRepository.findByUserId(id);
  }

  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  public String showAddAccountForm(Model model) {
    Account account = new Account();

    model.addAttribute("account", account);
    return "account/add";
  }

  @Transactional
  public String processAddAccountForm(Account account, BindingResult result,
      CurrentUser currentUser) {
    if (result.hasErrors()) {
      return "account/add";
    }
    String accountNumber = getNewAccountNumber();
    User user = currentUser.getUser();

    account.setAccountNumber(accountNumber);
    user.getAccounts().add(account);
    account.setUser(user);
    save(account);
    userService.update(user);
    return "redirect:/account/list";
  }

  public String showAccountList(Model model, CurrentUser currentUser) {
    User user = currentUser.getUser();
    List<Account> accounts = findByUserId(user.getId());

    model.addAttribute("accounts", accounts);
    return "account/list";
  }

  public List<String> getCurrencyList() {
    return exchangeRateService.getCurrencies();
  }

  @Transactional
  public void update(Account account) {
    accountRepository.save(account);
  }
}

