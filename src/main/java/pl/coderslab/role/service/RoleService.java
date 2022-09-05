package pl.coderslab.role.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.role.model.Role;
import pl.coderslab.role.repository.RoleRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
