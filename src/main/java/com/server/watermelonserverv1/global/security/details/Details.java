package com.server.watermelonserverv1.global.security.details;

import com.server.watermelonserverv1.domain.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
public class Details implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (getUser() != null) authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        else authorities.add(new SimpleGrantedAuthority("anonymous"));
        return authorities;
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return getUser() != null ? user.getEmail() : null; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Details(User user) { this.user = user; }
}
