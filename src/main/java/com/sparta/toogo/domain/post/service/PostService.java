package com.sparta.toogo.domain.post.service;

import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.global.responsedto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public ApiResponse createPost(Long category, PostRequestDto requestDto) {
        Post post = new Post(category, requestDto);

        postRepository.save(post);

        PostResponseDto responseDto = new PostResponseDto(post);

        ApiResponse response = new ApiResponse(200, "게시글이 등록되었습니다.", responseDto);
        return response;
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

    private Post findPost(Category.PostCategory category, Long postId) {
        return postRepository.findByCategoryAndId(category, postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
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