package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.user.res.UserInfoResponse;
import sopt.appjam.withsuhyeon.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserInfoResponse> getMyPage(
            @UserId long userId
    ) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfoResponse);
    }
}
