package com.ssafy.questory.dto.request.friend;

import com.ssafy.questory.domain.FollowStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendStatusRequestDto {
    private String requesterEmail;
    private FollowStatus status;

    @Builder
    private FriendStatusRequestDto(String requesterEmail, FollowStatus status) {
        this.requesterEmail = requesterEmail;
        this.status = status;
    }
}
