package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.constant.Age;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.constant.RequestInfo;
import sopt.appjam.withsuhyeon.domain.PostEntity;
import sopt.appjam.withsuhyeon.domain.RequestEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.post.req.PostRequestDto;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.PostRepository;
import sopt.appjam.withsuhyeon.repository.RequestRepository;
import sopt.appjam.withsuhyeon.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RequestRepository requestRepository;

    public PostEntity createPostItem(final Long userId, final PostRequestDto postRequestDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        validatePriceRange(postRequestDto.price());

        Age age = Age.fromValue(postRequestDto.age());
        Region region = Region.fromValue(postRequestDto.region());

        PostEntity postEntity = PostEntity.builder()
                .gender(postRequestDto.gender())
                .age(age)
                .region(region)
                .date(postRequestDto.date())
                .price(postRequestDto.price())
                .title(postRequestDto.title())
                .content(postRequestDto.content())
                .matching(false)
                .userEntity(userEntity)
                .build();

        postRepository.save(postEntity);

        List<String> requests = postRequestDto.requests();
        for (String request : requests) {
            // value를 기반으로 RequestInfo Enum 값 찾기
            RequestInfo requestInfo = RequestInfo.fromValue(request);

            RequestEntity requestEntity = RequestEntity.builder()
                    .requestInfo(requestInfo)
                    .postEntity(postEntity)
                    .build();

            requestRepository.save(requestEntity);
        }

        return postEntity;
    }

    private void validatePriceRange(Integer price) {
        if(price < 0 || price > 99999) {
            throw BaseException.type(PostErrorCode.POST_PRICE_INVALID_INPUT);
        }
    }
}
