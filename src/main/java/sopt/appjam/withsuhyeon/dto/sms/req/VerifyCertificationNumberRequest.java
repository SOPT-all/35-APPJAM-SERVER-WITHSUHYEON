package sopt.appjam.withsuhyeon.dto.sms.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VerifyCertificationNumberRequest(
        @Pattern(regexp = "^01([0|1|6|7|8|9])\\d{4}\\d{4}", message = "전화번호 형식에 맞게 입력해야 합니다.")
        @NotNull(message = "전화번호는 필수로 입력해야 합니다.")
        String phoneNumber,
        @Pattern(regexp = "^\\d{6}$", message = "인증번호는 숫자 6자리여야 합니다.")
        @NotNull(message = "인증번호는 필수로 입력해야 합니다.")
        String verifyNumber
) {
}
