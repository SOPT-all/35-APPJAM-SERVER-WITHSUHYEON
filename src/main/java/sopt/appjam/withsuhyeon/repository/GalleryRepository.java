package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sopt.appjam.withsuhyeon.constant.Category;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.domain.GalleryEntity;

import java.util.List;

public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {
    @Query("SELECT g FROM GalleryEntity g WHERE g.category = :category")
    List<GalleryEntity> findAllByCategory(Category category);
}
