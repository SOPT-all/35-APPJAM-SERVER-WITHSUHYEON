package sopt.appjam.withsuhyeon.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sopt.appjam.withsuhyeon.config.CategoryImageProperties;
import sopt.appjam.withsuhyeon.config.S3Config;
import sopt.appjam.withsuhyeon.constant.Category;
import sopt.appjam.withsuhyeon.domain.GalleryEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.gallery.res.GalleriesListResponse;
import sopt.appjam.withsuhyeon.dto.gallery.res.GalleryDetailResponse;
import sopt.appjam.withsuhyeon.dto.gallery.res.GalleryResponse;
import sopt.appjam.withsuhyeon.exception.FileErrorCode;
import sopt.appjam.withsuhyeon.exception.GalleryErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.GalleryRepository;
import sopt.appjam.withsuhyeon.util.FileConvertUtil;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryService {
    @Value("${cloud.aws.s3.bucket}") private String bucketName;
    private final CategoryImageProperties categoryImageProperties;
    private final GalleryRepository galleryRepository;
    private final S3Config s3Config;

    @Transactional
    public GalleryEntity createGallery(
            final MultipartFile image,
            final String category,
            final String title,
            final String content,
            final UserEntity user
    ) {
        AmazonS3 s3 = s3Config.getAmazonS3();

        UUID uuid = UUID.randomUUID();
        String imageName = uuid + "_" + image.getOriginalFilename();
        File uploadImageFile = null;

        try {
            uploadImageFile = FileConvertUtil.convertMultipartFileToFile(image);
            String guideImageObjectPath = "image/" + imageName;
            s3.putObject(bucketName, guideImageObjectPath, uploadImageFile);

            String imageUploadURL = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + guideImageObjectPath;
            log.info(imageUploadURL);

            setAcl(s3, guideImageObjectPath);

            GalleryEntity galleryEntity = GalleryEntity.builder()
                    .uploadUrl(guideImageObjectPath)
                    .imageUrl(imageUploadURL)
                    .title(title)
                    .content(content)
                    .category(Category.from(category))
                    .userEntity(user)
                    .build();

            return galleryRepository.save(galleryEntity);
        } catch (AmazonS3Exception e) {
            log.info(e.getErrorMessage());
            throw BaseException.type(FileErrorCode.S3_ERROR); // 이미지 저장중 생긴 예외
        } finally {
            if (uploadImageFile != null) {
                uploadImageFile.delete(); // 업로드에 사용한 임시 파일을 삭제합니다.
            }
        }
    }

    @Transactional(readOnly = true)
    public GalleriesListResponse getGalleriesWithCategory(
            String category
    ) {
        if (category.equals("all")) {
            List<GalleryResponse> galleries = new ArrayList<>( // Mutable list로 변환
                    galleryRepository.findAll().stream()
                            .map(gallery -> GalleryResponse.builder()
                                    .galleryId(gallery.getId())
                                    .imageUrl(gallery.getImageUrl())
                                    .title(gallery.getTitle()).build())
                            .toList()
            );
            Collections.reverse(galleries); // 역순 정렬

            return new GalleriesListResponse(galleries);
        } else {
            Category categoryConstant = Category.from(category);
            List<GalleryResponse> galleries = new ArrayList<>( // Mutable list로 변환
                    galleryRepository.findAllByCategory(categoryConstant).stream()
                            .map(gallery -> GalleryResponse.builder()
                                    .galleryId(gallery.getId())
                                    .imageUrl(gallery.getImageUrl())
                                    .title(gallery.getTitle()).build())
                            .toList()
            );
            Collections.reverse(galleries); // 역순 정렬

            return new GalleriesListResponse(galleries);
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

    private void setAcl(AmazonS3 s3, String objectPath) {
        AccessControlList objectAcl = s3.getObjectAcl(bucketName, objectPath);
        objectAcl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(bucketName, objectPath, objectAcl);
    }
}
