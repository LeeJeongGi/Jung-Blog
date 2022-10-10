package com.junglog.api.repository;

import com.junglog.api.domain.Post;
import com.junglog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
