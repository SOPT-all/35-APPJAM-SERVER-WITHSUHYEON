package sopt.appjam.withsuhyeon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.gallery.req.CreateGalleryRequest;
import sopt.appjam.withsuhyeon.service.GalleryService;
import sopt.appjam.withsuhyeon.service.UserRetriever;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/galleries")
public class GalleryController {
    private final GalleryService galleryService;
    private final UserRetriever userRetriever;

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @UserId Long userId,
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart(value = "createGalleryRequest") @Valid CreateGalleryRequest createGalleryRequest
    ) {
        galleryService.createGallery(
                image,
                createGalleryRequest.category(),
                createGalleryRequest.title(),
                createGalleryRequest.content(),
                userRetriever.findByUserId(userId)
        );

        return ResponseEntity.ok().build();
    }

}
