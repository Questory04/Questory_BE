package com.ssafy.questory.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 회원 관련
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    MEMBER_DELETED(HttpStatus.FORBIDDEN, "해당 계정은 탈퇴된 상태입니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "권한이 없는 회원입니다."),

    // 인증 관련
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰 형식입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "인증 코드가 만료되었습니다."),
    VERIFICATION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다."),

    // 이메일
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),

    // 스탬프 관련
    STAMP_LIST_EMPTY(HttpStatus.NO_CONTENT, "회원이 보유한 스탬프가 없습니다."),

    // 퀘스트 관련
    ATTRACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 관광지가 존재하지 않습니다."),
    KEYWORD_EMPTY(HttpStatus.BAD_REQUEST, "검색어가 비어있습니다. 검색어를 입력해주세요."),
    QUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 퀘스트입니다."),
    QUEST_ALREADY_DELETED(HttpStatus.GONE, "이미 삭제된 퀘스트입니다."),
    QUEST_LIST_EMPTY(HttpStatus.NO_CONTENT, "조회할 퀘스트가 없습니다."),

    // 계획, 경로 관련
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 생성한 계획이 없습니다."),
    ALREADY_SHARED_PLAN(HttpStatus.BAD_REQUEST, "이미 공유된 계획입니다."),

    // 입력 검증
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),

    // 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatusCode() {
        return this.status.value();
    }
}
