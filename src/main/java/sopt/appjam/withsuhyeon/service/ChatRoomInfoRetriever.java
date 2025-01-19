package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatRoomInfo;
import sopt.appjam.withsuhyeon.exception.ChatRoomInfoErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.ChatRoomInfoRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomInfoRetriever {
    private final ChatRoomInfoRepository chatRoomInfoRepository;

    public ChatRoomInfo findByRoomNumber(UUID roomNumber) {
        return chatRoomInfoRepository.findByRoomNumber(roomNumber)
                .orElseThrow(()-> BaseException.type(ChatRoomInfoErrorCode.CHAT_ROOM_INFO_NOT_FOUND));
    }
}
