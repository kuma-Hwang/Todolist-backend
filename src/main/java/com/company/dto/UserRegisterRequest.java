package com.company.dto;

import com.company.entity.Gender;
import com.company.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String userId;         // 로그인용 ID
    private String username;       // 사용자 이름
    private String password;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private boolean agreedToTerms;
    private Role role;
}
