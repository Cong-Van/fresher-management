package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {

    User getUserByUsername(String username);

}
