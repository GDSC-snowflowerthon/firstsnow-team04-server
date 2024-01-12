package com.dev.firstsnow.repository;

import com.dev.firstsnow.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    // 특정 키워드가 닉네임에 포함된 사용자 목록을 반환하는 메서드
    @Query("SELECT u FROM User u WHERE u.nickname LIKE %:keyword%")
    Page<User> findByNicknameContaining(@Param("keyword") String keyword, Pageable pageable);
}
