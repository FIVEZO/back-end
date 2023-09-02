package com.sparta.toogo.domain.mypage.service;

import com.sparta.toogo.domain.mypage.dto.MyPagePatchResponseDto;
import com.sparta.toogo.domain.mypage.dto.MyPageRequestDto;
import com.sparta.toogo.domain.mypage.entity.MyPage;
import com.sparta.toogo.domain.mypage.repository.MyPageRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MyPageServiceTest {

    @InjectMocks
    private MyPageService myPageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MyPageRepository myPageRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMyPageUpdate() {
        MyPageRequestDto requestDto = new MyPageRequestDto(
                "test",
                "test",
                null,
                null,
                "2"
        );

        User user = new User(
                "test@email.com",
                passwordEncoder.encode("1111aaaa"),
                "11",
                UserRoleEnum.USER,
                null,
                "1"
        );
        user.setId(1000L);

        MyPage myPage = new MyPage();
        myPage.setUser(user);
        user.setMyPage(myPage);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(myPageRepository.save(any(MyPage.class))).thenReturn(myPage);

        MyPagePatchResponseDto responseDto = myPageService.myPageUpdate(requestDto, user);

        assertNotNull(responseDto);
        assertEquals("test", responseDto.getNewNickname());
        assertEquals("test", responseDto.getNewIntroduction());
        assertEquals("2", responseDto.getNewEmoticon());
    }
}