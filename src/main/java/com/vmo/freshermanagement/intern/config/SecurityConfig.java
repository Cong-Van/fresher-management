package com.vmo.freshermanagement.intern.config;

import com.vmo.freshermanagement.intern.security.JwtAccessDeniedHandler;
import com.vmo.freshermanagement.intern.security.JwtAuthenticationEntryPoint;
import com.vmo.freshermanagement.intern.security.JwtAuthorizationFilter;
import com.vmo.freshermanagement.intern.service.CenterService;
import com.vmo.freshermanagement.intern.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.vmo.freshermanagement.intern.constant.SecurityConstant.AUTH_WHITELIST;
import static com.vmo.freshermanagement.intern.constant.SecurityConstant.PUBLIC_URLS;

@Configuration
public class SecurityConfig {

    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(JwtAccessDeniedHandler jwtAccessDeniedHandler,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(LoginService loginService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(loginService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exc -> exc
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
