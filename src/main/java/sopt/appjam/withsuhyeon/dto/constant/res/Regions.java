package sopt.appjam.withsuhyeon.dto.constant.res;

import java.util.List;

public record Regions(
        String location,
        List<String> subLocations
) {
}
