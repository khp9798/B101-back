package com.example.b101.service;

import com.example.b101.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    boolean isLogin(String username, String password);

    //회원가입
    User saveUser(User user);

    //이메일로 사용자 검색
    User findByEmail(String email);

    boolean isEmailExists(String email);


}
