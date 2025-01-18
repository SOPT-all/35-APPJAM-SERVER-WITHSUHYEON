package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            @RequestHeader("Authorization") Long blockerId,
            @RequestBody BlockNumberRequestDto blockNumberRequestDto
    ) {
        blockService.createBlockNumber(blockNumberRequestDto, blockerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/blocks")
    public ResponseEntity<Void> deleteBlockNumber(
            @RequestHeader("Authorization") Long blockerId,
            @RequestParam(value = "phoneNumber") String phoneNumber
    ) {
        blockService.removeBlockNumber(blockerId, phoneNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/blocks")
    public ResponseEntity<BlockNumberListResponseDto> getBlockNumbers(
            @RequestHeader("Authorization") Long userId
    ) {
        BlockNumberListResponseDto blockNumberListResponse = blockService.getBlockNumberList(userId);
        return ResponseEntity.ok(blockNumberListResponse);
    }
}
