package com.dev.firstsnow.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class TokenExtractor {
    private final JwtUtil jwtUtil;
    public static String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 다음부터 토큰이 시작됩니다.
        }
        return null;
    }

    public Long getId(HttpServletRequest request){
        String token = TokenExtractor.extractToken(request);
        Claims claims = jwtUtil.validateToken(token);
        Long userId = Long.parseLong(claims.get("id").toString());

        return userId;
    }
}
