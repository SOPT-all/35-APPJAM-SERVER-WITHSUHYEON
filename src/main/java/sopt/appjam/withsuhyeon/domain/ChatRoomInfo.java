package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "chat_room_info")
@Getter
@NoArgsConstructor
public class ChatRoomInfo {
    @Id
    private String id;

    @Field("room_number")
    private UUID roomNumber;

    @Field("last_message") // 마지막 메시지 내용
    private String lastMessage;

    @Field("last_chat_at") // 마지막 채팅 발생 시간
    private LocalDateTime lastChatAt;

    @Field("participating_users") // 현재 채팅에 참여중인 유저들
    private Set<Long> participatingUsers = new HashSet<>();

    @Builder
    public ChatRoomInfo(UUID roomNumber, String lastMessage, LocalDateTime lastChatAt) {
        this.roomNumber = roomNumber;
        this.lastMessage = lastMessage;
        this.lastChatAt = lastChatAt;
        this.participatingUsers = Collections.emptySet();
    }

    // 채팅방에 접속중인 유저 추가
    public void addUser(Long userId) {
        participatingUsers.add(userId);
    }

    // 채팅방에 접속중인 유저 제거
    public void removeUser(Long userId) {
        participatingUsers.remove(userId);
    }

    // 유저가 채팅방에 접속중인지 확인
    public boolean isUserParticipate(Long userId) {
        return participatingUsers.contains(userId);
    }

    // 채팅 로그 업데이트
    public void updateInfo(String content) {
        this.lastMessage = content;
        this.lastChatAt = LocalDateTime.now(); // chatMessage.getTimestamp(); 이런식으로 리팩터링 필요
    }
}
