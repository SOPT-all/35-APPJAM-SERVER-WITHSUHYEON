package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.post.req.PostRequestDto;
import sopt.appjam.withsuhyeon.dto.post.res.PostDetailResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostListResponseDto;
import sopt.appjam.withsuhyeon.service.PostService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<Void> createPost(
            @UserId Long userId,
            @RequestBody PostRequestDto postRequestDto
    ) {
        postService.createPostItem(userId, postRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<PostListResponseDto> getPosts(
            @UserId Long userId,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "date", required = false, defaultValue = "all") String date
    ) {
        PostListResponseDto postListResponseDto = postService.getPostList(userId, region, date);
        return ResponseEntity.ok(postListResponseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(
       @UserId long userId,
       @PathVariable(name = "postId") Long postId
    ) {
        return ResponseEntity.ok(postService.getPostDetail(userId, postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @UserId long userId,
            @PathVariable(name = "postId") Long postId
    ) {
        postService.removePostItem(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
