package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.home.res.HomePostsResponse;
import sopt.appjam.withsuhyeon.dto.post.req.PostRequestDto;
import sopt.appjam.withsuhyeon.dto.post.res.PostDetailResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostListResponseDto;
import sopt.appjam.withsuhyeon.service.BlockService;
import sopt.appjam.withsuhyeon.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;
    private final BlockService blockService;

    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(
            @UserId Long userId,
            @RequestBody PostRequestDto postRequestDto
    ) {
        postService.createPostItem(userId, postRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getPosts(
            @UserId Long userId,
            @RequestParam(value = "region") String region,
            @RequestParam(value = "date", defaultValue = "all") String date
    ) {
        List<Long> blockerIds = blockService.getBlockerIds(userId);
        PostListResponseDto postListResponseDto = postService.getPostList(userId, blockerIds, region, date);
        return ResponseEntity.ok(postListResponseDto);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(
            @UserId long userId,
            @PathVariable(name = "postId") Long postId
    ) {
        return ResponseEntity.ok(postService.getPostDetail(userId, postId));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @UserId long userId,
            @PathVariable(name = "postId") Long postId
    ) {
        postService.removePostItem(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/home")
    public ResponseEntity<HomePostsResponse> getRandomPosts(
            @UserId long userId
    ) {
        List<Long> blockerIds = blockService.getBlockerIds(userId);
        HomePostsResponse homePostsResponse = postService.getRandomPosts(userId, blockerIds);
        return ResponseEntity.ok(homePostsResponse);

    }
}
