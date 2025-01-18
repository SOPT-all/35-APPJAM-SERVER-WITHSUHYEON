package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.post.req.PostRequestDto;
import sopt.appjam.withsuhyeon.dto.post.res.PostListResponseDto;
import sopt.appjam.withsuhyeon.service.BlockService;
import sopt.appjam.withsuhyeon.service.PostService;

import java.time.LocalDate;

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
}
