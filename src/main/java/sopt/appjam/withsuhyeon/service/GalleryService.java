package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sopt.appjam.withsuhyeon.constant.Category;
import sopt.appjam.withsuhyeon.domain.GalleryEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.repository.GalleryRepository;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;

    @Transactional
    public GalleryEntity createGallery(
            final MultipartFile image,
            final String category,
            final String title,
            final String content,
            final UserEntity user
    ) {
        // MultipartFile URL 로 변경해주는 로직 필요

        GalleryEntity galleryEntity = GalleryEntity.builder()
                .imageUrl("")
                .title(title)
                .content(content)
                .category(Category.from(category))
                .userEntity(user)
                .build();

        return galleryRepository.save(galleryEntity);
    }
}
