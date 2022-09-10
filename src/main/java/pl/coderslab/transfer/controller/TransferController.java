package pl.coderslab.transfer.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.transfer.model.Transfer;
import pl.coderslab.transfer.service.TransferService;
import pl.coderslab.user.model.CurrentUser;

@Controller
@AllArgsConstructor
public class TransferController {

  private final TransferService transferService;

  @GetMapping("/transfer/make")
  String showMakeTransferForm(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
    return transferService.showMakeTransferForm(model, currentUser);
  }

  @PostMapping("/transfer/make")
  String processMakeTransferForm(@Valid Transfer transfer, BindingResult result) {
    return transferService.processMakeTransferForm(transfer, result);
  }

  @GetMapping("/transfer/history")
  String showTransferHistory(Model model) {
    return transferService.showTransferHistory(model);
  }
}
