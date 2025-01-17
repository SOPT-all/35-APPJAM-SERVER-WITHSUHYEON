package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.domain.BlockEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.auth.req.SignupRequestDto;
import sopt.appjam.withsuhyeon.dto.user.req.BlockNumberRequestDto;
import sopt.appjam.withsuhyeon.repository.BlockRepository;
import sopt.appjam.withsuhyeon.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;


    @Transactional
    public UserEntity createUser(SignupRequestDto signupRequestDto) {

        UserEntity userEntity = UserEntity.builder()
                .phoneNumber(signupRequestDto.phoneNumber())
                .nickname(signupRequestDto.nickname())
                .birthYear(signupRequestDto.birthYear())
                .gender(signupRequestDto.gender())
                .profileImage(signupRequestDto.profileImage())
                .region(signupRequestDto.region())
                .build();

        return userRepository.save(userEntity);
    }

    @Transactional
    public BlockEntity createBlockNumber(BlockNumberRequestDto blockNumberRequestDto, final String blockerId) {
        //blockerId로 UserEntity 불러오기
        UserEntity userEntity = userRepository.findById(Long.parseLong(blockerId))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 blockerId입니다."));

        //자기 자신의 번호는 차단할 수 없게 ?
        BlockEntity blockEntity = BlockEntity.builder()
                .phoneNumber(blockNumberRequestDto.phoneNumber())
                .userEntity(userEntity)
                .build();

        return blockRepository.save(blockEntity);
    }

    @Transactional
    public void removeBlockNumber(final Long blockerId, final String phoneNumber) {
        Optional<BlockEntity> blockEntity = blockRepository.findByUserEntityIdAndPhoneNumber(blockerId, phoneNumber);

        if(blockEntity.isEmpty()) {
            throw new IllegalArgumentException("사용자가 해당 전화번호를 차단한 기록이 없습니다.");
        }

        UserEntity userEntity = userRepository.findById(blockerId)
                .orElseThrow();

        blockRepository.deleteByPhoneNumberAndUserEntity(phoneNumber, userEntity);
    }
}