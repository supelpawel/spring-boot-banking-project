package pl.coderslab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Role;
import pl.coderslab.repository.RoleRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
}
