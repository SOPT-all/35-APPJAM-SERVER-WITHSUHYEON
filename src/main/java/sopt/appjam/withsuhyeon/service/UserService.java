package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.domain.BlockEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.auth.req.SignupRequestDto;
import sopt.appjam.withsuhyeon.dto.user.req.BlockNumberListResponseDto;
import sopt.appjam.withsuhyeon.dto.user.req.BlockNumberRequestDto;
import sopt.appjam.withsuhyeon.exception.BlockErrorCode;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.BlockRepository;
import sopt.appjam.withsuhyeon.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public BlockEntity createBlockNumber(BlockNumberRequestDto blockNumberRequestDto, final Long blockerId) {
        //blockerId로 UserEntity 불러오기
        UserEntity userEntity = userRepository.findById(blockerId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        validatePhoneNumberFormat(blockNumberRequestDto.phoneNumber());
        validateNotOwnPhoneNumber(blockerId, blockNumberRequestDto.phoneNumber());

        //이미 등록된 경우
        if(blockRepository.existsByPhoneNumberAndUserEntity_Id(blockNumberRequestDto.phoneNumber(), userEntity.getId())) {
            throw BaseException.type(BlockErrorCode.BLOCK_ALREADY_EXISTS_BAD_REQUEST);
        }
        BlockEntity blockEntity = BlockEntity.builder()
                .phoneNumber(blockNumberRequestDto.phoneNumber())
                .userEntity(userEntity)
                .build();

        return blockRepository.save(blockEntity);
    }

    @Transactional
    public void removeBlockNumber(final Long blockerId, final String phoneNumber) {
        validatePhoneNumberFormat(phoneNumber);
        if(!blockRepository.existsByPhoneNumberAndUserEntity_Id(phoneNumber, blockerId)) {
            throw BaseException.type(BlockErrorCode.BLOCK_NOT_FOUND);
        }
        UserEntity userEntity1 = userRepository.findById(blockerId)
                .orElseThrow(()-> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        blockRepository.deleteByPhoneNumberAndUserEntity(phoneNumber, userEntity1);
    }

    @Transactional(readOnly = true)
    public BlockNumberListResponseDto getBlockNumberList(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        List<BlockEntity> blockEntityList = blockRepository.findByUserEntity(userEntity);
        List<String> phoneNumbers = blockEntityList.stream()
                .map(BlockEntity::getPhoneNumber)
                .collect(Collectors.toList());
        //최신 등록순
        Collections.reverse(phoneNumbers);

        return new BlockNumberListResponseDto(userEntity.getNickname(), phoneNumbers);
    }

    // 전화번호 형식 검증
    private void validatePhoneNumberFormat(String phoneNumber) {
        String regex = "^01([0|1|6|7|8|9])\\d{3,4}\\d{4}$";
        if (!phoneNumber.matches(regex)) {
            throw BaseException.type(BlockErrorCode.BLOCK_FORMAT_BAD_REQUEST);
        }
    }

    //자기 번호인지 확인
    private void validateNotOwnPhoneNumber(Long userId, String phoneNumber) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        if (userEntity.getPhoneNumber().equals(phoneNumber)) {
            throw BaseException.type(BlockErrorCode.BLOCK_SELF_CALL_BAD_REQUEST);
        }
    }
}