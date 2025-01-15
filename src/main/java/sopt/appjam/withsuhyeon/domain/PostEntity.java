package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.constant.Region;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gender", nullable = false)
    @NotNull(message = "성별은 필수로 입력해야 합니다.")
    private Boolean gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    @NotNull(message = "관심지역은 필수로 입력해야 합니다.")
    private Region region;

    @Column(name = "date", nullable = false)
    @NotNull(message = "날짜 및 시간은 필수로 입력해야 합니다.")
    private LocalDateTime date;

    @Column(name = "price")
    private Integer price;

    @Column(name = "title", nullable = false)
    @NotNull(message = "제목은 필수로 입력해야 합니다.")
    @Size(max = 30)
    private String title;

    @Column(name = "content", nullable = false)
    @NotNull(message = "설명은 필수로 입력해야 합니다.")
    @Size(max = 200)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "matching")
    private Boolean matching;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public PostEntity(
            boolean gender,
            Region region,
            LocalDateTime date,
            Integer price,
            String title,
            String content,
            Boolean matching,
            UserEntity userEntity
    ) {
        this.gender = gender;
        this.region = region;
        this.date = date;
        this.price = price;
        this.title = title;
        this.content = content;
        this.matching = matching;
        this.userEntity = userEntity;
    }
}
