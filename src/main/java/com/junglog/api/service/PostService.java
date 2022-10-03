package com.junglog.api.service;

import com.junglog.api.domain.Post;
import com.junglog.api.repository.PostRepository;
import com.junglog.api.request.PostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostRequest request) {
        // repository.save(request)
        // PostRequest -> post 로 변환
        Post post = new Post(request.getTitle(), request.getContent());

        postRepository.save(post);
    }
}
