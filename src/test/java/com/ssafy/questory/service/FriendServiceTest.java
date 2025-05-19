package com.ssafy.questory.service;

import com.ssafy.questory.domain.FollowStatus;
import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.friend.FriendStatusRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.repository.FriendRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FriendServiceTest {

    private FriendRepository friendRepository;
    private FriendService friendService;

    @BeforeEach
    void setUp() {
        friendRepository = mock(FriendRepository.class);
        friendService = new FriendService(friendRepository);
    }

    @Test
    @DisplayName("친구 목록 조회 테스트")
    void testGetFriendsInfo() {
        // given
        Member member = Member.builder().email("me@domain.com").build();
        Member friend1 = Member.builder().email("a@domain.com").nickname("A").build();

        when(friendRepository.findFriendMembersByEmail("me@domain.com"))
                .thenReturn(Optional.of(friend1));

        // when
        List<MemberInfoResponseDto> friendsInfo = friendService.getFriendsInfo(member);

        // then
        assertThat(friendsInfo).hasSize(1);
        assertThat(friendsInfo.get(0).getEmail()).isEqualTo("a@domain.com");
    }

    @Test
    @DisplayName("친구 요청 테스트")
    void testRequestFriend() {
        // given
        Member member = Member.builder().email("me@domain.com").build();
        MemberEmailRequestDto dto = MemberEmailRequestDto.builder().email("other@domain.com").build();

        // when
        friendService.request(member, dto);

        // then
        ArgumentCaptor<Friend> captor = ArgumentCaptor.forClass(Friend.class);
        verify(friendRepository, times(1)).request(captor.capture());
        Friend sentFriend = captor.getValue();
        assertThat(sentFriend.getRequesterEmail()).isEqualTo("me@domain.com");
        assertThat(sentFriend.getTargetEmail()).isEqualTo("other@domain.com");
        assertThat(sentFriend.getStatus()).isEqualTo(FollowStatus.APPLIED);
    }

    @Test
    @DisplayName("친구 요청 수락 테스트")
    void testAcceptFriendRequest() {
        // given
        Member member = Member.builder().email("target@domain.com").build();
        FriendStatusRequestDto dto = FriendStatusRequestDto.builder()
                .requesterEmail("requester@domain.com")
                .status(FollowStatus.ACCEPTED)
                .build();

        // when
        friendService.update(member, dto);

        // then
        verify(friendRepository).deleteFollowRequest("requester@domain.com", "target@domain.com");
        verify(friendRepository).insertFriendRelation("requester@domain.com", "target@domain.com");
    }

    @Test
    @DisplayName("친구 요청 거절 테스트")
    void testRejectFriendRequest() {
        // given
        Member member = Member.builder().email("target@domain.com").build();
        FriendStatusRequestDto dto = FriendStatusRequestDto.builder()
                .requesterEmail("requester@domain.com")
                .status(FollowStatus.DENIED)
                .build();

        // when
        friendService.update(member, dto);

        // then
        verify(friendRepository, never()).deleteFollowRequest(anyString(), anyString());
        verify(friendRepository, never()).insertFriendRelation(anyString(), anyString());
        verify(friendRepository, atLeastOnce()).update(any(Friend.class));
    }
}
