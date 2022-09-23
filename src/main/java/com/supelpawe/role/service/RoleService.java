package com.supelpawe.role.service;

import com.supelpawe.role.model.Role;
import com.supelpawe.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
