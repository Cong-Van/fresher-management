package com.vmo.freshermanagement.intern.repository;

import com.vmo.freshermanagement.intern.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
