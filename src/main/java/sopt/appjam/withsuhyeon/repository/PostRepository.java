package sopt.appjam.withsuhyeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.appjam.withsuhyeon.domain.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
