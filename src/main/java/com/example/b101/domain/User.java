package com.example.b101.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor //기본생성자 생성
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id; // 사용자 고유 ID

    @Column(name = "USER_EMAIL", nullable = false, unique = true, length = 255)
    private String email; // 사용자 이메일

    @Column(name = "USER_PASSWORD", nullable = false, length = 255)
    private String password; // 사용자 비밀번호 (해싱된 값 저장)

    @Column(name = "USER_NICKNAME", nullable = false, length = 50)
    private String nickname; // 사용자 닉네임

    @Column(name = "CREATED_AT", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt; // 생성일

    @Column(name = "UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정일


}
