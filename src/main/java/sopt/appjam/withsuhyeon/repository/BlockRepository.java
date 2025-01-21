package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.appjam.withsuhyeon.domain.BlockEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;

import java.util.List;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    Boolean existsByPhoneNumberAndUserEntity(String phoneNumber, UserEntity userEntity);

    void deleteByPhoneNumberAndUserEntity(String phoneNumber, UserEntity userEntity);

    List<BlockEntity> findByUserEntity(UserEntity userEntity);

    @Query("SELECT b.userEntity.id FROM BlockEntity b WHERE b.phoneNumber = :phoneNumber")
    List<Long> findUserIdsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}