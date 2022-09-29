package com.junglog.api.controller;

import com.junglog.api.request.PostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    @PostMapping("/posts")
    public String post(@RequestBody PostRequest params) {

        log.info("params={}", params.toString());

        return "Hello World";
    }
}
