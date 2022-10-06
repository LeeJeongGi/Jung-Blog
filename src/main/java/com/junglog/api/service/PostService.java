package com.junglog.api.service;

import com.junglog.api.domain.Post;
import com.junglog.api.repository.PostRepository;
import com.junglog.api.request.PostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostRequest request) {
        // repository.save(request)
        // PostRequest -> post 로 변환
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepository.save(post);
    }

    public Post get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;
    }

    public List<Post> getList() {
        return postRepository.findAll();
    }
}
