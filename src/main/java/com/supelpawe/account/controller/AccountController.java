package com.supelpawe.account.controller;

import com.supelpawe.account.model.Account;
import com.supelpawe.user.model.CurrentUser;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.supelpawe.account.service.AccountService;

@Controller
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @ModelAttribute("currencyList")
  List<String> getCurrencyList() {
    return accountService.getCurrencyList();
  }

  @GetMapping("/account/add")
  String showAddAccountForm(Model model) {
    return accountService.showAddAccountForm(model);
  }

  @PostMapping("/account/add")
  String processAddAccountForm(@Valid Account account, BindingResult result,
      @AuthenticationPrincipal CurrentUser currentUser) {
    return accountService.processAddAccountForm(account, result, currentUser);
  }

  @GetMapping("/account/list")
  String showAccountList(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
    return accountService.showAccountList(model, currentUser);
  }
}
