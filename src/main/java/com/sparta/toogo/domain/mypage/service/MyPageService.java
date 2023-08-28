package com.sparta.toogo.domain.mypage.service;

import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import com.sparta.toogo.domain.mypage.dto.*;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.scrap.entity.Scrap;
import com.sparta.toogo.domain.scrap.repository.ScrapRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.toogo.global.enums.ErrorCode.DUPLICATE_NICKNAME;
import static com.sparta.toogo.global.enums.ErrorCode.INCORRECT_PASSWORD;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageRoomService messageRoomService;

    // 마이페이지 조회
    public ApiResponse<?> getMyPage(User user) {
        Long myScrapCount = scrapRepository.countByUser(user);
        MyPageDto myPageDto = new MyPageDto(myScrapCount, user);
        return ResponseUtil.ok(myPageDto);
    }

    public MsgResponseDto deleteUser(User user) {
        userRepository.delete(user);
        return MsgResponseDto.success("그동안 서비스를 이용해 주셔서 감사합니다.");
    }

    // 내가 스크랩한 게시물 조회
    public List<MyPagePostDto> getMyScrap(User user, int pageNum) {
        Long myScrapCount = scrapRepository.countByUser(user);

        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Scrap> scraps = scrapRepository.findAllByUser(pageable, user);
        List<MyPagePostDto> scrapList = new ArrayList<>();

        for (Scrap scrap : scraps) {
            Post post = scrap.getPost();

            scrapList.add(new MyPagePostDto(post, myScrapCount));
        }
        return scrapList;
    }

    public MyPageResponseDto updateUser(MyPageRequestDto requestDto, User user) {
        // 닉네임 수정
        if (requestDto.getNickname() != null) {
            User existUser = userRepository.findByNickname(requestDto.getNickname());
            if (existUser != null && requestDto.getNickname().equals(existUser.getNickname())) {
                throw new MyPageException(DUPLICATE_NICKNAME);
            }
            String newNickname = requestDto.getNickname();
            user.updateNickname(newNickname);
            userRepository.save(user);
        }
        // 비밀번호 수정
        String newPassword = user.getPassword();
        if (requestDto.getPassword() != null) { // 비밀번호를 변경하기 위해 기존의 비밀번호의 값을 입력했을 경우
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new MyPageException(INCORRECT_PASSWORD);
            }
            newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        }
        user.updatePassword(user, newPassword);
        userRepository.save(user);
        return new MyPageResponseDto(user);
    }
}
