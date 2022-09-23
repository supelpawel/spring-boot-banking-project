package com.supelpawel.transfer.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.supelpawel.transfer.model.Transfer;
import com.supelpawel.transfer.service.TransferService;
import com.supelpawel.user.model.CurrentUser;

@Controller
@AllArgsConstructor
public class TransferController {

  private final TransferService transferService;

  @GetMapping("/transfer/make")
  String showMakeTransferForm(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
    return transferService.showMakeTransferForm(model, currentUser);
  }

  @PostMapping("/transfer/make")
  String processMakeTransferForm(Model model, @AuthenticationPrincipal CurrentUser currentUser,
      @Valid Transfer transfer, BindingResult result) {
    return transferService.processMakeTransferForm(model, currentUser, transfer, result);
  }

  @GetMapping("/transfer/history")
  String showTransferHistory(Model model) {
    return transferService.showTransferHistory(model);
  }
}
