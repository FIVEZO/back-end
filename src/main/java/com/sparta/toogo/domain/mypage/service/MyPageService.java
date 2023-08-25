package com.sparta.toogo.domain.mypage.service;

import com.sparta.toogo.domain.mypage.dto.*;
import com.sparta.toogo.domain.mypage.entity.MyPage;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.mypage.repository.MyPageRepository;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.scrap.entity.Scrap;
import com.sparta.toogo.domain.scrap.repository.ScrapRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
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
import java.util.stream.Collectors;

import static com.sparta.toogo.global.enums.ErrorCode.*;
import static com.sparta.toogo.global.enums.SuccessCode.PASSWORD_CHANGE_SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final MyPageRepository myPageRepository;

    // 내가 작성한 게시글 조회
    public List<MyPageDto> getMyPagePost(User user) {

        List<Post> userPosts = postRepository.findByUser(user);
        return userPosts.stream()
                .map(MyPageDto::new)
                .collect(Collectors.toList());
    }

    public MsgResponseDto deleteUser(User user) {
        userRepository.delete(user);
        return MsgResponseDto.success("그동안 서비스를 이용해 주셔서 감사합니다.");
    }

    // 내가 스크랩한 게시물 조회
    public List<MyPageDto> getMyScrap(User user, int pageNum) {
        Long myScrapCount = scrapRepository.countByUser(user);

        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Scrap> scraps = scrapRepository.findAllByUser(pageable, user);
        List<MyPageDto> scrapList = new ArrayList<>();

        for (Scrap scrap : scraps) {
            Post post = scrap.getPost();

            scrapList.add(new MyPageDto(post, myScrapCount));
        }
        return scrapList;
    }

    public MyPagePatchResponseDto myPageUpdate(MyPageRequestDto requestDto, User user) {
        // 이모티콘
        MyPage myPage = user.getMyPage();
        String newEmoticon = requestDto.getNewEmoticon();
        user.updateEmoticon(newEmoticon);

        // 소개
        String newIntroduction = requestDto.getNewIntroduction();
        myPage.update(newIntroduction);
        myPageRepository.save(myPage);

        // 닉네임 수정
        String newNickname = requestDto.getNewNickname();
        if (newNickname != null) {
            User existUser = userRepository.findByNickname(newNickname);
            if (existUser != null && newNickname.equals(existUser.getNickname())) {
                throw new MyPageException(DUPLICATE_NICKNAME);
            }
            user.updateNickname(newNickname);
            userRepository.save(user);
            return new MyPagePatchResponseDto(newNickname, newIntroduction, newEmoticon);
        }
        String userNickname = user.getNickname();
        return new MyPagePatchResponseDto(userNickname, newIntroduction, newEmoticon);
    }

    public MyPageResponseDto passwordUpdate(MyPageRequestDto requestDto, User user) {
        // 비밀번호 수정
        String password = requestDto.getPassword();
        String newPassword = requestDto.getNewPassword();
        if (password == null) { // 비밀번호를 변경하기 위해 기존의 비밀번호의 값을 입력했을 경우
            throw new MyPageException(PASSWORD_REQUIRED);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MyPageException(PASSWORD_MISMATCH);
        }
        String encodingNewPassword = passwordEncoder.encode(newPassword);
        user.updatePw(encodingNewPassword);
        userRepository.save(user);
        return new MyPageResponseDto(PASSWORD_CHANGE_SUCCESS);
    }
}