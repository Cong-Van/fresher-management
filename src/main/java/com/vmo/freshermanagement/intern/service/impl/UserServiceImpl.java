package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.exception.UserNotFoundException;
import com.vmo.freshermanagement.intern.exception.UsernameExistException;
import com.vmo.freshermanagement.intern.exception.UsernameNotExistException;
import com.vmo.freshermanagement.intern.repository.UserRepository;
import com.vmo.freshermanagement.intern.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            LOGGER.error(String.format(USERNAME_ALREADY_EXIST, username));
            throw new UsernameExistException(String.format(USERNAME_ALREADY_EXIST, username));
        } else {
            User newUser = new User(username, encodePassword(password));
            userRepository.save(newUser);
            return newUser;
        }
    }

    @Override
    public User updateUser(int userId, String username, String password) {
        Optional<User> userOp = userRepository.findById(userId);
        User user = userRepository.findByUsername(username);

        if (!userOp.isPresent()) {
            throw new UserNotFoundException(NOT_FOUND_USER);
        }
        User updateUse = userOp.get();
        if (user != null && !updateUse.getUsername().equals(username)) {
            throw new UsernameExistException(String.format(USERNAME_ALREADY_EXIST, username));
        }

        updateUse.setUsername(username);
        updateUse.setPassword(encodePassword(password));
        userRepository.save(updateUse);
        return updateUse;
    }

    @Override
    public User lockUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotExistException(USERNAME_NOT_EXIST);
        }
        user.setNotLocked(false);
        userRepository.save(user);
        return user;
    }

    @Override
    public User unlockUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotExistException(USERNAME_NOT_EXIST);
        }
        user.setNotLocked(true);
        userRepository.save(user);
        return user;
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
