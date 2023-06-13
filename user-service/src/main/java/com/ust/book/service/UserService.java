package com.ust.book.service;

import com.ust.book.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> checkLogin(String username, String password);
    Optional<User> findByUsername(String username);

    long findCount();

    User save(User reg);
}
