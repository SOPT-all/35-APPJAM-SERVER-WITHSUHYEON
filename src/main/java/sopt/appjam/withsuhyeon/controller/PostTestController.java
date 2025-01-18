package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.post.res.PostDetailResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostsResponse;
import sopt.appjam.withsuhyeon.service.PostTestService;

@RestController
@RequiredArgsConstructor
public class PostTestController {
    private final PostTestService postTestService;

    @GetMapping("/api/v1/test/posts")
    public ResponseEntity<PostsResponse> getAllPosts(
            @UserId Long userId
    ) {
        return ResponseEntity.ok(postTestService.getAllPost());
    }

    @GetMapping("/api/v1/test/posts/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(
            @UserId Long userId,
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postTestService.getPostDetail(userId, postId));
    }
}
