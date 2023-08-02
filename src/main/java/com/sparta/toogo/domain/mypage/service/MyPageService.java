package com.sparta.toogo.domain.mypage.service;

import com.sparta.toogo.domain.mypage.dto.MsgResponseDto;
import com.sparta.toogo.domain.mypage.dto.MyPageDto;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;

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
