package com.ssafy.questory.dto.response.friend;

import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponseDto {
    private String requesterEmail;
    private String targetNickname;
    private String targetEmail;
    private FollowStatus status;

    @Builder
    private FollowResponseDto(String requesterEmail, FollowStatus status, String targetNickname, String targetEmail) {
        this.requesterEmail = requesterEmail;
        this.targetNickname = targetNickname;
        this.targetEmail = targetEmail;
        this.status = status;
    }

    public static FollowResponseDto from(Friend friend) {
        return FollowResponseDto.builder()
                .requesterEmail(friend.getRequesterEmail())
                .status(friend.getStatus())
                .targetNickname(friend.getTargetNickname())
                .targetEmail(friend.getTargetEmail())
                .build();
    }
}
