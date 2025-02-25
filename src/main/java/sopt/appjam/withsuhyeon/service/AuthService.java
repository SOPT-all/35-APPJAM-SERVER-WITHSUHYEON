package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.constant.ProfileImage;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.auth.req.SignInRequestDto;
import sopt.appjam.withsuhyeon.dto.auth.req.SignUpRequestDto;
import sopt.appjam.withsuhyeon.dto.auth.res.JwtTokensDto;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.UserRepository;
import sopt.appjam.withsuhyeon.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserEntity signUp(SignUpRequestDto signUpRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .phoneNumber(signUpRequestDto.phoneNumber())
                .nickname(signUpRequestDto.nickname())
                .birthYear(signUpRequestDto.birthYear())
                .gender(signUpRequestDto.gender())
                .profileImage(ProfileImage.from(signUpRequestDto.profileImage()))
                .region(Region.fromValue(signUpRequestDto.region()))
                .build();

        return userRepository.save(userEntity);
    }

    @Transactional
    public JwtTokensDto login(SignInRequestDto signInRequestDto) {
        UserEntity user = userRepository.findUserEntityByPhoneNumber(signInRequestDto.phoneNumber())
                .orElseThrow(() -> BaseException.type(UserErrorCode.USER_NOT_REGISTERED)); // 예외 상황 추가

        return jwtUtil.generateTokens(user.getId());
    }
}
