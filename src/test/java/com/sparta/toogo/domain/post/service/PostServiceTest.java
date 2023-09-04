package com.sparta.toogo.domain.post.service;

import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("게시글 생성")
    void addTest() {
        // given
        Long category = 1L;

        User user = new User(
                "jungin9634@gmail.com",
                "asdzxc123",
                "닉네임1",
                UserRoleEnum.USER,
                null,
                "1"
        );
        userRepository.save(user);

        PostRequestDto postRequestDto = new PostRequestDto(
                "게시글제목",
                "게시글내용",
                "게시글나라",
                111,
                222,
                "11",
                "2"
        );

        // when
        PostResponseDto post = postService.createPost(category, postRequestDto, user);

        // then
        assertNotNull(post);
    }
}