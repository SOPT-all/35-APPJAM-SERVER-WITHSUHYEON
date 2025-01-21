package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sopt.appjam.withsuhyeon.config.CategoryImageProperties;
import sopt.appjam.withsuhyeon.constant.Category;
import sopt.appjam.withsuhyeon.domain.GalleryEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.constant.res.Categories;
import sopt.appjam.withsuhyeon.dto.gallery.res.GalleriesListResponse;
import sopt.appjam.withsuhyeon.dto.gallery.res.GalleryDetailResponse;
import sopt.appjam.withsuhyeon.dto.gallery.res.GalleryResponse;
import sopt.appjam.withsuhyeon.exception.GalleryErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.GalleryRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final CategoryImageProperties categoryImageProperties;
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
                .imageUrl("https://kr.object.ncloudstorage.com/with-suhyeon-bucket/%EA%B0%9C%EB%A0%88%EC%A0%84%EB%93%9C%EC%82%AC%EC%A7%84.jpeg")
                .title(title)
                .content(content)
                .category(Category.from(category))
                .userEntity(user)
                .build();

        return galleryRepository.save(galleryEntity);
    }

    @Transactional(readOnly = true)
    public GalleriesListResponse getGalleriesWithCategory(
            String category
    ) {
        ArrayList<Categories> categoriesArray = new ArrayList<>(Category.toCategoriesResponse(categoryImageProperties));
        categoriesArray.add(
                0,
                new Categories("all", "전체")
        );

        if(category.equals("all")) {
            List<GalleryResponse> galleries = galleryRepository.findAll().stream().map(
                    gallery -> GalleryResponse.builder()
                            .galleryId(gallery.getId())
                            .imageUrl(gallery.getImageUrl())
                            .title(gallery.getTitle()).build()
            ).toList();

            return GalleriesListResponse.builder()
                    .categories(categoriesArray)
                    .galleries(galleries).build();
        } else {
            Category categoryConstant = Category.from(category);
            List<GalleryResponse> galleries = galleryRepository.findAllByCategory(categoryConstant).stream().map(
                    gallery -> GalleryResponse.builder()
                            .galleryId(gallery.getId())
                            .imageUrl(gallery.getImageUrl())
                            .title(gallery.getTitle()).build()
            ).toList();

            return GalleriesListResponse.builder()
                    .categories(categoriesArray)
                    .galleries(galleries).build();
        }
    }

    @Transactional(readOnly = true)
    public GalleryDetailResponse getGalleryDetail(
            final Long galleryId,
            final UserEntity user
    ) {
        GalleryEntity gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> BaseException.type(GalleryErrorCode.GALLERY_NOT_FOUND));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);

        return GalleryDetailResponse.builder()
                .imageUrl(gallery.getImageUrl())
                .category(gallery.getCategory().getValue())
                .title(gallery.getTitle())
                .profileImage(gallery.getUserEntity().getProfileImage().getValue())
                .nickname(gallery.getUserEntity().getNickname())
                .createdAt(gallery.getCreatedDate().format(dateTimeFormatter))
                .content(gallery.getContent())
                .owner(user.equals(gallery.getUserEntity()))
                .build();
    }

    @Transactional
    public void deleteGallery(
            final Long galleryId,
            final UserEntity user
    ) {
        GalleryEntity gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> BaseException.type(GalleryErrorCode.GALLERY_NOT_FOUND));

        if(!user.equals(gallery.getUserEntity())) {
            throw BaseException.type(GalleryErrorCode.GALLERY_USER_FORBIDDEN);
        }

        galleryRepository.delete(gallery); // S3 내에 있는 이미지도 삭제해야 되는 URL 필요합니다.
    }
}
