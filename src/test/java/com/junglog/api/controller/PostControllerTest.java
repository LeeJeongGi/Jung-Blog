package com.junglog.api.controller;

import com.junglog.api.domain.Post;
import com.junglog.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void delete() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다")
    void test() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" :  \"제목입니다.\", \"content\" : \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다")
    void test2() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" :  null, \"content\" : \"내용입니다.\"}")
                )
                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Hello World"))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validations.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다")
    void test3() throws Exception {
        // expected
        // when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" :  \"제목입니다.\", \"content\" : \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }
}