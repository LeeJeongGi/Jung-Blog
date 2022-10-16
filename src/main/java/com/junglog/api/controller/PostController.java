package com.junglog.api.controller;

import com.junglog.api.domain.Post;
import com.junglog.api.request.PostEdit;
import com.junglog.api.request.PostRequest;
import com.junglog.api.request.PostSearch;
import com.junglog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostRequest request) throws Exception {
        postService.write(request);
    }

    /**
     * /posts -> 글 전체 조회
     * /posts/{postsId} -> 한 개만 조회
     */
    @GetMapping("/posts")
    public List<Post> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @GetMapping("/posts/{postsId}")
    public Post get(@PathVariable(name = "postsId") Long id) {
        Post post = postService.get(id);

        return post;
    }

    @PatchMapping("/posts/{postId}")
    public void update(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }
}
