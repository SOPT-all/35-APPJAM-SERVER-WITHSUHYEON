package sopt.appjam.withsuhyeon.dto.home.res;

import java.util.List;

public record HomePostsResponse(
        Integer count,
        String region,
        String nickname,
        List<HomePost> homePosts
) {
    public static HomePostsResponse of(final Integer count, final String region, final String nickname, final List<HomePost> homePosts) {
        return new HomePostsResponse(count, region, nickname, homePosts);
    }
}
