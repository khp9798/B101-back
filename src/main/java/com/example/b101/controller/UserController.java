package com.example.b101.controller;

import com.example.b101.domain.User;
import com.example.b101.dto.SignUpDto;
import com.example.b101.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService userService) {
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder();
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDto signUpDto) {
        if(userService.findByNicKname(signUpDto.getNickname()).isPresent()) {
            return new ResponseEntity<>("이미 사용중인 닉네임입니다.", HttpStatus.CONFLICT);
        }

        if (userService.findByEmail(signUpDto.getEmail()).isPresent()) {
            return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.CONFLICT);
        }

        User newUser = new User();

        newUser.setEmail(signUpDto.getEmail());
        newUser.setPassword(encoder.encode(signUpDto.getPassword())); //비밀번호 암호화 후 저장
        newUser.setNickname(signUpDto.getNickname());

        userService.saveUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }


    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        if(userService.findByEmail(nickname).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("이미 사용중인 닉네임입니다.");
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String nickname, @RequestParam String password) {
        if(userService.findByNicKname(nickname).isPresent()) {
            User newUser = userService.findByNicKname(nickname).get();

            if(newUser.getPassword().equals(password)) {
                System.out.println("로그인 성공");
                return ResponseEntity.status(HttpStatus.OK).body(nickname);
            }else{
                return ResponseEntity.badRequest().body("비밀번호가 틀렸습니다.");
            }
        }

        return ResponseEntity.badRequest().body("가입되지 않은 닉네임입니다.");
    }



}
