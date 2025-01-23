package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sopt.appjam.withsuhyeon.constant.Age;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.global.base.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class PostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gender", nullable = false)
    @NotBlank(message = "성별은 필수로 입력해야 합니다.")
    private Boolean gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "age", nullable = false)
    @NotBlank(message = "나이대는 필수로 입력해야 합니다.")
    private Age age;

    @Column(name = "city", nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    @NotBlank(message = "관심지역은 필수로 입력해야 합니다.")
    private Region region;

    @Column(name = "date", nullable = false)
    @NotBlank(message = "날짜 및 시간은 필수로 입력해야 합니다.")
    private LocalDateTime date;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "제목은 필수로 입력해야 합니다.")
    @Length(max = 30)
    private String title;

    @Column(name = "content", nullable = false)
    @NotBlank(message = "설명은 필수로 입력해야 합니다.")
    @Length(max = 200)
    private String content;

    @Column(name = "matching")
    private Boolean matching;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public PostEntity(
            boolean gender,
            Age age,
            Region region,
            LocalDateTime date,
            Integer price,
            String title,
            String content,
            Boolean matching,
            UserEntity userEntity
    ) {
        this.gender = gender;
        this.age = age;
        this.city = Region.getCityFrom(region);
        this.region = region;
        this.date = date;
        this.price = price;
        this.title = title;
        this.content = content;
        this.matching = matching;
        this.userEntity = userEntity;
    }
}