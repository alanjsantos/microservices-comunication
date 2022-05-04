package com.alanjsantos.productapi.service.jwt;

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
            return JwtResponse.builder()
                    .id((Long) jwtCalims.get("id"))
                    .name((String) jwtCalims.get("name"))
                    .email((String) jwtCalims.get("email"))
                    .build();
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
