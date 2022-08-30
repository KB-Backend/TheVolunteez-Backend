package com.example.TheVolunteez.security;

import com.example.TheVolunteez.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        return memberRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없음")
                );
    }
}
