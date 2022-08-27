package pl.coderslab.transfer.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.account.data.Account;
import pl.coderslab.transfer.service.TransferService;
import pl.coderslab.transfer.data.Transfer;
import pl.coderslab.user.data.User;
import pl.coderslab.user.data.CurrentUser;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/transfer/make")
    String showMakeTransferForm(Model model, @AuthenticationPrincipal CurrentUser currentUser) {

        Transfer transfer = new Transfer();
        User user = currentUser.getUser();
        List<Account> accounts = user.getAccounts();

        model.addAttribute("transfer", transfer);
        model.addAttribute("accounts", accounts);

        return "transfer/make";
    }

    @PostMapping("/transfer/make")
    String processMakeTransferForm(@Valid Transfer transfer, BindingResult result) {

        BigDecimal transferOriginalAmount = transfer.getOriginalAmount();
        BigDecimal currentBalance = transfer.getFromAccount().getBalance();

        if (result.hasErrors() || transferOriginalAmount.compareTo(currentBalance) == 1) {

            return "redirect:/transfer/make";
        }

        transferService.processMakeTransferForm(transfer);

        return "redirect:/account/list";
    }

    @GetMapping("/transfer/history")
    String showTransferHistory(Model model) {

        List<Transfer> transfers = transferService.findAll();

        model.addAttribute("transfers", transfers);

        return "transfer/history";
    }
}
