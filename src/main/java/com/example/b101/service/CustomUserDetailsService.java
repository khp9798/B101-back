package com.example.b101.service;

import com.example.b101.domain.User;
import com.example.b101.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        // Optional을 사용하여 사용자 조회
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + nickname));

        // Spring Security의 UserDetails로 반환
        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),  // username 필드로 nickname 사용
                user.getPassword(),
                Collections.emptyList() // 권한 정보 추가 필요 시 수정
        );
    }
}
