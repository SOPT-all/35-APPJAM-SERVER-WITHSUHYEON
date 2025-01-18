package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.domain.BlockEntity;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.block.res.BlockNumberListResponseDto;
import sopt.appjam.withsuhyeon.dto.block.req.BlockNumberRequestDto;
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
public class BlockService {
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    @Transactional
    public BlockEntity createBlockNumber(final BlockNumberRequestDto blockNumberRequestDto, final Long blockerId) {
        //blockerId로 UserEntity 불러오기
        UserEntity userEntity = userRepository.findById(blockerId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        validatePhoneNumberFormat(blockNumberRequestDto.phoneNumber());
        validateNotOwnPhoneNumber(userEntity, blockNumberRequestDto.phoneNumber());

        //이미 등록된 경우
        if(blockRepository.existsByPhoneNumberAndUserEntity(blockNumberRequestDto.phoneNumber(), userEntity)) {
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

        UserEntity userEntity = userRepository.findById(blockerId)
                .orElseThrow(()-> BaseException.type(UserErrorCode.USER_NOT_FOUND));

        if(!blockRepository.existsByPhoneNumberAndUserEntity(phoneNumber, userEntity)) {
            throw BaseException.type(BlockErrorCode.BLOCK_NOT_FOUND);
        }
        blockRepository.deleteByPhoneNumberAndUserEntity(phoneNumber, userEntity);
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
    private void validateNotOwnPhoneNumber(UserEntity userEntity, String phoneNumber) {
        if (userEntity.getPhoneNumber().equals(phoneNumber)) {
            throw BaseException.type(BlockErrorCode.BLOCK_SELF_CALL_BAD_REQUEST);
        }
    }
}