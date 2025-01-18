package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.appjam.withsuhyeon.domain.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
