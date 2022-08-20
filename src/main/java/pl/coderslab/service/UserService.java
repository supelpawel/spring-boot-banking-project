package pl.coderslab.service;

import pl.coderslab.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUserName(String username);

    void save(User user);

    void hashPasswordAndUpdate(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    void update(User user);
}
