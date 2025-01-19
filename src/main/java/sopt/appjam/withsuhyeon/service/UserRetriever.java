package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserRetriever {
    private final UserRepository userRepository;

    public UserEntity findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> BaseException.type(UserErrorCode.USER_NOT_FOUND));
    }
}
