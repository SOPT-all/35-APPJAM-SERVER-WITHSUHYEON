package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.appjam.withsuhyeon.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}