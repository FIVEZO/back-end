package com.sparta.toogo.domain.post.controller;

import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.service.PostService;
import com.sparta.toogo.global.responsedto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/{category}")
    public ResponseEntity<ApiResponse> createPost(@PathVariable Long category,
                                  @RequestBody PostRequestDto requestDto) {
        ApiResponse response = postService.createPost(category, requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
//        public ResponseEntity<Map<String, Object>> createPost(@PathVariable Long category, @RequestBody PostRequestDto requestDto) {
//            ResponseEntity<Map<String, Object>> responseEntity = postService.createPost(category, requestDto);
//            return responseEntity;
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<PostResponseDto>> getPostsByCategory(@PathVariable Long category) {
        log.info("get 동작중!");
        List<PostResponseDto> response = postService.getPostsByCategory(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{category}/{postId}")
    public ResponseEntity<PostResponseDto> getDetailPost(@PathVariable Long category,
                                                         @PathVariable Long postId) {
        PostResponseDto response = postService.getDetailPost(category, postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
