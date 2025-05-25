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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public int getMemberExp(String memberEmail){
        return memberRepository.getMemberExp(memberEmail);
    }

    public void setMemberExp(String memberEmail, int experiencePoints) {
        memberRepository.addMemberExp(memberEmail, experiencePoints);
    }
}
