package pl.coderslab.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.account.model.Account;
import pl.coderslab.account.service.AccountService;
import pl.coderslab.user.model.User;
import pl.coderslab.user.model.CurrentUser;
import pl.coderslab.exchange.service.ExchangeRateService;
import pl.coderslab.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final ExchangeRateService exchangeRateService;

    @ModelAttribute("currencyList")
    List<String> currencyList() {

        List<String> currencyList = exchangeRateService.getCurrencies();

        return currencyList;
    }

    @GetMapping("/account/add")
    String showAddAccountForm(Model model) {

        Account account = new Account();

        model.addAttribute("account", account);

        return "account/add";
    }

    @PostMapping("/account/add")
    String processAddAccountForm(@Valid Account account, BindingResult result,
                                 @AuthenticationPrincipal CurrentUser currentUser) {

        if (result.hasErrors()) {
            return "account/add";
        }

        String accountNumber = accountService.getNewAccountNumber();
        account.setAccountNumber(accountNumber);

        User user = currentUser.getUser();
        user.getAccounts().add(account);
        account.setUser(user);

        accountService.save(account);

        userService.update(user);

        return "redirect:/account/list";
    }

    @GetMapping("/account/list")
    String showAccountList(Model model, @AuthenticationPrincipal CurrentUser currentUser) {

        User user = currentUser.getUser();

        List<Account> accounts = accountService.findByUserId(user.getId());

        model.addAttribute("accounts", accounts);

        return "account/list";
    }
}
