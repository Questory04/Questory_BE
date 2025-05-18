package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Friend {
    private String requesterEmail;
    private String targetEmail;
    private FollowStatus status;

    protected Friend() {}

    @Builder
    private Friend(String requesterEmail, String targetEmail, FollowStatus status) {
        this.requesterEmail = requesterEmail;
        this.targetEmail = targetEmail;
        this.status = status;
    }
}
