package com.alanjsantos.productapi.service.jwt;

import com.alanjsantos.productapi.service.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Objects;

@Service
public class JwtService {
    private static final String BEARER = "bearer";

    @Value("{app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization (String token) {
        try {
            var accessToken =  token(token);
            var claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            var user = JwtResponse.getUser(claims);
            if (Objects.isNull(user) || Objects.isNull(user.getId())) {
                throw new AuthenticationException("The user is not valid.");
            }
        } catch (Exception e) {
            e.getStackTrace();
            throw new AuthenticationException("Error while trying to proccess the access Token.");
        }
    }

    private String token(String token) {
        if (Objects.isNull(token)) {
            throw new AuthenticationException("The access token was not informed.");
        }
        if (token.toLowerCase().contains(BEARER)) {
            token = token.toLowerCase();
            token = token.replace(BEARER, Strings.EMPTY);
        }
        return token;
    }
}
