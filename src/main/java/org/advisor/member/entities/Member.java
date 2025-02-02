package org.advisor.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="member")
public class Member implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 65)
    private String password;

    @Column(length = 40, nullable = false)
    private String nickName;

    @Column(nullable = false)
    private LocalDate birthDt; // 생년월일

    @Column(length = 10, nullable = false)
    private String zipCode;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100)
    private String addressSub;

    private boolean requiredTerms1;

    private boolean requiredTerms2;

    private boolean requiredTerms3;

    @Column(length = 50)
    private String optionalTerms; // 선택 약관

    @Column(length = 65)
    private String socialToken; // 소셜 로그인 기본 ID

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authorities> authorities;

    // 비밀번호 변경 일시
    private LocalDateTime credentialChangedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // 사용자 이름으로 email 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 확인 로직 추가 필요
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 확인 로직 추가 필요
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 확인 로직 추가 필요
    }


}