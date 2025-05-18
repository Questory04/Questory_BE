package com.ssafy.questory.service;

import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public void request(Member member, MemberEmailRequestDto memberEmailRequestDto) {
        Friend friend = Friend.builder()
                .requesterEmail(member.getEmail())
                .targetEmail(memberEmailRequestDto.getEmail())
                .status(FollowStatus.APPLIED)
                .build();
        friendRepository.request(friend);
    }
}
