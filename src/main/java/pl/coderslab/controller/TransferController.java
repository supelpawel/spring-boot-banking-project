package pl.coderslab.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.entity.Account;
import pl.coderslab.entity.Transfer;
import pl.coderslab.entity.User;
import pl.coderslab.service.AccountService;
import pl.coderslab.service.CurrentUser;
import pl.coderslab.service.TransferService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final AccountService accountService;

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

        Account fromAccount = transfer.getFromAccount();
        Account toAccount = transfer.getToAccount();

        LocalDateTime transferDate = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));

        transfer.setTransferDate(transferDate);

        String fromCurrency = fromAccount.getCurrency();
        String toCurrency = toAccount.getCurrency();

        BigDecimal originalAmount = transfer.getOriginalAmount();
        BigDecimal finalAmount = transferService.calculateFinalAmount(originalAmount, fromCurrency, toCurrency);

        transfer.setFinalAmount(finalAmount);
        transferService.save(transfer);

        fromAccount.setBalance(fromAccount.getBalance().subtract(originalAmount));
        toAccount.setBalance(toAccount.getBalance().add(finalAmount));
        accountService.update(fromAccount);
        accountService.update(toAccount);

        return "redirect:/account/list";
    }

    @GetMapping("/transfer/history")
    String showTransferHistory(Model model) {

        List<Transfer> transfers = transferService.findAll();

        model.addAttribute("transfers", transfers);

        return "transfer/history";
    }
}
