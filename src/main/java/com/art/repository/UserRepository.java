package com.art.repository;

import com.art.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findUserById(Long id);
}
