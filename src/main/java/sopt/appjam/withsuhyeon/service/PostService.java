package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.constant.Age;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.constant.RequestInfo;
import sopt.appjam.withsuhyeon.domain.PostEntity;
import sopt.appjam.withsuhyeon.domain.RequestEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.post.req.PostRequestDto;
import sopt.appjam.withsuhyeon.dto.post.res.PostListResponseDto;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.PostRepository;
import sopt.appjam.withsuhyeon.repository.RequestRepository;
import sopt.appjam.withsuhyeon.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public PostListResponseDto getPostList(final Long userId, final String region, final String date) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        //날짜 출력 format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("M월 d일 (E) a h시 mm분", Locale.KOREAN);

        //date format 변환(String -> LocalDate)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //post 조건별 조회
        List<PostEntity> postList;
        List<PostEntity> filteredPostList = new ArrayList<>();
        // 선택된 지역에 대한 게시글 리스트
        if(region != null) {
            Region selectedRegion = Region.fromValue(region);
            postList = postRepository.findAllByRegion(selectedRegion);
            
            // 1. 지역과 날짜를 모두 선택하는 경우
            if(!date.equals("all")) {
                LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
                filteredPostList = filterByDate(postList, parsedDate);
            } else {
                // 2. 지역만 선택하는 경우
                filteredPostList = postList;
            }
            // 3. 날짜만 선택하는 경우
        } else if(!date.equals("all")) {
            postList = postRepository.findAllByRegion(userEntity.getRegion());
            LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
            filteredPostList = filterByDate(postList, parsedDate);
            
            // 4. 아무것도 선택하지 않는 경우
        } else {
            filteredPostList = postRepository.findAllByRegion(userEntity.getRegion());
        }
        
        //post 최신순 리스트
        Collections.reverse(filteredPostList);

        List<PostListResponseDto.PostResponse> postResponseList = filteredPostList.stream()
                .filter(post -> post.getGender().equals(userEntity.getGender()))
                .map(post -> PostListResponseDto.PostResponse.of(
                        post.getId(),
                        post.getTitle(),
                        post.getPrice(),
                        post.getAge().getValue(),
                        post.getGender(),
                        post.getDate().format(outputFormatter),
                        post.getMatching()
                ))
                .collect(Collectors.toList());

        //날짜 필터 리스트
        List<String> days = getDaysFilterList();

        return PostListResponseDto.of(userEntity.getRegion().getSubLocation(), days, postResponseList);
    }

    @Transactional
    public void removePostItem(final Long userId, final Long postId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> BaseException.type(PostErrorCode.POST_NOT_FOUND));

        // PostEntity가 UserEntity의 게시글인지 검증
        if (!postEntity.getUserEntity().equals(userEntity)) {
            throw new BaseException(PostErrorCode.POST_USER_FORBIDDEN);
        }

        // RequestEntity에서 해당 postId와 관련된 데이터를 삭제
        List<RequestEntity> relatedRequests = requestRepository.findByPostEntity(postEntity);
        if (!relatedRequests.isEmpty()) {
            requestRepository.deleteAll(relatedRequests);
        }

        postRepository.delete(postEntity);
    }
    private void validatePriceRange(Integer price) {
        if(price < 0 || price > 99999) {
            throw BaseException.type(PostErrorCode.POST_PRICE_INVALID_INPUT);
        }
    }

    private List<String> getDaysFilterList() {
        // 필터링 날짜 포맷터
        DateTimeFormatter filterDateFormatter = getFilterFormatter();

        List<String> days = new ArrayList<>();
        days.add("전체");

        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate targetDate = today.plusDays(i);
            days.add(targetDate.format(filterDateFormatter));
        }
        return days;
    }

    private DateTimeFormatter getFilterFormatter() {
        return DateTimeFormatter.ofPattern("M/d (E)", Locale.KOREAN);
    }

    // 필터링 함수 - 날짜 기준
    private List<PostEntity> filterByDate(List<PostEntity> postList, LocalDate localDate) {
        List<PostEntity> filteredPosts = postList.stream()
                .filter(post -> post.getDate().toLocalDate().equals(localDate))
                .collect(Collectors.toList());

        return filteredPosts;
    }
}