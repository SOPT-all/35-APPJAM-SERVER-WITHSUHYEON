package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.appjam.withsuhyeon.domain.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String phoneNumber);
}