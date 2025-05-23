package com.ssafy.questory.domain;

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
    private Member(String email, String password, String nickname,
                   Long exp, String title, LocalDate createdAt,
                   boolean isAdmin, boolean mode, boolean isDeleted,
                   String profileUrl) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.exp = exp != null ? exp : 0L;
        this.title = title != null ? title : "";
        this.createdAt = createdAt != null ? createdAt : LocalDate.now();
        this.isAdmin = isAdmin;
        this.mode = mode;
        this.isDeleted = isDeleted;
        this.profileUrl = profileUrl != null ? profileUrl : "";
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
