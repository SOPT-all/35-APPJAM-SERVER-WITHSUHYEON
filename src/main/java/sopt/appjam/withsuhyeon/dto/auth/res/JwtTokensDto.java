package sopt.appjam.withsuhyeon.dto.auth.res;

import lombok.Builder;

@Builder
public record JwtTokensDto(
        String accessToken,
        String refreshToken
) {
}

