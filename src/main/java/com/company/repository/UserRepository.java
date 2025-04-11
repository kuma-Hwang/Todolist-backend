
package com.company.repository;

import com.company.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 로그인 시 사용할 userId로 사용자 조회
    Optional<User> findByUserId(String userId);

    // 회원가입 시 userId 중복 체크
    boolean existsByUserId(String userId);

    // 이메일 중복 확인 (선택)
    boolean existsByEmail(String email);
}
