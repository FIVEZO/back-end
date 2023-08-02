package com.sparta.toogo.domain.mypage.service;

import com.sparta.toogo.domain.mypage.dto.MsgResponseDto;
import com.sparta.toogo.domain.mypage.dto.MyPageDto;
import com.sparta.toogo.domain.mypage.dto.MyPageRequestDto;
import com.sparta.toogo.domain.mypage.dto.MyPageResponseDto;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.scrap.entity.Scrap;
import com.sparta.toogo.domain.scrap.repository.ScrapRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.enums.ErrorCode;
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

import static com.sparta.toogo.global.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<?> getMyPage(User user) {
        return ResponseUtil.ok(new MyPageDto());
    }

    public MsgResponseDto deleteUser(Long loginId, User user) {

        if (user.getId().equals(loginId)) {
            user.Delete();
            userRepository.delete(user);
        } else {
            throw new MyPageException(ErrorCode.ID_NOT_FOUND);
        }
        return MsgResponseDto.success("그동안 서비스를 이용해 주셔서 감사합니다.");
    }

    @Transactional
    public MyPageResponseDto updateUser(Long loginId, MyPageRequestDto requestDto, User user) {
        if (!user.getId().equals(loginId)) {
            throw new MyPageException(NO_AUTHORITY_TO_DATA);
        }

        // 닉네임 수정
        String nickname = user.getNickname();
        if (requestDto.getNickname() != null) {
            if (requestDto.getNickname().equals(String.valueOf(userRepository.findByNickname(requestDto.getNickname())))) {
                throw new MyPageException(DUPLICATE_NICKNAME);
            }
        }

        // 비밀번호 수정
        String newPassword = user.getPassword();
        if (requestDto.getPassword() != null) { // 비밀번호를 변경하기 위해 기존의 비밀번호의 값을 입력했을 경우
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new MyPageException(INCORRECT_PASSWORD);
            }
            newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        }
        user.modifyUser(user, requestDto, newPassword, nickname);
        userRepository.save(user);
        return new MyPageResponseDto(user);
    }

    public List<PostResponseDto> getMyScrap(User user) {
        Pageable pageable = PageRequest.of(0, 9, Sort.by("createdAt"));
        Page<Scrap> scraps = scrapRepository.findAllByUser(pageable, user);
        List<PostResponseDto> scrapList = new ArrayList<>();

        for (Scrap scrap : scraps) {
            Post post = scrap.getPost();
            long scrapPostSum = post.getScrapList().size();

            scrapList.add(new PostResponseDto(post, scrapPostSum));
        }
        return scrapList;
    }
}
