package com.junglog.api.controller;

import com.junglog.api.repository.UserRepository;
import com.junglog.api.request.Login;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public void login(@RequestBody Login login) {
        // json 아이디/비밀번호
        log.info(">>>>>>login={}", login);

        // DB에서 조회



        // 토큰 조회

    }
}
