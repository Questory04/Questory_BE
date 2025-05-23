package com.ssafy.questory.service;

import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.friend.FriendStatusRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.response.friend.FollowResponseDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public List<FollowResponseDto> getFollowRequestInfo(Member member) {
        List<Friend> friends = friendRepository.findFollowRequestsByTargetEmail(member.getEmail());
        return friends.stream()
                .map(FollowResponseDto::from)
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
        String requesterEmail = friendStatusRequestDto.getRequesterEmail();
        FollowStatus status = friendStatusRequestDto.getStatus();

        Friend friend = Friend.builder()
                .requesterEmail(friendStatusRequestDto.getRequesterEmail())
                .targetEmail(member.getEmail())
                .status(friendStatusRequestDto.getStatus())
                .build();

        if (status == FollowStatus.ACCEPTED) {
            friendRepository.deleteFollowRequest(requesterEmail, member.getEmail());
            friendRepository.insertFriendRelation(requesterEmail, member.getEmail());
        } else {
            friendRepository.update(friend);
        }
        friendRepository.update(friend);
    }

    public Page<FollowResponseDto> getFollowRequests(Member member, int page, int size) {
        int offset = page * size;
        List<Friend> sentRequests = friendRepository.findFollowRequestsByRequesterEmailWithPaging(member.getEmail(), offset, size);
        int total = friendRepository.countFollowRequestsByRequesterEmail(member.getEmail());

        List<FollowResponseDto> result = sentRequests.stream()
                .map(friend -> FollowResponseDto.builder()
                        .requesterEmail(friend.getRequesterEmail())
                        .targetNickname(friend.getTargetNickname())
                        .status(friend.getStatus())
                        .build())
                .toList();
        return new PageImpl<>(result, PageRequest.of(page, size), total);
    }
}
