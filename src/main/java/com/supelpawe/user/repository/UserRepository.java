package com.supelpawe.user.repository;

import com.supelpawe.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
