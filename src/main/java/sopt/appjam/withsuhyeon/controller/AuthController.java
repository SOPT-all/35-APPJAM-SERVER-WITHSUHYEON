package sopt.appjam.withsuhyeon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.dto.auth.req.SignupRequestDto;
import sopt.appjam.withsuhyeon.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody SignupRequestDto signupRequestDto
    ) {
        userService.createUser(signupRequestDto);
        return ResponseEntity.ok().build();
    }
}
