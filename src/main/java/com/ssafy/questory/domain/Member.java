package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
public class Member {
    private String email;
    private String password;
    private String nickname;
    private Long exp;
    private String title;
    private LocalDate createAt;
    private boolean isAdmin;
    private boolean mode;
    private boolean isDeleted;

    protected Member() {}

    @Builder
    private Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.exp = 0L;
        this.title = "";
        this.createAt = LocalDate.now();
        this.isAdmin = false;
        this.mode = false;
        this.isDeleted = false;
    }
}
