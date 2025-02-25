package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import sopt.appjam.withsuhyeon.constant.ProfileImage;
import sopt.appjam.withsuhyeon.constant.Region;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@DynamicUpdate
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^01([0|1|6|7|8|9])\\d{3,4}\\d{4}", message = "전화번호 형식에 맞게 입력해야 합니다.")
    @NotBlank(message = "전화번호는 필수로 입력해야 합니다.")
    private String phoneNumber;

    @Column(name = "nickname", nullable = false)
    @Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{2,12}$", message = "한글(자음/모음 포함), 영문, 숫자로 조합된 2 ~ 12자로 입력해야 합니다.")
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(min = 2, max = 12)
    private String nickname;

    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;

    @Column(name = "gender", nullable = false, updatable = false)
    private Boolean gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "image", nullable = false)
    private ProfileImage profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private Region region;

    @Builder
    public UserEntity(
            String phoneNumber,
            String nickname,
            Integer birthYear,
            Boolean gender,
            ProfileImage profileImage,
            Region region
    ) {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.gender = gender;
        this.profileImage = profileImage;
        this.region = region;
    }

    // 관심 지역 업데이트 메서드 추가
    public void updateRegion(Region newRegion) {
        this.region = newRegion;
    }
}