package org.advisor.member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.advisor.member.constants.Authority;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Authorities {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Authority authority;
}