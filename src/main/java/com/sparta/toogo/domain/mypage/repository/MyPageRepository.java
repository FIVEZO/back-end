package com.sparta.toogo.domain.mypage.repository;

import com.sparta.toogo.domain.mypage.entity.MyPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyPage, Long> {
    MyPage findByUserId(Long userId);

}