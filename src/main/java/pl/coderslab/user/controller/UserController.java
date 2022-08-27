package pl.coderslab.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.role.data.Role;
import pl.coderslab.role.service.RoleService;
import pl.coderslab.user.service.UserService;
import pl.coderslab.user.data.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @ModelAttribute("roles")
    Collection<Role> findAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/create-admin")
    public String createAdmin() {

        if (userService.findByUserName("admin1") == null) {

            User user = new User();
            userService.saveAdmin(user);
            log.info("admin created");

            return "redirect:/login";
        }

        log.info("admin already in database");

        return "redirect:/login";
    }

    @GetMapping("/user/add")
    String showAddUserForm(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "user/add";
    }

    @PostMapping("/user/add")
    String processAddUserForm(@Valid @ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors() || userService.calculateAgeFromPesel(user.getPesel()) < 18 ||
                userService.findByUserName(user.getUsername()) != null) {

            return "user/add";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/user/list")
    String showUserList(Model model) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/user/edit")
    String showEditUserForm(@RequestParam("id") long id, Model model) {

        userService.findById(id)
                .ifPresent(u -> model.addAttribute("user", u));

        return "user/edit";
    }

    @PostMapping("/user/edit")
    String processEditUserForm(@Valid User user, BindingResult result) {

        if (result.hasErrors()) {
            return "user/edit";
        }

        userService.saveUser(user);

        return "redirect:/user/list";
    }
}
