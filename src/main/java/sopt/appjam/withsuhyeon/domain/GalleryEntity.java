package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.global.base.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gallery")
public class GalleryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url", nullable = false)
    @NotNull
    private String imageUrl;

    @Column(name = "title", nullable = false)
    @NotNull(message = "제목은 필수로 입력해야 합니다.")
    @Size(max = 30)
    private String title;

    @Column(name = "content", nullable = false)
    @NotNull(message = "설명은 필수로 입력해야 합니다.")
    @Size(max = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public GalleryEntity(
            String imageUrl,
            String title,
            String content,
            UserEntity userEntity
    ) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
        this.userEntity = userEntity;
    }
}
