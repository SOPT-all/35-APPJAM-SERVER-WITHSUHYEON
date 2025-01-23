package sopt.appjam.withsuhyeon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.dto.auth.req.SignInRequestDto;
import sopt.appjam.withsuhyeon.dto.auth.req.SignUpRequestDto;
import sopt.appjam.withsuhyeon.dto.auth.res.JwtTokensDto;
import sopt.appjam.withsuhyeon.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody @Valid SignUpRequestDto signUpRequestDto
    ) {
        authService.signUp(signUpRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtTokensDto> signIn(
            @RequestBody @Valid SignInRequestDto signInRequestDto
    ) {
        return ResponseEntity.ok(authService.login(signInRequestDto));
    }
}
