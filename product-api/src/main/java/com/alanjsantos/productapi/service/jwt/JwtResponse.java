package com.alanjsantos.productapi.service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private Long id;
    private String name;
    private String email;

    public static JwtResponse getUser(Claims jwtCalims) {
        try {
            return new ObjectMapper().convertValue(jwtCalims.get("authUser"), JwtResponse.class);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
