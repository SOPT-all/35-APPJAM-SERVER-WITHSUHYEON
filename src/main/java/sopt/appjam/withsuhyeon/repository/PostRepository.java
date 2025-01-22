package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.domain.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.region = :region AND p.userEntity.id NOT IN (:blockerIds)")
    List<PostEntity> findAllByRegionExcludingBlockedUsers(Region region, List<Long> blockerIds);

    @Query("SELECT p.id FROM PostEntity p WHERE p.region = :region AND p.userEntity.id NOT IN (:blockerIds)")
    List<Long> findIdsByRegionExcludingBlockedUsers(Region region, List<Long> blockerIds);

    List<PostEntity> findByIdIn(List<Long> ids);

    @Query("SELECT p FROM PostEntity p WHERE p.city = :city AND p.userEntity.id NOT IN (:blockerIds)")
    List<PostEntity> findAllByCityExcludingBlockedUsers(String city, List<Long> blockerIds);

    @Query("SELECT p FROM PostEntity p WHERE p.userEntity.id NOT IN (:blockerIds)")
    List<PostEntity> findAllExcludingBlockedUsers(List<Long> blockerIds);
}