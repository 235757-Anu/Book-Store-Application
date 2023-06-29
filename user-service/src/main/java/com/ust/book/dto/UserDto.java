package com.ust.book.dto;

import com.ust.book.domain.Role;

public record UserDto(long userId, String username, String email, String password, Role role) {
}
