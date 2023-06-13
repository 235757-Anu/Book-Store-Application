package com.ust.book.repository;

import com.ust.book.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameAndPassword(String username,String password);
    Optional<User> findByUsername(String username);
}
