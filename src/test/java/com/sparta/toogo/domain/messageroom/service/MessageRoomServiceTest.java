package com.sparta.toogo.domain.messageroom.service;

import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.post.service.PostService;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageRoomServiceTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MessageRoomRepository messageRoomRepository;
    @Autowired
    MessageRoomService messageRoomService;
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;

    User user;
    User receiverUser;
    PostResponseDto createPost;

    @BeforeAll
    public void clearUp() {
        messageRoomRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        System.out.println("데이터 삭제 및 테스트 시작");
    }

    @Test
    @Order(1)
    @DisplayName("게시글 생성")
    public void addTest1() {
        // given
        Long category = 1L;

        user = new User(
                "test123@gmail.com",
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

        createPost = post;
    }


    @Test
    @Order(2)
    @DisplayName("쪽지방 생성")
    public void addTest2() {
        // given
        Long postId = this.createPost.getId();

        receiverUser = new User(
                "test456@naver.com",
                "fghvbn456",
                "닉네임2",
                UserRoleEnum.USER,
                null,
                "1"
        );
        userRepository.save(receiverUser);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                receiverUser.getNickname(),
                postId
        );

        // when
        MessageResponseDto messageRoom = messageRoomService.createRoom(messageRequestDto, receiverUser);

        // then
        assertNotNull(messageRoom);
    }
}