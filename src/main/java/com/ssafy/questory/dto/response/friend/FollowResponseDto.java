package com.ssafy.questory.dto.response.friend;

import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponseDto {
    private String requesterEmail;
    private FollowStatus status;

    @Builder
    private FollowResponseDto(String requesterEmail, FollowStatus status) {
        this.requesterEmail = requesterEmail;
        this.status = status;
    }

    public static FollowResponseDto from(Friend friend) {
        return FollowResponseDto.builder()
                .requesterEmail(friend.getRequesterEmail())
                .status(friend.getStatus())
                .build();
    }
}
