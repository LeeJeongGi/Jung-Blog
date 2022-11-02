package com.junglog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junglog.api.domain.Post;
import com.junglog.api.repository.PostRepository;
import com.junglog.api.request.PostEdit;
import com.junglog.api.request.PostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void deleteAfter() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("필수 값 채워지지 않았을 때 에러 발생하는지 테스트 케이스")
    void create_error_test() throws Exception {
        //given
        PostRequest request = PostRequest.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validations.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 테스트 케이스")
    void create_test() throws Exception {
        //given
        PostRequest request = PostRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("특정 게시글 조회하는 테스트 케이스")
    void readPost() throws Exception {
        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        postRepository.save(post);

        //expected = when + then
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 목록 조회 테스트 케이스")
    void readPosts() throws Exception {
        //given
        List<Post> posts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목 입니다 - " + i)
                        .content("내용 입니다 - " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(posts);

        //expected = when + then
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("제목 입니다 - 19"))
                .andExpect(jsonPath("$[0].content").value("내용 입니다 - 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다. - pageDefault 설정으로 인해 0 -> 1로 처리")
    void readPostsPageTest() throws Exception {
        //given
        List<Post> posts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목 입니다 - " + i)
                        .content("내용 입니다 - " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(posts);

        //expected = when + then
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("제목 입니다 - 19"))
                .andExpect(jsonPath("$[0].content").value("내용 입니다 - 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 제목 수정 테스트 케이스")
    void updatePost() throws Exception {
        //given
        Post post = Post.builder()
                .title("title")
                .content("contest")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("update Title")
                .content("contest")
                .build();

        //expected = when + then
        mockMvc.perform(patch("/posts/{postId}", post.getId()) //PATCH /posts/{postId}
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 삭제 시 에러 발생 테스트 케이스")
    void deletePostErrorTest() throws Exception {
        // expected
        mockMvc.perform(delete("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정 시 에러 발생 테스트 케이스")
    void updatePostErrorTest() throws Exception {
        //given
        PostEdit postEdit = PostEdit.builder()
                .title("update Title")
                .content("contest")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DisplayName("게시글 작성 시 바보는 포함될수 없다. -> 임시로 이런 요청이 있을 때 어떤 방법으로 에러를 처리해야되는지 샘플 테스트")
    void requestPostError() throws Exception {
        //given
        PostRequest request = PostRequest.builder()
                .title("나는 바보입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}