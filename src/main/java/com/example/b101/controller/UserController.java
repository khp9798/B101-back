package com.example.b101.controller;

import com.example.b101.domain.User;
import com.example.b101.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            if(userService.isEmailExists(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이메일입니다.");
            }
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패 : "+e.getMessage());
        }

    };

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        boolean isLogin = userService.isLogin(email, password);

        if(isLogin) {
            return ResponseEntity.ok("로그인 성공");
        }else{
            return ResponseEntity.status(401).body("로그인 실패 : 이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }

}
