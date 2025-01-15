package com.example.b101.controller;

import com.example.b101.domain.User;
import com.example.b101.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if(userService.findByNicKname(user.getNickname()).isPresent()) {
            return new ResponseEntity<>("이미 사용중인 닉네임입니다.", HttpStatus.CONFLICT);
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.CONFLICT);
        }

        User newUser = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
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
                return ResponseEntity.status(HttpStatus.OK).body(nickname);
            }else{
                return ResponseEntity.badRequest().body("비밀번호가 틀렸습니다.");
            }
        }

        return ResponseEntity.badRequest().body("가입되지 않은 닉네임입니다.");
    }


}
