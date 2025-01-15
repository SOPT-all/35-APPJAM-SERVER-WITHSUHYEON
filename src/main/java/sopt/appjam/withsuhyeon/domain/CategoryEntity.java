package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.constant.Category;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    private GalleryEntity galleryEntity;

    @Builder
    public CategoryEntity(Category category, GalleryEntity galleryEntity) {
        this.category = category;
        this.galleryEntity = galleryEntity;
    }
}
