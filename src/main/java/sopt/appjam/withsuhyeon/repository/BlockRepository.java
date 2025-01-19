package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.appjam.withsuhyeon.domain.BlockEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;

import java.util.List;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    Boolean existsByPhoneNumberAndUserEntity(String phoneNumber, UserEntity userEntity);

    void deleteByPhoneNumberAndUserEntity(String phoneNumber, UserEntity userEntity);

    List<BlockEntity> findByUserEntity(UserEntity userEntity);
}