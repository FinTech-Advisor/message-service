package org.advisor.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.advisor.member.constants.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member implements UserDetails {

    private Long seq;
    private String email;

    private List<Authority> _authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return _authorities == null || _authorities.isEmpty()
                ? List.of()
                : _authorities.stream().map(s -> new SimpleGrantedAuthority(s.name())).toList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }


}
