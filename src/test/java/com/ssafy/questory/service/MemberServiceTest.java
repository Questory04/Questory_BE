package com.ssafy.questory.service;

import com.ssafy.questory.config.jwt.CustomUserDetailsService;
import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberLoginRequestDto;
import com.ssafy.questory.dto.request.member.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.dto.response.member.MemberTokenResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private JwtService jwtService;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        userDetailsService = mock(CustomUserDetailsService.class);
        jwtService = mock(JwtService.class);

        memberService = new MemberService(
                memberRepository,
                passwordEncoder,
                authenticationManager,
                userDetailsService,
                jwtService
        );
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

    @Test
    @DisplayName("로그인 성공 시 토큰 반환")
    void login_success() {
        // given
        String email = "test@test.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        String expectedToken = "jwt.token.value";

        MemberLoginRequestDto request = MemberLoginRequestDto.builder()
                .email(email)
                .password(rawPassword)
                .build();

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);
        when(userDetails.getPassword()).thenReturn(encodedPassword);

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(expectedToken);

        // when
        MemberTokenResponseDto response = memberService.login(request);

        // then
        verify(authenticationManager).authenticate(
                argThat(authToken -> authToken instanceof UsernamePasswordAuthenticationToken &&
                        authToken.getPrincipal().equals(email) &&
                        authToken.getCredentials().equals(rawPassword))
        );

        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getAccessToken()).isEqualTo(expectedToken);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시 UsernameNotFoundException 발생")
    void login_emailNotFound_throwsException() {
        // given
        String email = "notfound@test.com";
        String password = "password";

        MemberLoginRequestDto request = MemberLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        when(userDetailsService.loadUserByUsername(email))
                .thenThrow(new UsernameNotFoundException("회원이 존재하지 않습니다."));

        // when & then
        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을 경우 RuntimeException 발생")
    void login_passwordMismatch_throwsException() {
        // given
        String email = "test@test.com";
        String rawPassword = "wrongpassword";
        String encodedPassword = "encodedPassword";

        MemberLoginRequestDto request = MemberLoginRequestDto.builder()
                .email(email)
                .password(rawPassword)
                .build();

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);
        when(userDetails.getPassword()).thenReturn(encodedPassword);

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("비밀번호 불일치");
    }
}
