package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.constant.Category;
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

    @Column(name = "upload_url", nullable = false, length = 1000)
    @NotBlank
    private String uploadUrl;

    @Column(name = "image_url", nullable = false, length = 1000)
    @NotBlank
    private String imageUrl;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "제목은 필수로 입력해야 합니다.")
    @Size(max = 30)
    private String title;

    @Column(name = "content")
    @Size(max = 200)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public GalleryEntity(
            String uploadUrl,
            String imageUrl,
            String title,
            String content,
            Category category,
            UserEntity userEntity
    ) {
        this.uploadUrl = uploadUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
        this.category = category;
        this.userEntity = userEntity;
    }
}