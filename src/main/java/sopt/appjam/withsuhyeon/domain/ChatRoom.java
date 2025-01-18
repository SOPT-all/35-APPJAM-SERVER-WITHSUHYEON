package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document(collection = "chat_room")
@Getter
@NoArgsConstructor
public class ChatRoom {
    @Id
    private String id;

    @Field("room_number")
    private UUID roomNumber;

    @Field("post_id")
    private Long postId;

    @Field("owner_id") // 채팅방을 소유한 사람
    private Long ownerId;

    @Field("peer_id") // 채팅 상대방의 ID
    private Long peerId;

    @Builder
    public ChatRoom(UUID roomNumber, Long postId, Long ownerId, Long peerId) {
        this.roomNumber = roomNumber;
        this.postId = postId;
        this.ownerId = ownerId;
        this.peerId = peerId;
    }
}
