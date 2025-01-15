package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "detail_area", nullable = false)
    private String detailArea;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "price")
    private Integer price;

    @Column(name = "title", nullable = false)
    @Size(max = 30)
    private String title;

    @Column(name = "content", nullable = false)
    @Size(max = 200)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "matching")
    private Boolean matching;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    @Builder
    public PostEntity(
            boolean gender,
            String area,
            String detailArea,
            LocalDateTime date,
            Integer price,
            String title,
            String content,
            LocalDateTime createdAt,
            Boolean matching,
            UserEntity userEntity
    ) {
        this.gender = gender;
        this.area = area;
        this.detailArea = detailArea;
        this.date = date;
        this.price = price;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.matching = matching;
        this.userEntity = userEntity;
    }


}
