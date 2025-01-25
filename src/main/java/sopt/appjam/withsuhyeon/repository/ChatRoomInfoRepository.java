package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sopt.appjam.withsuhyeon.domain.ChatRoomInfo;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomInfoRepository extends MongoRepository<ChatRoomInfo, String> {
    Optional<ChatRoomInfo> findByRoomNumber(UUID roomNumber); // roomNumber 를 가지고 ChatRoomInfo 를 찾아봅니다.
    void deleteByRoomNumber(UUID roomNumber);
}

