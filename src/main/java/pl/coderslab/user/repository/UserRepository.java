package pl.coderslab.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.user.data.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
