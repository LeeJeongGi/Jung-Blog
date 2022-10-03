package com.junglog.api.controller;

import com.junglog.api.request.PostRequest;
import com.junglog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    private final PostService postService;

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostRequest request) throws Exception {
        postService.write(request);

        return Map.of();
    }
}
