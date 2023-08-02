package com.sparta.toogo.domain.kakao.repository;

import com.sparta.toogo.domain.kakao.entity.Kakao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoRepository extends JpaRepository<Kakao, Long> {
    Optional<Kakao> findById(Long id);
}
