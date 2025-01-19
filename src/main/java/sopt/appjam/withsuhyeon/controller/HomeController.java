package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.home.res.HomePostsResponse;
import sopt.appjam.withsuhyeon.service.HomeService;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/api/v1/home")
    public ResponseEntity<HomePostsResponse> getRandomPosts(
            @UserId long userId
    ) {
        HomePostsResponse homePostsResponse = homeService.getRandomPosts(userId);
        return ResponseEntity.ok(homePostsResponse);

    }
}
