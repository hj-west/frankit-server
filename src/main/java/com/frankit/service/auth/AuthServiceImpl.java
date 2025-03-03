package com.frankit.service.auth;

import com.frankit.config.auth.JwtTokenProvider;
import com.frankit.entity.Member;
import com.frankit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(String email, String password){
        // 존재하는 회원인지 먼저 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        // 패스워드 틀림 여부 판단
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("잘못된 로그인 정보 : " + email);
        }

        List<String> roles = member.getRoles();

        // 모든 검증이 끝나면 토큰 발급
        return jwtTokenProvider.generateToken(member.getUuid(), member.getEmail(), roles);
    }
}
