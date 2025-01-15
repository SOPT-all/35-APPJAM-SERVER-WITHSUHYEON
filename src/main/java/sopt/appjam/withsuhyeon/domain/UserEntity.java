package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.constant.Profile;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "nickname", nullable = false, unique = true)
    @Size(min = 2, max = 12)
    private String nickname;

    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "image", nullable = false)
    private Profile image;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "detail_area", nullable = false)
    private String detailArea;


    @Builder
    public UserEntity(
            String phoneNumber,
            String nickname,
            Integer birthYear,
            Boolean gender,
            Profile image,
            String area,
            String detailArea
    ) {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.gender = gender;
        this.image = image;
        this.area = area;
        this.detailArea = detailArea;
    }


}
