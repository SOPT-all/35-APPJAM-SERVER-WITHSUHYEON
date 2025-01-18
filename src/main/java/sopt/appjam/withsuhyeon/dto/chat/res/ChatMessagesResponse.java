package sopt.appjam.withsuhyeon.dto.chat.res;

import java.util.List;

public record ChatMessagesResponse(
        List<ChatMessageResponse> chatMessages
) {
}
