package sopt.appjam.withsuhyeon.dto.post.res;

import lombok.Builder;

import java.util.List;

@Builder
public record PostDetailInfo(
        String region,
        Boolean gender,
        String age,
        String date,
        List<String> requests
) {
}
