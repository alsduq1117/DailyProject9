package com.example.project9.repository;

import com.example.project9.domain.Post;
import com.example.project9.request.PostSearch;
import org.springframework.data.domain.Page;

public interface PostCustomRepository {

    Page<Post> getList(PostSearch postSearch);
}
