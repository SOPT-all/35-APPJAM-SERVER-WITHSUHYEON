package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.user.res.UserIdResponse;
import sopt.appjam.withsuhyeon.dto.user.res.UserInfoResponse;
import sopt.appjam.withsuhyeon.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/mypage")
    public ResponseEntity<UserInfoResponse> getMyPage(
            @UserId long userId
    ) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfoResponse);
    }

    @GetMapping("/user-id")
    public ResponseEntity<UserIdResponse> getUserId(
            @UserId long userId
    ) {
        return ResponseEntity.ok(new UserIdResponse(userId));
    }
}
