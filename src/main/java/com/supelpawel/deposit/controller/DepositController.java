package com.supelpawel.deposit.controller;

import com.supelpawel.deposit.model.Deposit;
import com.supelpawel.deposit.service.DepositService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class DepositController {

  private final DepositService depositService;

  @GetMapping("/deposit/{id}")
  String showMakeDepositForm(Model model, @PathVariable(name = "id") String accountId) {
    return depositService.showMakeDepositForm(model, accountId);
  }

  @PostMapping("/deposit/{id}")
  String processMakeDepositForm(@Valid Deposit deposit, BindingResult result,
      @PathVariable(name = "id") String accountId) {
    return depositService.processMakeDepositForm(deposit, result, accountId);
  }
}