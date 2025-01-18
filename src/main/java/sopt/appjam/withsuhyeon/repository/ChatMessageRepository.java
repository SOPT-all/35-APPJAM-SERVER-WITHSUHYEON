package sopt.appjam.withsuhyeon.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sopt.appjam.withsuhyeon.domain.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatRoomId(String ObjectId); // chatRoomId 를 가지고 특정 chatRoom 의 모든 메시지를 가져옵니다.
    Long countByChatRoomIdAndIsRead(String chatRoomId, Boolean isRead); // 안 읽은 메시지의 수를 확인합니다.

}