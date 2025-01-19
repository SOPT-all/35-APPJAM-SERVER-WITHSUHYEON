package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.domain.PostEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllByRegion(Region region);

    @Query("SELECT p.id FROM PostEntity p WHERE p.region = :region")
    List<Long> findIdsByRegion(Region region);

    List<PostEntity> findByIdIn(List<Long> ids);
}