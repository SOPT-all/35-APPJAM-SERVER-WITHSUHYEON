package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.appjam.withsuhyeon.domain.BlockEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    Boolean existsByPhoneNumberAndUserEntity(Long userId, String phoneNumber);

    void deleteByPhoneNumberAndUserEntity(String phoneNumber, UserEntity userEntity);
}