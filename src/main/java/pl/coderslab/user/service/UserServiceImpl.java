package pl.coderslab.user.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.coderslab.role.model.Role;
import pl.coderslab.role.repository.RoleRepository;
import pl.coderslab.user.model.User;
import pl.coderslab.user.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  public static final int MIN_AGE = 18;
  public static final String ADMIN_USERNAME = "admin1";
  public static final String ADMIN_PASS = "admin1";
  public static final String ADMIN_PESEL = "88072808939";
  public static final String ADMIN_ROLE = "ROLE_ADMIN";
  public static final int YEAR_START_INDEX = 0;
  public static final int YEAR_END_INDEX = 2;
  public static final int MONTH_END_INDEX = 4;
  public static final int DAY_END_INDEX = 6;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public User findByUserName(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void update(User user) {
    userRepository.save(user);
  }

  public String showAddUserForm(Model model) {
    User user = new User();

    model.addAttribute("user", user);
    return "user/add";
  }

  @Transactional
  public String processAddUserForm(User user, BindingResult result) {
    if (result.hasErrors() || calculateAgeFromPesel(user.getPesel()) < MIN_AGE ||
        findByUserName(user.getUsername()) != null) {
      return "user/add";
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setAge(calculateAgeFromPesel(user.getPesel()));
    user.setEnabled(true);
    Role userRole = roleRepository.findByName("ROLE_USER");
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    userRepository.save(user);
    return "redirect:/login";
  }

  public String showEditUserForm(long id, Model model) {
    findById(id)
        .ifPresent(u -> model.addAttribute("user", u));
    return "user/edit";
  }

  public String showUserList(Model model) {
    List<User> users = findAll();

    model.addAttribute("users", users);
    return "user/list";
  }

  @Transactional
  public String processEditUserForm(User user, BindingResult result) {
    if (result.hasErrors()) {
      return "user/edit";
    }
    processAddUserForm(user, result);
    return "redirect:/user/list";
  }

  @Override
  @Transactional
  public String createAdmin() {
    if (findByUserName(ADMIN_USERNAME) == null) {
      User user = new User();

      user.setUsername(ADMIN_USERNAME);
      user.setPassword(ADMIN_PASS);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setPesel(ADMIN_PESEL);
      user.setAge(calculateAgeFromPesel(user.getPesel()));
      user.setEnabled(true);
      Role userRole = roleRepository.findByName(ADMIN_ROLE);
      user.setRoles(new HashSet<>(Arrays.asList(userRole)));
      userRepository.save(user);
      log.info("admin created");
      return "redirect:/login";
    }
    log.info("admin already in database");
    return "redirect:/login";
  }

  @Override
  public int calculateAgeFromPesel(String pesel) {
    int userAge;
    String tempYear = (pesel.substring(YEAR_START_INDEX, YEAR_END_INDEX));
    String year =
        tempYear.charAt(YEAR_START_INDEX) == '0' || tempYear.charAt(YEAR_START_INDEX) == '1'
            || tempYear.charAt(
            YEAR_START_INDEX) == '2' ? 20
            + tempYear : 19 + tempYear;
    int month = Integer.parseInt(pesel.substring(YEAR_END_INDEX, MONTH_END_INDEX));
    int day = Integer.parseInt(pesel.substring(MONTH_END_INDEX, DAY_END_INDEX));
    LocalDate ld = LocalDate.now();
    LocalDate birthDate = LocalDate.of(Integer.parseInt(year), month, day);

    userAge = (int) ChronoUnit.YEARS.between(birthDate, ld);
    return userAge;
  }
}

