package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Friend {
    private String followingEmail;
    private String followedEmail;
    private FollowStatus status;

    protected Friend() {}

    @Builder
    private Friend(String followingEmail, String followedEmail, FollowStatus status) {
        this.followingEmail = followingEmail;
        this.followedEmail = followedEmail;
        this.status = status;
    }
}
