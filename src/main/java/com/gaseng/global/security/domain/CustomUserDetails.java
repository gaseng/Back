package com.gaseng.global.security.domain;

import com.gaseng.global.security.dto.UserDetailsDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final UserDetailsDto member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        addRoles(authorities);
        return authorities;
    }

    private void addRoles(Collection<GrantedAuthority> authorities) {
        member.getMemRole()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);
    }

    @Override
    public String getPassword() {
        return member.getMemPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
