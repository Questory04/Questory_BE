package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class Friend {
    private String requesterEmail;
    private String targetEmail;
    private FollowStatus status;
    private String targetNickname;

    protected Friend() {}

    @Builder
    private Friend(String requesterEmail, String targetEmail, FollowStatus status, String targetNickname) {
        this.requesterEmail = requesterEmail;
        this.targetEmail = targetEmail;
        this.status = status;
        this.targetNickname = targetNickname;
    }
}
