package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.auth.req.SignupRequestDto;
import sopt.appjam.withsuhyeon.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
