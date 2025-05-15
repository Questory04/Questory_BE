package com.ssafy.questory.dto.response.error;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDto {
    private final int status;
    private final String message;

    @Builder
    public ErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
