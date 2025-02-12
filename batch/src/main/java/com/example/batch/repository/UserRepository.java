package com.example.batch.repository;

import com.example.batch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
}
