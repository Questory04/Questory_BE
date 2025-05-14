package com.ssafy.questory.config.jwt;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.SecurityMember;
import com.ssafy.questory.repository.SecurityMemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityMemberRepository memberRepository;

    public CustomUserDetailsService(SecurityMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return SecurityMember.of(member);
    }
}
