package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberModifyPasswordRequestDto;
import com.ssafy.questory.dto.request.member.MemberModifyRequestDto;
import com.ssafy.questory.dto.request.member.MemberSearchRequestDto;
import com.ssafy.questory.dto.response.member.MemberSearchResponseDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class MemberAuthServiceTest {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private MemberAuthService memberAuthService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        memberAuthService = new MemberAuthService(memberRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원 정보 조회 성공")
    void getInfo_success() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("testNick")
                .password("pw")
                .build();

        when(memberRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(member));

        // when
        MemberInfoResponseDto response = memberAuthService.getInfo(member);

        // then
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getNickname()).isEqualTo("testNick");
    }

    @Test
    @DisplayName("회원 정보 조회 실패 - 존재하지 않는 회원")
    void getInfo_fail() {
        // given
        Member member = Member.builder()
                .email("none@example.com")
                .nickname("none")
                .password("pw")
                .build();

        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberAuthService.getInfo(member))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("회원 정보 수정")
    void modify_success() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("oldNick")
                .password("pw")
                .build();

        MemberModifyRequestDto dto = MemberModifyRequestDto.builder()
                .nickname("newNick")
                .title("newTitle")
                .mode(true)
                .build();

        // when
        memberAuthService.modify(member, dto);

        // then
        verify(memberRepository).modify(member);
        assertThat(member.getNickname()).isEqualTo("newNick");
        assertThat(member.getTitle()).isEqualTo("newTitle");
        assertThat(member.isMode()).isTrue();
    }

    @Test
    @DisplayName("비밀번호 수정")
    void modifyPassword_success() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("nick")
                .password("oldPassword")
                .build();

        MemberModifyPasswordRequestDto dto = MemberModifyPasswordRequestDto.builder()
                .password("newPw")
                .build();

        when(passwordEncoder.encode("newPw")).thenReturn("encodedPw");

        // when
        memberAuthService.modifyPassword(member, dto);

        // then
        verify(memberRepository).changePassword(member, "encodedPw");
        assertThat(member.getPassword()).isEqualTo("encodedPw");
    }


    @Test
    @DisplayName("회원 탈퇴")
    void withdraw_success() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("nick")
                .password("pw")
                .build();

        // when
        memberAuthService.withdraw(member);

        // then
        verify(memberRepository).withdraw(member);
    }

    @Test
    @DisplayName("이메일 키워드로 유저 검색 - 페이징 포함")
    void searchMembersByEmail_withPaging() {
        // given
        String keyword = "kim";
        int page = 0;
        int size = 2;
        int offset = page * size;

        Member member1 = Member.builder()
                .email("kim1@example.com")
                .nickname("김하나")
                .profileUrl("https://cdn.questory.com/kim1.jpg")
                .build();

        Member member2 = Member.builder()
                .email("kim2@example.com")
                .nickname("김둘")
                .profileUrl("https://cdn.questory.com/kim2.jpg")
                .build();

        List<Member> memberList = List.of(member1, member2);

        given(memberRepository.searchByEmailWithPaging(keyword, offset, size))
                .willReturn(memberList);
        given(memberRepository.countByEmail(keyword)).willReturn(10);

        MemberSearchRequestDto requestDto = MemberSearchRequestDto.builder()
                .email(keyword)
                .page(page)
                .size(size)
                .build();

        Member member = Member.builder()
                .email("test@example.com")
                .nickname("nick")
                .password("pw")
                .build();

        // when
        Page<MemberSearchResponseDto> result = memberAuthService.search(member, requestDto);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(result.getContent().get(0).getEmail()).isEqualTo("kim1@example.com");
        assertThat(result.getContent().get(1).getNickname()).isEqualTo("김둘");
    }
}
