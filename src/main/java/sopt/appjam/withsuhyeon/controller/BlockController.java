package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.block.res.BlockNumberListResponseDto;
import sopt.appjam.withsuhyeon.dto.block.req.BlockNumberRequestDto;
import sopt.appjam.withsuhyeon.service.BlockService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mypage")
public class BlockController {
    private final BlockService blockService;

    @PostMapping("/blocks")
    public ResponseEntity<Void> createBlockNumber(
            @UserId Long blockerId,
            @RequestBody BlockNumberRequestDto blockNumberRequestDto
    ) {
        blockService.createBlockNumber(blockNumberRequestDto, blockerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/blocks")
    public ResponseEntity<BlockNumberListResponseDto> getBlockNumbers(
            @UserId Long blockerId
    ) {
        BlockNumberListResponseDto blockNumberListResponse = blockService.getBlockNumberList(blockerId);
        return ResponseEntity.ok(blockNumberListResponse);
    }

    @DeleteMapping("/blocks")
    public ResponseEntity<Void> deleteBlockNumber(
            @UserId Long blockerId,
            @RequestParam(value = "number") String phoneNumber
    ) {
        blockService.removeBlockNumber(blockerId, phoneNumber);
        return ResponseEntity.noContent().build();
    }
}
