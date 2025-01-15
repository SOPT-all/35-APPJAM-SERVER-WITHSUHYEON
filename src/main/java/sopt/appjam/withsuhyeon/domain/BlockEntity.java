package sopt.appjam.withsuhyeon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "block")
public class BlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "^01(?:0|1|[6-9])\\\\d{8}$", message = "전화번호 형식에 맞게 입력해야 합니다.")
    @NotNull(message = "전화번호는 필수로 입력해야 합니다.")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public BlockEntity(
            String phoneNumber,
            UserEntity userEntity
    ) {
        this.phoneNumber = phoneNumber;
        this.userEntity = userEntity;
    }
}
