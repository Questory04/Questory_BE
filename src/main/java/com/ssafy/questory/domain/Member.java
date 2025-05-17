package com.ssafy.questory.domain;

import com.ssafy.questory.dto.request.member.MemberModifyPasswordRequestDto;
import com.ssafy.questory.dto.request.member.MemberModifyRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Member {
    private String email;
    private String password;
    private String nickname;
    private Long exp;
    private String title;
    private LocalDate createdAt;
    private boolean isAdmin;
    private boolean mode;
    private boolean isDeleted;
    private String profileUrl;

    protected Member() {}

    @Builder
    private Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.exp = 0L;
        this.title = "";
        this.createdAt = LocalDate.now();
        this.isAdmin = false;
        this.mode = false;
        this.isDeleted = false;
        this.profileUrl = "";
    }

    public void modify(MemberModifyRequestDto memberModifyRequestDto) {
        this.nickname = memberModifyRequestDto.getNickname();
        this.title = memberModifyRequestDto.getTitle();
        this.mode = memberModifyRequestDto.isMode();
    }

    public void modifyPassword(String newPassword) {
        this.password = newPassword;
    }
}
