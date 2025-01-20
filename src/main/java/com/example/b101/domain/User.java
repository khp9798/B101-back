package com.example.b101.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(name = "USER_EMAIL", nullable = true) // 이메일은 null 허용
    private String email;

    @Column(name = "USER_PASSWORD", length = 255) // 비밀번호 필수
    private String password;

    @Column(name = "USER_NICKNAME", length = 50, nullable = true)
    private String nickname;

    @Column(name = "USERNAME", unique = true) // 고유 필드
    private String username;

    @Column(name = "ROLE", nullable = false)
    private String role = "ROLE_USER"; // 기본값 설정

    @Column(name = "CREATED_AT", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Author> books = new ArrayList<>();
}
