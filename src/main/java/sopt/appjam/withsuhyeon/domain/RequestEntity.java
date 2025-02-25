package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.appjam.withsuhyeon.constant.RequestInfo;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_info")
    private RequestInfo requestInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Builder
    public RequestEntity(
            RequestInfo requestInfo,
            PostEntity postEntity
    ) {
        this.requestInfo = requestInfo;
        this.postEntity = postEntity;
    }
}
