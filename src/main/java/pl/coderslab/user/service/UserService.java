package pl.coderslab.user.service;

import java.util.List;
import java.util.Optional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.coderslab.user.model.User;

public interface UserService {

  User findByUserName(String username);

  String showAddUserForm(Model model);

  String processAddUserForm(User user, BindingResult result);

  String showEditUserForm(long id, Model model);

  String processEditUserForm(User user, BindingResult result);

  String createAdmin();

  String showUserList(Model model);

  Optional<User> findById(Long id);

  List<User> findAll();

  void update(User user);

  int calculateAgeFromPesel(String pesel);
}
