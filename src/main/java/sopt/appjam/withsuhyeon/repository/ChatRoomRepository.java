package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sopt.appjam.withsuhyeon.domain.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findByPostIdAndOwnerIdAndPeerId(Long postId, Long ownerId, Long peerId); // 해당 게시글에 대해 나와 상배당이 진행한 채팅방이 있는지 확인합니다.
    List<ChatRoom> findAllByOwnerId(Long ownerId);
}