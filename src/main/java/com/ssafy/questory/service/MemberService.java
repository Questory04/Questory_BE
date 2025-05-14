package com.ssafy.questory.service;

import com.ssafy.questory.config.jwt.CustomUserDetailsService;
import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberLoginRequestDto;
import com.ssafy.questory.dto.request.member.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.dto.response.member.MemberTokenResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public MemberRegistResponseDto regist(MemberRegistRequestDto memberRegistRequestDto) {
        String email = memberRegistRequestDto.getEmail();
        String password = passwordEncoder.encode(memberRegistRequestDto.getPassword());
        String nickname = memberRegistRequestDto.getNickname();

        validateDuplicatedMember(email);

        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
        memberRepository.regist(member);
        return MemberRegistResponseDto.from(member);
    }

    public MemberTokenResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();

        authenticate(email, password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        checkPassword(password, userDetails.getPassword());

        String token = jwtService.generateToken(userDetails);
        return MemberTokenResponseDto.from(email, token);
    }

    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RuntimeException("인증되지 않은 아이디입니다.");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new RuntimeException("비밀번호 불일치");
        }
    }

    private void validateDuplicatedMember(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(error -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }
}
