package com.dev.firstsnow.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_PARAMETER("4000", HttpStatus.BAD_REQUEST, "유효하지 않는 파라미터입니다."),
    MISSING_REQUEST_PARAMETER("4001", HttpStatus.BAD_REQUEST, "필수 파라미터가 누락되었습니다."),
    INVALID_ROLE("4002", HttpStatus.NOT_FOUND, "유효하지 않은 권한입니다."),
    INVALID_PROVIDER("4003", HttpStatus.NOT_FOUND, "유효하지 않은 제공자입니다."),
    INVALID_HEADER("4004", HttpStatus.NOT_FOUND, "유효하지 않은 헤더값입니다."),
    DUPLICATED_SERIAL_ID("4005", HttpStatus.NOT_FOUND, "중복된 아이디입니다."),

    FAILURE_LOGIN("4010", HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다"),

    // Not Found Error
    NOT_FOUND_USER("4040", HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."),
    NOT_END_POINT("4041", HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트입니다."),
    NOT_FOUND_RESOURCE("4042", HttpStatus.NOT_FOUND, "요청한 데이터를 찾을 수 없습니다."),


    // Access Denied Error
    ACCESS_DENIED_ERROR("4030", HttpStatus.FORBIDDEN, "액세스 권한이 없습니다."),

    // Server, File Up/DownLoad Error
    SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}