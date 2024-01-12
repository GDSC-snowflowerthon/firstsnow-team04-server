package com.dev.firstsnow.controller;

import com.dev.firstsnow.dto.common.ResponseDto;
import com.dev.firstsnow.dto.request.UserRequestDto;
import com.dev.firstsnow.dto.response.CreateUserDto;
import com.dev.firstsnow.dto.response.IsDuplicateDto;
import com.dev.firstsnow.service.UserService;
import com.dev.firstsnow.util.CookieUtil;
import com.dev.firstsnow.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-in")
    public ResponseDto<?> createUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        // 사용자 생성 및 사용자 ID 가져오기
        CreateUserDto createUserDto = userService.createUser(userRequestDto);
        Long userId = createUserDto.user_id();

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(userId, 9999999);

        // 쿠키에 JWT 토큰 저장
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // 쿠키가 전송되는 경로 설정
        response.addCookie(cookie);

        return ResponseDto.ok(userService.createUser(userRequestDto));
    }

    @GetMapping("/is-duplicate") // 닉네임 중복검사
    public ResponseDto<?> isDuplicate(@RequestParam("nickname") String nickname) {
        return ResponseDto.ok(userService.isDuplicate(nickname));
    }
}
