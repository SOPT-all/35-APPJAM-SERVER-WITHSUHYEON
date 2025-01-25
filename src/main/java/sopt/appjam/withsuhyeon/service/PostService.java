package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.constant.Age;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.constant.RequestInfo;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.domain.PostEntity;
import sopt.appjam.withsuhyeon.domain.RequestEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.home.res.HomePost;
import sopt.appjam.withsuhyeon.dto.home.res.HomePostsResponse;
import sopt.appjam.withsuhyeon.dto.post.req.PostRequestDto;
import sopt.appjam.withsuhyeon.dto.post.res.ChatRoomInfoPost;
import sopt.appjam.withsuhyeon.dto.post.res.PostDetailInfo;
import sopt.appjam.withsuhyeon.dto.post.res.PostDetailResponse;
import sopt.appjam.withsuhyeon.dto.post.res.PostListResponseDto;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RequestRepository requestRepository;
    private final UserRetriever userRetriever;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomInfoRepository chatRoomInfoRepository;

    @Transactional
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
    public PostListResponseDto getPostList(final Long userId, final List<Long> blockers, final String region, final String date) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        //날짜 출력 format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("M월 d일 (E) a h시 mm분", Locale.KOREAN);

        //post 조건별 조회
        List<PostEntity> filteredPostList;

        // 내 관심지역 여부 필터링
        Region selectedRegion;
        if(region.equals("my-region")) {
            selectedRegion = userEntity.getRegion();
        } else {
            selectedRegion = Region.fromValue(region);
        }

        // 전체지역 여부 필터링
        if(Region.isAllRegion(region)) {
            if(selectedRegion.equals(Region.ALL)) {
                filteredPostList = postRepository.findAllExcludingBlockedUsers(blockers);
            } else {
                String city = Region.getCityFrom(selectedRegion);
                filteredPostList = postRepository.findAllByCityExcludingBlockedUsers(city, blockers);
            }
        } else {
            filteredPostList = postRepository.findAllByRegionExcludingBlockedUsers(selectedRegion, blockers);
        }

        // 날짜 포매팅
        if(!date.equals("전체")) {
            // 요일 제거 및 파싱
            int currentYear = LocalDate.now().getYear();
            LocalDate parsedDate = LocalDate.parse(currentYear + "/" + date.split(" ")[0], DateTimeFormatter.ofPattern("yyyy/M/d"));
            log.info(parsedDate.toString());
            filteredPostList = filterByDate(filteredPostList, parsedDate);
        }

        //post 최신순 리스트
        Collections.reverse(filteredPostList);

        List<PostListResponseDto.PostResponse> postResponseList = filteredPostList.stream()
                .map(post -> PostListResponseDto.PostResponse.of(
                        post.getId(),
                        post.getTitle(),
                        post.getPrice(),
                        post.getAge().getValue(),
                        post.getGender(),
                        post.getDate().format(outputFormatter),
                        post.getMatching(),
                        post.getDate().isBefore(LocalDateTime.now())
                ))
                .collect(Collectors.toList());

        //날짜 필터 리스트
        List<String> days = getDaysFilterList();

        return PostListResponseDto.of(selectedRegion.getSubLocation(), days, postResponseList);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(final Long userId, final Long postId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> BaseException.type(PostErrorCode.POST_NOT_FOUND));

        DateTimeFormatter postWriteDateFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);
        DateTimeFormatter findDateFormatter = DateTimeFormatter.ofPattern("M월 d일 (E) a h시 mm분", Locale.KOREAN);

        List<RequestEntity> requestEntities = requestRepository.findByPostEntity(post);
        List<String> requests = requestEntities.stream().map(
                requestEntity -> requestEntity.getRequestInfo().getValue()
        ).toList();

        return PostDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUserEntity().getNickname())
                .createdAt(post.getCreatedDate().format(postWriteDateFormatter))
                .profileImage(post.getUserEntity().getProfileImage().getValue())
                .price(post.getPrice())
                .owner(user.equals(post.getUserEntity()))
                .matching(false) // 이후 리팩터링 필요
                .isExpired(post.getDate().isBefore(LocalDateTime.now()))
                .postDetailInfo(
                        PostDetailInfo.builder()
                                .region(post.getRegion().getSubLocation())
                                .gender(post.getGender())
                                .age(post.getAge().getValue().replace(" ", ""))
                                .date(post.getDate().format(findDateFormatter))
                                .requests(requests).build()
                )
                .chatRoomInfoPost(
                        ChatRoomInfoPost.builder()
                                .postId(post.getId())
                                .ownerId(user.getId())
                                .writerId(post.getUserEntity().getId())
                                .ownerChatRoomId(chatRoomService.getOwnerChatRoomIdInPost(post.getId(), user.getId(), post.getUserEntity().getId()))
                                .peerChatRoomId(chatRoomService.getPeerChatRoomIdInPost(post.getId(), user.getId(), post.getUserEntity().getId())).build()
                ).build();

    }

    @Transactional(readOnly = true)
    public HomePostsResponse getRandomPosts(final long userId, final List<Long> blockerIds) {
        UserEntity userEntity = userRetriever.findByUserId(userId);

        Integer matchingCount = 3212;
        Region userRegion = userEntity.getRegion();

        List<Long> allPostIds = postRepository.findIdsByRegionExcludingBlockedUsersAndExpiredDate(userRegion, blockerIds);
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

        return HomePostsResponse.of(matchingCount, userRegion.getSubLocation(), userEntity.getNickname(), randomPosts);
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

        // 관련된 ChatRoom 삭제
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByPostId(postId);
        if (!chatRooms.isEmpty()) {
            chatRooms.forEach(chatRoom -> {
                // roomNumber 기준으로 ChatRoomInfo 삭제
                chatRoomInfoRepository.deleteByRoomNumber(chatRoom.getRoomNumber());
            });
            chatRoomRepository.deleteAll(chatRooms); // ChatRoom 삭제
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
        log.info("오늘의 날짜 보여주기 =========" + today.toString());

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