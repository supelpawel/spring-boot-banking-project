package pl.coderslab.service;

import pl.coderslab.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUserName(String username);

    void saveUser(User user);

    void saveAdmin(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    void update(User user);

    int calculateAgeFromPesel(String pesel);
}
