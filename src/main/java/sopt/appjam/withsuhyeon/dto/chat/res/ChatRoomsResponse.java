package sopt.appjam.withsuhyeon.dto.chat.res;

import java.util.List;

public record ChatRoomsResponse(
        List<ChatRoomResponse> chatRooms
) {
}
