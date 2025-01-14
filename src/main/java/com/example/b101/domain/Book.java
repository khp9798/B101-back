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
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="BOOK_ID")
    private Long id;

    @Column(name = "BOOK_TITLE", nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private int viewCnt = 0;

    @Column(nullable = false)
    private int likeCnt = 0;

    @CreatedDate
    private LocalDateTime createdAt;
}
