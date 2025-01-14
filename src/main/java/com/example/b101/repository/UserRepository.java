package com.example.b101.repository;

import com.example.b101.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Long: @Id 필드의 데이터 타입
public interface UserRepository extends JpaRepository<User,Long> {

    // 이메일로 사용자 검색
    //Optional은 값이 있거나 없음을 나타냅니다.  값이 없으면 null 대신 Optional.empty()를 반환합니다.
    Optional<User> findByEmail(String email);

    //Spring data JPA에서 제공하는 이 email을 가지고 있는 데이터가 있는지 존재여부 반환해주는 메서드
    boolean existsByEmail(String email);

}
