package sopt.appjam.withsuhyeon.dto.block.res;

import java.util.List;

public record BlockNumberListResponseDto(
        String nickname,
        List<String> phoneNumbers
) {
}