package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

    private void validateDuplicatedMember(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(error -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }
}
