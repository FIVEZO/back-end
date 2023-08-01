package com.sparta.toogo.domain.post.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.entity.QPost;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.toogo.domain.post.entity.QPost.post;
import static com.sparta.toogo.global.enums.ErrorCode.NO_AUTHORITY_TO_DATA;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final JPAQueryFactory queryFactory;

    public PostResponseDto createPost(Long category, PostRequestDto requestDto, User user) {
        Post post = new Post(category, requestDto, user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostsByCategory(Long category) {
        log.info("get 동작중!");
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        System.out.println("categoryEnum = " + categoryEnum);
        List<Post> posts = postRepository.findAllByCategory(categoryEnum); // ASIA : Long 1L
        log.info("get 동작중중!");
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostResponseDto getDetailPost(Long category, Long postId) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        Post post = findPost(categoryEnum, postId);
        return new PostResponseDto(post);
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

        queryFactory.delete(post).where(post.id.eq(postId)).execute();

        return SuccessCode.POST_DELETE_SUCCESS;
    }

    private Post confirmPost(Category.PostCategory categoryEnum, Long postId, User user) {
        Post post = findPost(categoryEnum, postId);
        if(!post.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(NO_AUTHORITY_TO_DATA.getHttpStatus(), NO_AUTHORITY_TO_DATA.getDetail());
        }
        return post;
    }

    private Post findPost(Category.PostCategory category, Long postId) {
        return postRepository.findByCategoryAndId(category, postId)
                .orElseThrow(() -> new ResponseStatusException(ErrorCode.NOT_FOUND_DATA.getHttpStatus(), ErrorCode.NOT_FOUND_DATA.getDetail()));
    }


}
// 대륙(6), 국가(18)

//public String countryUrl(String country) {
//ArrayList koreaUrl = new ArrayList();
//koreaUrl.add("한국이미지1")
//koreaUrl.add("한국이미지2")
//switch(country)
//	case "한국"
//		url = koreaUrl
//}
// @Column(Id , country, url)
//select url from countryUrl where country = "한국"