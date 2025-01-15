package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.constant.Profile;
import sopt.appjam.withsuhyeon.constant.Region;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^01(?:0|1|[6-9])\\\\d{8}$", message = "전화번호 형식에 맞게 입력해야 합니다.")
    @NotNull(message = "전화번호는 필수로 입력해야 합니다.")
    private String phoneNumber;

    @Column(name = "nickname", nullable = false, unique = true)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,12}$", message = "한글, 영문, 숫자로 조합된 2 ~ 12자로 입력해야 합니다.")
    @NotNull(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(min = 2, max = 12)
    private String nickname;

    @Column(name = "birth_year", nullable = false)
    @NotNull(message = "출생연도는 필수로 입력해야 합니다.")
    private Integer birthYear;

    @Column(name = "gender", nullable = false, updatable = false)
    @NotNull(message = "성별은 필수로 입력해야 합니다.")
    private Boolean gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "image", nullable = false)
    @NotNull(message = "프로필 이미지는 필수로 입력해야 합니다.")
    private Profile image;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false)
    @NotNull(message = "관심 지역은 필수로 입력해야 합니다.")
    private Region region;

    @Builder
    public UserEntity(
            String phoneNumber,
            String nickname,
            Integer birthYear,
            Boolean gender,
            Profile image,
            Region region
    ) {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.gender = gender;
        this.image = image;
        this.region = region;
    }
}
