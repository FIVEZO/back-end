package com.sparta.toogo.domain.home.service;

import com.sparta.toogo.domain.home.dto.HomeResponseDto;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostRepository postRepository;

    public List<HomeResponseDto> getHome() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<Post> postList = postRepository.findAll(pageable);

        return postList.stream()
                .map(HomeResponseDto::new)
                .toList();
    }
}
