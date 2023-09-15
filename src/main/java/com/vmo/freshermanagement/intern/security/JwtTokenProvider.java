package com.vmo.freshermanagement.intern.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.vmo.freshermanagement.intern.constant.SecurityConstant.*;

@Component
public class JwtTokenProvider{

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrinciple userPrinciple) {
        String claim = userPrinciple.getAuthorities().iterator().next().getAuthority();

        return JWT.create().withIssuer(VMO_INTERN).withAudience(ADMINISTRATION).withIssuedAt(new Date())
                .withSubject(userPrinciple.getUsername()).withClaim(AUTHORITY, claim)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_JWT_TIME))
                .sign(HMAC512(secret.getBytes()));
    }

    public GrantedAuthority getAuthority(String token) {
        String claim = getClaimFromToken(token);
        return new SimpleGrantedAuthority(claim);
    }

    private String getClaimFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITY).asString();
    }

    public Authentication getAuthentication(String username, GrantedAuthority authority, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(authority));
        userPassAuthToken.setDetails(new WebAuthenticationDetails(request));
        return userPassAuthToken;
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotBlank(username) && !isTokenExpired(verifier, token);
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;

        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(VMO_INTERN).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
}
