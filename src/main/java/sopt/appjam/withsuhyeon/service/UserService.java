package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.user.res.UserInfoResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRetriever userRetriever;

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(final long userId) {
        UserEntity userEntity = userRetriever.findByUserId(userId);

        return UserInfoResponse.of(userEntity.getNickname(), userEntity.getProfileImage().getValue());
    }
}