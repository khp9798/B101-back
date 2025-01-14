package com.example.b101.serviceImpl;

import com.example.b101.domain.User;
import com.example.b101.repository.UserRepository;
import com.example.b101.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
       this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public boolean isLogin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() && user.get().getPassword().equals(password);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    @Transactional
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new IllegalArgumentException("검색되지 않는 이메일입니다. : "+email));
    }


}
