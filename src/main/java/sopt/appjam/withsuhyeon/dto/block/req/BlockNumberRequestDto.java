package sopt.appjam.withsuhyeon.dto.block.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BlockNumberRequestDto(
        @Pattern(regexp = "^01([0|1|6|7|8|9])\\d{4}\\d{4}", message = "차단할 번호는 전화번호 형식에 맞게 11자로 입력해야 합니다.")
        @NotBlank(message = "차단할 번호는 형식에 맞게 11자리로 입력해야 합니다.")
        String phoneNumber
) {
}