package com.dev.firstsnow.util;

import com.dev.firstsnow.exception.CommonException;
import com.dev.firstsnow.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;


public class HeaderUtil {
    public static Optional<String> refineHeader(
            HttpServletRequest request,
            String header,
            String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            throw new CommonException(ErrorCode.INVALID_HEADER);
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }
}