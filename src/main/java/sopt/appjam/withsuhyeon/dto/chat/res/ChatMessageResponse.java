package sopt.appjam.withsuhyeon.dto.chat.res;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        String type,
        String content,
        LocalDateTime timestamp,
        Boolean isRead
) {
}
