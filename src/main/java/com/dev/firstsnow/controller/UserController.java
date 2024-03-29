package com.dev.firstsnow.controller;

import com.dev.firstsnow.dto.common.ResponseDto;
import com.dev.firstsnow.dto.request.UserRequestDto;
import com.dev.firstsnow.dto.response.UserResponseDto;
import com.dev.firstsnow.service.UserService;
import com.dev.firstsnow.util.JwtUtil;
import com.dev.firstsnow.util.TokenExtractor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final TokenExtractor tokenExtractor;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-in")
    public ResponseDto<?> createUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseDto.ok(userService.createUser(userRequestDto));
    }

    @GetMapping("/is-duplicate") // 닉네임 중복검사
    public ResponseDto<?> isDuplicate(@RequestParam("nickname") String nickname) {
        return ResponseDto.ok(userService.isDuplicate(nickname));
    }

    @PostMapping("/login") // 로그인
    public ResponseDto<?> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        // 사용자 생성 및 사용자 ID 가져오기
        UserResponseDto userResponseDto = userService.login(userRequestDto);
        Long userId = userResponseDto.getUser_id();

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(userId, 9999999);

        UserResponseDto login = userService.login(userRequestDto);

        login.setToken(token);

        return ResponseDto.ok(login);
    }

    @GetMapping("/logout") //로그아웃
    public ResponseDto<?> logout(HttpServletResponse response) {
        // 쿠키 삭제를 위해 만료 시간을 과거로 설정
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 만료 시간을 0으로 설정

        response.addCookie(cookie); // 변경된 쿠키를 응답에 추가

        return ResponseDto.ok("Logged out successfully");
    }

    @PatchMapping ("/change-nickname") //닉네임 변경
    public ResponseDto<?> changeNickname(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request){
        Long userId = tokenExtractor.getId(request);

        return ResponseDto.ok(userService.changeNickname(userId, userRequestDto.nickname()));
    }

    @PatchMapping("/change-location") //위치 변경
    public ResponseDto<?> changeLocation(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request){
        Long userId = tokenExtractor.getId(request);

        return ResponseDto.ok(userService.changeLocation(userId, userRequestDto.location()));
    }

    @PatchMapping("/change-snowman") //눈사람 변경
    public ResponseDto<?> changeSnowman(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request){
        Long userId = tokenExtractor.getId(request);

        return ResponseDto.ok(userService.changeSnowman(userId, userRequestDto.snowman_num()));
    }

    @GetMapping("/duplicate") // 닉네임 중복체크
    public ResponseDto<?> isDuplicateUser(@RequestParam("nickname") String nickname){
        return ResponseDto.ok(userService.isDuplicate(nickname));
    }

    @GetMapping("/search-users") // 닉네임 검색
    public ResponseDto<?> searchUsers(@RequestParam String keyword){
        Integer pageIndex = 0;
        Integer pageSize = 100;
        return ResponseDto.ok(userService.searchUsers(keyword, pageIndex, pageSize));
    }
}
