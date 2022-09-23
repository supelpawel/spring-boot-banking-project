package com.supelpawe.user.controller;

import com.supelpawe.role.model.Role;
import java.util.Collection;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.supelpawe.role.service.RoleService;
import com.supelpawe.user.model.User;
import com.supelpawe.user.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {

  private final UserService userService;
  private final RoleService roleService;

  @ModelAttribute("roles")
  Collection<Role> findAllRoles() {
    return roleService.findAll();
  }

  @GetMapping("/create-admin")
  public String createAdmin() {
    return userService.createAdmin();
  }

  @GetMapping("/user/add")
  String showAddUserForm(Model model) {
    return userService.showAddUserForm(model);
  }

  @PostMapping("/user/add")
  String processAddUserForm(@Valid @ModelAttribute("user") User user, BindingResult result) {
    return userService.processAddUserForm(user, result);
  }

  @GetMapping("/user/list")
  String showUserList(Model model) {
    return userService.showUserList(model);
  }

  @GetMapping("/user/edit")
  String showEditUserForm(@RequestParam("id") long id, Model model) {
    return userService.showEditUserForm(id, model);
  }

  @PostMapping("/user/edit")
  String processEditUserForm(@Valid User user, BindingResult result) {
    return userService.processEditUserForm(user, result);
  }
}
