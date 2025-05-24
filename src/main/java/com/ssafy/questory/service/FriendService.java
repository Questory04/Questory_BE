package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.friend.FriendStatusRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.request.member.MemberSearchRequestDto;
import com.ssafy.questory.dto.response.friend.FollowResponseDto;
import com.ssafy.questory.dto.response.member.MemberSearchResponseDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.repository.FriendRepository;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public List<MemberInfoResponseDto> getFriendsInfo(Member member) {
        List<Member> friends = friendRepository.findFriendMembersByEmail(member.getEmail());
        return friends.stream()
                .map(MemberInfoResponseDto::from)
                .toList();
    }

    public List<FollowResponseDto> getFollowRequestInfo(Member member) {
        List<Friend> friends = friendRepository.findFollowRequestsByTargetEmail(member.getEmail());
        return friends.stream()
                .map(friend -> {
                    Member requester = memberRepository.findByEmail(friend.getRequesterEmail())
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

                    return FollowResponseDto.builder()
                            .requesterEmail(friend.getRequesterEmail())
                            .targetNickname(requester.getNickname())
                            .status(friend.getStatus())
                            .build();
                })
                .toList();
    }

    public void deleteFriend(Member member, MemberEmailRequestDto dto) {
        String email = member.getEmail();
        String targetEmail = dto.getEmail();

        boolean isFriend = friendRepository.existsByEmails(email, targetEmail);
        if (!isFriend) {
            throw new CustomException(ErrorCode.FRIEND_NOT_FOUND);
        }

        friendRepository.deleteFriend(email, targetEmail);
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

    public void cancelRequest(Member member, MemberEmailRequestDto memberEmailRequestDto) {
        friendRepository.cancelRequest(member.getEmail(), memberEmailRequestDto.getEmail());
    }

    public Page<FollowResponseDto> getFollowRequests(Member member, int page, int size) {
        int offset = page * size;
        List<Friend> sentRequests = friendRepository.findFollowRequestsByRequesterEmailWithPaging(member.getEmail(), offset, size);
        int total = friendRepository.countFollowRequestsByRequesterEmail(member.getEmail());

        List<FollowResponseDto> result = sentRequests.stream()
                .map(friend -> FollowResponseDto.builder()
                        .requesterEmail(friend.getRequesterEmail())
                        .targetNickname(friend.getTargetNickname())
                        .targetEmail(friend.getTargetEmail())
                        .status(friend.getStatus())
                        .build())
                .toList();
        return new PageImpl<>(result, PageRequest.of(page, size), total);
    }

    public Page<MemberSearchResponseDto> search(Member requester, MemberSearchRequestDto memberSearchRequestDto) {
        String email = memberSearchRequestDto.getEmail();
        int offSet = memberSearchRequestDto.getPage() * memberSearchRequestDto.getSize();
        int limit = memberSearchRequestDto.getSize();

        List<Member> members = memberRepository.searchByEmailWithPaging(email, requester.getEmail(), offSet, limit);
        List<MemberSearchResponseDto> result = members.stream()
                .filter(m -> !m.getEmail().equals(requester.getEmail()))
                .map(member -> MemberSearchResponseDto.from(
                        member.getEmail(),
                        member.getNickname(),
                        member.getProfileUrl()
                ))
                .toList();
        int total = memberRepository.countByEmail(email);
        return new PageImpl<>(result, PageRequest.of(memberSearchRequestDto.getPage(), memberSearchRequestDto.getSize()), total);
    }
}
