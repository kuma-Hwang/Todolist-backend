package com.company.service;

import com.company.dto.UserRegisterRequest;
import com.company.entity.User;
import com.company.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 또는 @Bean으로 주입
    }

    public User registerUser(UserRegisterRequest request) {
        if (!request.isAgreedToTerms()) {
            throw new IllegalArgumentException("약관에 동의해야 가입할 수 있습니다.");
        }

        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        User user = new User();
        user.setUserId(request.getUserId());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        user.setAgreedToTerms(true);
        user.setRole(request.getRole());

        return userRepository.save(user);
    }
}
