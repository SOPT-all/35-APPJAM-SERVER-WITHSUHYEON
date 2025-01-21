package sopt.appjam.withsuhyeon.dto.block.res;

import java.util.List;

public record BlockerIdsResponse(
        List<Long> userIds
) {
    public static BlockerIdsResponse of(List<Long> userIds) {
        return new BlockerIdsResponse(userIds);
    }
}
