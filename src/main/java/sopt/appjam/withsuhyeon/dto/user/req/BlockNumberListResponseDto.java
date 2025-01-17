package sopt.appjam.withsuhyeon.dto.user.req;

import java.util.List;

public record BlockNumberListResponseDto(
        String nickname,
        List<String> phoneNumbers
) {
}