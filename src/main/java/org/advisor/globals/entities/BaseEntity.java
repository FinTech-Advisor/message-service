package org.advisor.globals.entities;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 등록일시
}
