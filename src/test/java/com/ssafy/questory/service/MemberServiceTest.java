package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerMember_success() {
        // given
        MemberRegistRequestDto request = MemberRegistRequestDto.builder()
                .email("test@example.com")
                .password("1234")
                .nickname("nickname")
                .build();

        when(memberRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");
        when(memberRepository.regist(any(Member.class))).thenReturn(1);

        // when
        MemberRegistResponseDto response = memberService.regist(request);

        // then
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getNickname()).isEqualTo("nickname");

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).regist(captor.capture());
        Member savedMember = captor.getValue();

        assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
        assertThat(savedMember.getPassword()).isEqualTo("encodedPassword");
        assertThat(savedMember.getNickname()).isEqualTo("nickname");
    }

    @Test
    @DisplayName("중복 이메일 예외 발생")
    void registerMember_duplicateEmail() {
        // given
        MemberRegistRequestDto request = MemberRegistRequestDto.builder()
                .email("duplicate@example.com")
                .password("1234")
                .nickname("nickname")
                .build();

        when(memberRepository.findByEmail("duplicate@example.com"))
                .thenReturn(Optional.of(mock(Member.class)));

        // when & then
        assertThatThrownBy(() -> memberService.regist(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }
}
