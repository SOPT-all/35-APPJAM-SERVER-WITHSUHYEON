package sopt.appjam.withsuhyeon.dto.post.res;

public record PostDetailResponse(
    Long postId,
    Long connectorId,
    Long writerId,
    String nickname,
    String title,
    String content,
    PostChatRoomInfoResponse chatRoomInfo
){
}