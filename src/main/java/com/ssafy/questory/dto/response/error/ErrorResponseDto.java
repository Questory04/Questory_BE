package com.ssafy.questory.dto.response.error;

import com.ssafy.questory.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto {
    private int status;
    private String message;

    public static ErrorResponseDto of(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .status(errorCode.getStatusCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponseDto of(ErrorCode errorCode, String customMessage) {
        return ErrorResponseDto.builder()
                .status(errorCode.getStatusCode())
                .message(customMessage)
                .build();
    }
}