package com.ssafy.questory.service;

import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.friend.FriendStatusRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public List<MemberInfoResponseDto> getFriendsInfo(Member member) {
        Optional<Member> friends = friendRepository.findFriendMembersByEmail(member.getEmail());
        return friends.stream()
                .map(MemberInfoResponseDto::from)
                .toList();
    }

    public void request(Member member, MemberEmailRequestDto memberEmailRequestDto) {
        Friend friend = Friend.builder()
                .requesterEmail(member.getEmail())
                .targetEmail(memberEmailRequestDto.getEmail())
                .status(FollowStatus.APPLIED)
                .build();
        friendRepository.request(friend);
    }

    public void update(Member member, FriendStatusRequestDto friendStatusRequestDto) {
        Friend friend = Friend.builder()
                .requesterEmail(friendStatusRequestDto.getRequesterEmail())
                .targetEmail(member.getEmail())
                .status(friendStatusRequestDto.getStatus())
                .build();
        friendRepository.update(friend);
    }
}
