package com.vmo.freshermanagement.intern.service.Impl;

import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.repository.UserRepository;
import com.vmo.freshermanagement.intern.security.UserPrinciple;
import com.vmo.freshermanagement.intern.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.FOUND_USER_BY_USERNAME;
import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.USERNAME_NOT_FOUND;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.error(String.format(USERNAME_NOT_FOUND, username));
            throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username));
        } else {
            LOGGER.info(String.format(FOUND_USER_BY_USERNAME, username));
            return new UserPrinciple(user);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username));
        }
        return user;
    }
}
