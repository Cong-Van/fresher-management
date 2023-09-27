package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.constant.SecurityConstant;
import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.security.JwtTokenProvider;
import com.vmo.freshermanagement.intern.security.UserPrinciple;
import com.vmo.freshermanagement.intern.service.LoginService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class LoginRestController {

    private LoginService loginService;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    public LoginRestController(LoginService loginService,
                               JwtTokenProvider jwtTokenProvider,
                               AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        authenticate(username, password);

        User loginUser = loginService.getUserByUsername(username);
        UserPrinciple userPrinciple = new UserPrinciple(loginUser);
        HttpHeaders headers = getJwtHeader(userPrinciple);

        return new ResponseEntity<>(loginUser, headers, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrinciple userPrinciple) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrinciple));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
