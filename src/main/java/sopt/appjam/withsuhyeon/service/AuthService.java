package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.auth.req.SignInRequestDto;
import sopt.appjam.withsuhyeon.dto.auth.req.SignUpRequestDto;
import sopt.appjam.withsuhyeon.dto.auth.res.JwtTokensDto;
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
                .profileImage(signUpRequestDto.profileImage())
                .region(signUpRequestDto.region())
                .build();

        return userRepository.save(userEntity);
    }

    @Transactional
    public JwtTokensDto login(SignInRequestDto signInRequestDto) {
        UserEntity user = userRepository.findUserEntityByPhoneNumber(signInRequestDto.phoneNumber())
                .orElseThrow(); // 예외 상황 추가

        return jwtUtil.generateTokens(user.getId());
    }
}
