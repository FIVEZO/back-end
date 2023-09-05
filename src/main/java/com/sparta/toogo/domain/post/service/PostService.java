package com.sparta.toogo.domain.post.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.mypage.entity.MyPage;
import com.sparta.toogo.domain.mypage.repository.MyPageRepository;
import com.sparta.toogo.domain.notification.entity.Notification;
import com.sparta.toogo.domain.notification.repository.NotificationRepository;
import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.dto.PostResponseGetDto;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.exception.PostException;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.scrap.repository.ScrapRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sparta.toogo.domain.post.entity.QPost.post;
import static com.sparta.toogo.domain.user.entity.QUser.user;
import static com.sparta.toogo.global.enums.ErrorCode.NO_AUTHORITY_TO_DATA;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final MessageRoomRepository messageRoomRepository;

    private final PostRepository postRepository;
    private final JPAQueryFactory queryFactory;
    private final ScrapRepository scrapRepository;
    private final MyPageRepository myPageRepository;
    private final NotificationRepository notificationRepository;

    public PostResponseDto createPost(Long category, PostRequestDto requestDto, User user) {

        String title = requestDto.getTitle();
        String contents = requestDto.getContents();

        if (title.isEmpty() || contents.isEmpty()) {
            throw new PostException(ErrorCode.EMPTY_TITLE_OR_CONTENTS);
        }

        Post post = new Post(category, requestDto, user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }


    // 전체 조회
    public Map<String, Object> getPostsByCategory(Long category, int pageNum) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        log.info("categoryEnum = " + categoryEnum);

        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postsPage = postRepository.findAllByCategory(categoryEnum, pageable);

        List<PostResponseGetDto> postResponseList = postsPage.getContent().stream()
                .map(PostResponseGetDto::new)
                .collect(Collectors.toList());

        int totalPages = postsPage.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("data", postResponseList);
        response.put("totalPages", totalPages);

        return response;
    }


    public Map<String, Object> getPostsByCategoryAndCountry(Long category, String country, int pageNum) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);

        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAllByCategoryAndCountry(categoryEnum, country, pageable);

        List<PostResponseGetDto> postResponseList = posts.getContent().stream()
                .map(PostResponseGetDto::new)
                .toList();

        int totalPages = posts.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("data", postResponseList);
        response.put("totalPages", totalPages);

        return response;
    }

    // 상세 조회
    public PostResponseDto getDetailPost(Long category, Long postId, Long userId) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        Post post = findPost(categoryEnum, postId);

        MyPage myPage = myPageRepository.findByUserId(post.getUser().getId());
        String newIntroduction = null; // 기본 값

        if (myPage != null) {
            newIntroduction = myPage.getIntroduction();
        }

        boolean isScrap = scrapRepository.findByPostIdAndUserId(postId, userId).isPresent();
        return new PostResponseDto(post, newIntroduction, isScrap);
    }


    public PostResponseDto updatePost(Long category, Long postId, User user, PostRequestDto requestDto) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        Post post = confirmPost(categoryEnum, postId, user);
        post.update(requestDto);
        post = postRepository.save(post);
        return new PostResponseDto(post);
    }

    public SuccessCode deletePost(Long category, Long postId, User user) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        confirmPost(categoryEnum, postId, user);

        List<Notification> notifications = notificationRepository.findByPostId(postId);
        notificationRepository.deleteAll(notifications);

        List<MessageRoom> messageRooms = messageRoomRepository.findByPostId(postId);
        messageRoomRepository.deleteAll(messageRooms);

        postRepository.deleteById(postId);

        //queryFactory.delete(post).where(post.id.eq(postId)).execute();

        return SuccessCode.POST_DELETE_SUCCESS;
    }

    private Post confirmPost(Category.PostCategory categoryEnum, Long postId, User user) {
        Post post = findPost(categoryEnum, postId);
        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostException(NO_AUTHORITY_TO_DATA);
        }
        return post;
    }

    private Post findPost(Category.PostCategory category, Long postId) {
        return postRepository.findByCategoryAndId(category, postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_DATA));
    }

    // 검색
    public List<PostResponseDto> searchPost(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20);

        String[] keywords = keyword.split(" "); // 입력된 키워드를 공백으로 분리

        List<BooleanExpression> expressions = new ArrayList<>();

        for (String key : keywords) {
            BooleanExpression titleOrContentsContain = post.title.containsIgnoreCase(key)
                    .or(post.contents.containsIgnoreCase(key));

            BooleanExpression titleContainsCountry = post.title.containsIgnoreCase("[" + key + "]")
                    .or(post.country.containsIgnoreCase(key));

            BooleanExpression titleOrContentsOrDateContain = titleOrContentsContain
                    .or(post.meetDate.containsIgnoreCase(key));

            BooleanExpression titleOrContentsOrDateNicknameContain = titleOrContentsContain
                    .or(user.nickname.containsIgnoreCase(key));

            expressions.add(titleOrContentsContain
                    .or(titleContainsCountry)
                    .or(titleOrContentsOrDateContain)
                    .or(titleOrContentsOrDateNicknameContain));

            log.info("key = " + key);
        }

        JPAQuery<Post> query = queryFactory.selectFrom(post)
                .where(expressions.toArray(new BooleanExpression[expressions.size()]));

        List<PostResponseDto> postList = query
                .orderBy(post.createdAt.desc()) // 최신 게시글 순으로 정렬
                .offset(pageable.getOffset()) // 현재 페이지의 시작 위치를 반환(가져올 데이터의 시작 위치)
                .limit(pageable.getPageSize()) // 쿼리로 가져올 최대 항목 수
                .fetch()
                .stream()
                .map(PostResponseDto::new)
                .toList();

        return postList;
    }
}