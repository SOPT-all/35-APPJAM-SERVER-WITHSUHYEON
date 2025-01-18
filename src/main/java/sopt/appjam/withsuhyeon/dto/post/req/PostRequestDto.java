package sopt.appjam.withsuhyeon.dto.post.req;

import java.time.LocalDateTime;
import java.util.List;

public record PostRequestDto(
        Boolean gender,
        String age,
        List<String> requests,
        String region,
        LocalDateTime date,
        Integer price,
        String title,
        String content
) {
}
