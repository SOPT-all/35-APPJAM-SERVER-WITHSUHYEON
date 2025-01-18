package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.PostEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.post.res.PostChatRoomInfoResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostDetailResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostsResponse;
import sopt.appjam.withsuhyeon.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostTestService {
    private final UserRetriever userRetriever;
    private final ChatRoomService chatRoomService;
    private final PostRepository postRepository;

    public PostsResponse getAllPost() {
        return new PostsResponse(
                postRepository.findAll().stream().map(
                        post -> new PostResponse(
                                post.getId(),
                                post.getTitle()
                        )
                ).toList());
    }

    public PostDetailResponse getPostDetail(Long userId, Long postId) {
        UserEntity user = userRetriever.findByUserId(userId);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow();

        return new PostDetailResponse(
                postId,
                user.getId(),
                post.getUserEntity().getId(),
                post.getUserEntity().getNickname(),
                post.getTitle(),
                post.getContent(),
                new PostChatRoomInfoResponse(
                        chatRoomService.getChatRoomWithPostOwner(post.getId(), user.getId(), post.getUserEntity().getId()),
                        chatRoomService.getChatRoomWithPostOpponent(post.getId(), user.getId(), post.getUserEntity().getId())
                )
        );
    }

}
