package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User addUser(String username, String password);

    User updateUser(int userId, String username, String password);

    User lockUser(String username);

    User unlockUser(String username);
}
