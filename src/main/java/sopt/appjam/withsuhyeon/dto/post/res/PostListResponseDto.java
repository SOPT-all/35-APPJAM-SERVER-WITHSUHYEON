package sopt.appjam.withsuhyeon.dto.post.res;

import java.util.List;

public record PostListResponseDto(
        String region,
        List<String> days,
        List<PostResponse> posts
) {
    public record PostResponse(
            Long postId,
            String title,
            Integer price,
            String age,
            Boolean gender,
            String date,
            Boolean matching,
            Boolean isExpired
    ) {
        public static PostResponse of(final Long postId, final String title, final Integer price, final String age, final Boolean gender, final String date, final Boolean matching, final Boolean isExpired) {
            return new PostResponse(postId, title, price, age, gender, date, matching, isExpired);
        }
     }

     public static PostListResponseDto of(final String region, final List<String> days, final List<PostResponse> postList) {
        return new PostListResponseDto(region, days, postList);
     }
}