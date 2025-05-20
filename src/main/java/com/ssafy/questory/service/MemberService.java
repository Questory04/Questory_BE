package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
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

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.MEMBER_DELETED);
        }

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
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void validateDuplicatedMember(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    if (member.isDeleted()) {
                        throw new CustomException(ErrorCode.MEMBER_DELETED);
                    }
                    throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
                });
    }
}
