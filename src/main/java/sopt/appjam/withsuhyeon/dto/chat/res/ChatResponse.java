package sopt.appjam.withsuhyeon.dto.chat.res;

import java.time.LocalDateTime;

public record ChatResponse(
        String content,
        LocalDateTime timestamp
) {
}
