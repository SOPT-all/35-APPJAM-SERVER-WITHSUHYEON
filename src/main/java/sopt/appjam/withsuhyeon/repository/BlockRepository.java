package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sopt.appjam.withsuhyeon.domain.BlockEntity;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    Optional<BlockEntity> findByUserEntityIdAndPhoneNumber(Long userId, String phoneNumber);
//    @Modifying
//    @Query("DELETE FROM BlockEntity b WHERE b.userEntity.id = :userId AND b.phoneNumber = :phoneNumber")
//    void deleteByUserEntity_IDAndPhoneNumber(Long userId, String phoneNumber);

    @Modifying
    @Query("DELETE FROM BlockEntity b WHERE b.phoneNumber = :phoneNumber")
    void deleteByPhoneNumber(String phoneNumber);
}