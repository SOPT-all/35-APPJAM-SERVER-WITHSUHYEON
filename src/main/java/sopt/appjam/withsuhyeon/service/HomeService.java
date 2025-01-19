package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.home.res.HomePost;
import sopt.appjam.withsuhyeon.dto.home.res.HomePostsResponse;
import sopt.appjam.withsuhyeon.repository.PostRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserRetriever userRetriever;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public HomePostsResponse getRandomPosts(final long userId) {
        UserEntity userEntity = userRetriever.findByUserId(userId);

        Integer matchingCount = 3212;
        Region userRegion = userEntity.getRegion();

        List<Long> allPostIds = postRepository.findIdsByRegion(userRegion);
        Collections.shuffle(allPostIds);

        List<Long> randomIds = allPostIds.stream()
                .limit(3)
                .collect(Collectors.toList());

        //날짜 출력 format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("M월 d일 (E) a h시 mm분", Locale.KOREAN);

        // 주 활동 지역 내 게시글 조회
        List<HomePost> randomPosts = postRepository.findByIdIn(randomIds)
                .stream()
                .map(post -> HomePost.of(
                        post.getId(),
                        post.getTitle(),
                        post.getPrice(),
                        post.getAge().getValue(),
                        post.getGender(),
                        post.getDate().format(outputFormatter),
                        post.getMatching()
                ))
                .collect(Collectors.toList());

        return HomePostsResponse.of(matchingCount, userRegion.getSubLocation(), randomPosts);
    }

}
