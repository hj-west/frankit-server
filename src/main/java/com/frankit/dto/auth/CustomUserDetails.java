package com.frankit.dto.auth;

import com.frankit.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private String password;
    private Long uuid;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 사용자의 권한 정보를 반환
     * @return GrantedAuthority 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Member 엔티티를 기반으로 CustomUserDetails 생성자 초기화
     * @param member Member
     */
    public CustomUserDetails(Member member) {
        this.uuid = member.getUuid();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.authorities = member.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
