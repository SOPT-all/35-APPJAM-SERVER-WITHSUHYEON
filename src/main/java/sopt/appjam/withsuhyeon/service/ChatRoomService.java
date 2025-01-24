package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatRoomResponse;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatRoomsResponse;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.ChatMessageRepository;
import sopt.appjam.withsuhyeon.repository.ChatRoomRepository;
import sopt.appjam.withsuhyeon.repository.PostRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserRetriever userRetriever;
    private final ChatRoomInfoRetriever chatRoomInfoRetriever;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository; // 코드 분리 필요
    private final PostRepository postRepository;

    public String getOwnerChatRoomIdInPost(Long postId, Long ownerId, Long peerId) {
        // 양쪽 방향에서 ChatRoom을 검색
        Optional<ChatRoom> ownerChatRoom = chatRoomRepository.findByPostIdAndOwnerIdAndPeerId(postId, ownerId, peerId);
        Optional<ChatRoom> peerChatRoom = chatRoomRepository.findByPostIdAndOwnerIdAndPeerId(postId, peerId, ownerId);

        if (ownerChatRoom.isPresent() && peerChatRoom.isPresent()) {
            return ownerChatRoom.get().getId().toString();
        } else if (ownerChatRoom.isPresent()) {
            return ownerChatRoom.get().getId().toString(); // 2번 시나리오: A 유저는 방이 있지만 B 유저는 방이 없는 경우 (앱잼 기간 내 구현 X)
        } else if (peerChatRoom.isPresent()) {
            return "NO"; // 3번 시나리오: B 유저는 방이 있지만 A 유저는 방이 없는 경우 (앱잼 기간 내 구현 X)
        } else {
            return "NO"; // 4번 시나리오: 양쪽 모두 채팅방이 없는 경우
        }
    }

    public String getPeerChatRoomIdInPost(Long postId, Long ownerId, Long peerId) {
        // 양쪽 방향에서 ChatRoom을 검색
        Optional<ChatRoom> ownerChatRoom = chatRoomRepository.findByPostIdAndOwnerIdAndPeerId(postId, ownerId, peerId);
        Optional<ChatRoom> peerChatRoom = chatRoomRepository.findByPostIdAndOwnerIdAndPeerId(postId, peerId, ownerId);

        if (ownerChatRoom.isPresent() && peerChatRoom.isPresent()) {
            return peerChatRoom.get().getId().toString();
        } else if (ownerChatRoom.isPresent()) {
            return "NO"; // 2번 시나리오: A 유저는 방이 있지만 B 유저는 방이 없는 경우 (앱잼 기간 내 구현 X)
        } else if (peerChatRoom.isPresent()) {
            return peerChatRoom.get().getId().toString(); // 3번 시나리오: B 유저는 방이 있지만 A 유저는 방이 없는 경우 (앱잼 기간 내 구현 X)
        } else {
            return "NO"; // 4번 시나리오: 양쪽 모두 채팅방이 없는 경우
        }
    }

    public ChatRoomsResponse getChatRooms(Long userId) {
        UserEntity user = userRetriever.findByUserId(userId);
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByOwnerId(user.getId());

        List<ChatRoomResponse> chatRoomsResponses = chatRooms.stream().map(
                chatRoom -> ChatRoomResponse.builder()
                        .ownerChatRoomId(chatRoom.getId())
                        .peerChatRoomId(chatRoomRepository.findByPostIdAndOwnerIdAndPeerId(chatRoom.getPostId(), chatRoom.getPeerId(), chatRoom.getOwnerId()).orElseThrow().getId())
                        .postId(chatRoom.getPostId())
                        .chatOwnerId(userId)
                        .chatPeerId(chatRoom.getPeerId())
                        .chatPeerNickname(userRetriever.findByUserId(chatRoom.getPeerId()).getNickname())
                        .chatPeerProfileImage(userRetriever.findByUserId(chatRoom.getPeerId()).getProfileImage().getValue())
                        .lastChatMessage(chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber()).getLastMessage())
                        .lastChatAt(chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber()).getLastChatAt())
                        .unReadCount(chatMessageRepository.countByChatRoomIdAndIsRead(chatRoom.getId(), false))
                        .postTitle(postRepository.findById(chatRoom.getPostId()).orElseThrow(() -> BaseException.type(PostErrorCode.POST_NOT_FOUND)).getTitle())
                        .postPlace(postRepository.findById(chatRoom.getPostId()).orElseThrow(() -> BaseException.type(PostErrorCode.POST_NOT_FOUND)).getRegion().getSubLocation())
                        .postCost(postRepository.findById(chatRoom.getPostId()).orElseThrow(() -> BaseException.type(PostErrorCode.POST_NOT_FOUND)).getPrice().toString())
                        .build()
        ).sorted(Comparator.comparing(ChatRoomResponse::lastChatAt).reversed()).toList();

        return new ChatRoomsResponse(chatRoomsResponses);
    }
}
