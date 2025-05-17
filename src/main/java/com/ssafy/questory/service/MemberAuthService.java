package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberModifyPasswordRequestDto;
import com.ssafy.questory.dto.request.member.MemberModifyRequestDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberInfoResponseDto getInfo(Member member) {
        return memberRepository.findByEmail(member.getEmail())
                .map(MemberInfoResponseDto::from)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberInfoResponseDto modify(Member member, MemberModifyRequestDto memberModifyRequestDto) {
        member.modify(memberModifyRequestDto);
        memberRepository.modify(member);
        return MemberInfoResponseDto.from(member);
    }

    public MemberInfoResponseDto modifyPassword(
            Member member,
            MemberModifyPasswordRequestDto memberModifyPasswordRequestDto) {
        String newPassword = passwordEncoder.encode(memberModifyPasswordRequestDto.getPassword());
        member.modifyPassword(newPassword);
        memberRepository.changePassword(member, newPassword);
        return MemberInfoResponseDto.from(member);
    }

    public void withdraw(Member member) {
        memberRepository.withdraw(member);
    }
}
