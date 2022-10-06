package com.junglog.api.service;

import com.junglog.api.domain.Post;
import com.junglog.api.repository.PostRepository;
import com.junglog.api.request.PostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void createTest() {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postRequest);
        Post post = postRepository.findAll().get(0);

        //then
        assertEquals(1L, postRepository.count());
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 한개 조회")
    void test2() {
        //given
        Post requestPost = Post.builder()
                .title("title")
                .content("content")
                .build();

        postRepository.save(requestPost);

        //when
        Post post = postService.get(requestPost.getId());

        //then
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("title", post.getTitle());
        assertEquals("content", post.getContent());
    }

    @Test
    @DisplayName("글 여러건 조회")
    void test3() {
        //given
        postRepository.saveAll(List.of(
                Post.builder()
                    .title("title1")
                    .content("content1")
                    .build(),
                Post.builder()
                    .title("title2")
                    .content("content2")
                    .build()
        ));

        //when
        List<Post> posts = postService.getList();

        //then
        assertEquals(2, posts.size());
    }
}