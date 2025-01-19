package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "chat_message")
@Getter
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;

    @Field("sender_id")
    private Long senderId;

    @Field("receiver_id")
    private Long receiverId;

    @Field("content")
    private String content;

    @Field("timestamp")
    private LocalDateTime timestamp;

    @Field("is_read")
    private Boolean isRead;

    @Field("chat_room_id")
    private String chatRoomId;

    @Builder
    public ChatMessage(Long senderId, Long receiverId, String content, Boolean isRead, String chatRoomId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = isRead;
        this.chatRoomId = chatRoomId;
    }

    public void updateIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}