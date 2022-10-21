package com.junglog.api.service;

import com.junglog.api.domain.Post;
import com.junglog.api.exception.PostNotFound;
import com.junglog.api.repository.PostRepository;
import com.junglog.api.request.PostEdit;
import com.junglog.api.request.PostRequest;
import com.junglog.api.request.PostSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("게시글 작성 테스트 케이스")
    void createPost() {
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
    @DisplayName("특정 게시글 조회하는 테스트 케이스")
    void readPost() {
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
    @DisplayName("존재하지 않는 게시글 조회시 에러 발생 테스트 케이스")
    void readPostError() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        postRepository.save(post);

        //expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

    }

    @Test
    @DisplayName("게시글 조회하는 테스트 케이스")
    void readPosts() {
        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("이정기 제목 - " + i)
                        .content("이정기 내용 - " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0, 10, Direction.DESC, "id");
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        //when
        List<Post> posts = postService.getList(postSearch);

        //then
        assertEquals(10, posts.size());

        assertEquals("이정기 제목 - 19", posts.get(0).getTitle());
        assertEquals("이정기 내용 - 19", posts.get(0).getContent());
    }

    @Test
    @DisplayName("게시글 제목 수정하는 테스트 케이스")
    void updateTitlePost() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("contest")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("update title")
                .content(null)
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 하지 않습니다. id = " + post.getId()));

        assertEquals("update title", changePost.getTitle());
        assertEquals("contest", changePost.getContent());
    }

    @Test
    @DisplayName("게시글 내용 수정하는 테스트 케이스")
    void updateContentPost() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("contest")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("update contest")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 하지 않습니다. id = " + post.getId()));

        assertEquals("title", changePost.getTitle());
        assertEquals("update contest", changePost.getContent());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 내용 수정시 에러 발생하는 테스트 케이스")
    void notFoundPostUpdateError() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("contest")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("update contest")
                .build();

        //expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1, postEdit);
        });
    }

    @Test
    @DisplayName("게시글 삭제하는 테스트 케이스")
    void deletePost() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("존재 하지 않는 게시글 조회 시 발생하는 에러 테스트 케이스")
    void notFoundPostReadError() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        postRepository.save(post);

        //expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }
}