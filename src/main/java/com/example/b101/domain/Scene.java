package com.example.b101.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Scene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // SCENE_ID

    @ManyToOne(fetch = FetchType.LAZY) // BOOK 테이블과 N:1 관계
    @JoinColumn(name = "BOOK_ID", nullable = true) // 초기에는 NULL 가능
    private Book book;

    @Column(nullable = false)
    private int sceneOrder; // SCENE_ORDER: 장면 순서

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt; // 사용자가 입력한 텍스트 (텍스트 길이 제한 없음)

    @Column(nullable = false, length = 2083)
    private String imageUrl; // 생성된 이미지 URL (최대 URL 길이)

    @CreatedDate
    private LocalDateTime createdAt; // 생성 시간
}
