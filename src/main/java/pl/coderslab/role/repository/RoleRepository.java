package pl.coderslab.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.role.data.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
