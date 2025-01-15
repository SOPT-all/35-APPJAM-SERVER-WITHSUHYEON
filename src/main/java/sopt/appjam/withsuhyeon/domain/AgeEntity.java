package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "age", nullable = false)
    private String age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Builder
    public AgeEntity(String age, PostEntity postEntity) {
        this.age = age;
        this.postEntity = postEntity;
    }
}
