package com.dev.firstsnow.util;

import com.dev.firstsnow.exception.CommonException;
import com.dev.firstsnow.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class TokenExtractor {
    private final JwtUtil jwtUtil;
    public static String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Long getId(HttpServletRequest request){
        try{
            String token = TokenExtractor.extractToken(request);
            Claims claims = jwtUtil.validateToken(token);
            Long userId = Long.parseLong(claims.get("id").toString());

            return userId;
        }
        catch (Exception e){
            throw new CommonException(ErrorCode.FAILURE_LOGIN);
        }
    }
}
