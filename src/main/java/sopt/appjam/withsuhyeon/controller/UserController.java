package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.dto.auth.req.SignupRequestDto;
import sopt.appjam.withsuhyeon.dto.user.req.BlockNumberRequestDto;
import sopt.appjam.withsuhyeon.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mypage")
public class UserController {
    private final UserService userService;

    @PostMapping("/blocks")
    public ResponseEntity<Void> createBlockNumber(
            @RequestHeader("Authorization") Long blockerId,
            @RequestBody BlockNumberRequestDto blockNumberRequestDto
    ) {
        userService.createBlockNumber(blockNumberRequestDto, blockerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/blocks")
    public ResponseEntity<Void> deleteBlockNumber(
            @RequestHeader("Authorization") Long blockerId,
            @RequestParam(value = "phoneNumber") String phoneNumber
    ) {
        userService.removeBlockNumber(blockerId, phoneNumber);
        return ResponseEntity.noContent().build();
    }
}
