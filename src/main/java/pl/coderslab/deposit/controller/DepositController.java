package pl.coderslab.deposit.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.account.model.Account;
import pl.coderslab.account.service.AccountService;
import pl.coderslab.deposit.service.DepositService;
import pl.coderslab.deposit.model.Deposit;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DepositController {

    private final DepositService depositService;
    private final AccountService accountService;

    @GetMapping("/deposit/{id}")
    String showMakeDepositForm(Model model, @PathVariable (name = "id") String accountId) {

        Deposit deposit = new Deposit();

        model.addAttribute("deposit", deposit);

        return "deposit/make";
    }

    @PostMapping("/deposit/{id}")
    String processMakeDepositForm(@Valid Deposit deposit, BindingResult result,
                                  @PathVariable (name = "id") String accountId) {

        Optional<Account> optAccount = accountService.findById(Long.valueOf(accountId));

        if (result.hasErrors() || !optAccount.isPresent()) {

            return "deposit/make";
        }

        Account account = optAccount.get();
        deposit.setAccount(account);

        BigDecimal currentAmount = account.getBalance();
        BigDecimal depositAmount = deposit.getAmount();
        BigDecimal resultBalance = currentAmount.add(depositAmount);

        account.setBalance(resultBalance);

        LocalDateTime depositDate = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
        deposit.setDepositDate(depositDate);

        depositService.save(deposit);
        accountService.update(account);

        return "redirect:/account/list";
    }
}
