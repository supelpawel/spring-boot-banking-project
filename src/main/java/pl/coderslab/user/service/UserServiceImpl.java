package pl.coderslab.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.role.model.Role;
import pl.coderslab.role.repository.RoleRepository;
import pl.coderslab.user.model.User;
import pl.coderslab.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

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

    @Override
    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAge(calculateAgeFromPesel(user.getPesel()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveAdmin(User user) {

        user.setUsername("admin1");
        user.setPassword("admin1");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPesel("88072808939");
        user.setAge(calculateAgeFromPesel(user.getPesel()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        userRepository.save(user);
    }

    @Override
    public int calculateAgeFromPesel(String pesel) {

        int userAge;

        String tempYear = (pesel.substring(0, 2));
        String year = tempYear.charAt(0) == '0' || tempYear.charAt(0) == '1' || tempYear.charAt(0) == '2' ? 20 + tempYear : 19 + tempYear;

        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        LocalDate ld = LocalDate.now();
        LocalDate birthDate = LocalDate.of(Integer.parseInt(year), month, day);

        userAge = (int) ChronoUnit.YEARS.between(birthDate, ld);

        return userAge;
    }
}

