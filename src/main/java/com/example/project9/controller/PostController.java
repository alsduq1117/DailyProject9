package com.example.project9.controller;

import com.example.project9.request.PostCreate;
import com.example.project9.request.PostEdit;
import com.example.project9.request.PostSearch;
import com.example.project9.response.PagingResponse;
import com.example.project9.response.PostResponse;
import com.example.project9.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;


    @PostMapping("")
    public PostResponse write(@RequestBody @Valid PostCreate postCreate){
        return postService.write(postCreate);
    }

    @GetMapping("/{postId}")
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }

    @GetMapping("")
    public PagingResponse<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PutMapping("/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit){
        return postService.edit(postId, postEdit);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId){
        postService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
