package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.exception.RedisErrorCode;
import sopt.appjam.withsuhyeon.exception.UserErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.UserRepository;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMSMessageService {
    @Value("${sms.sender}") private String senderPhoneNumber;
    private final DefaultMessageService messageService;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public void sendMessage(
            final String phoneNumber
    ) {
        // 1) 인증번호 생성
        String verificationCode = createRandomNumber();

        // 2) Redis에 인증번호 저장 (3분 TTL)
        //    key 예시: "cert:01012341234" 처럼 prefix 써주는 경우 많음
        //    인증번호 받고 뒤로가기 눌렀다가 다시 인증 들어가는 경우 key 값 덮어쓰게 됨으로 걱정 없음
        redisTemplate.opsForValue().set(
                "cert:" + phoneNumber,
                verificationCode,
                Duration.ofMinutes(3) // 3분 TTL
        );

        // 3) 문자 보내기
        Message message = new Message();
        message.setFrom(senderPhoneNumber);
        message.setTo(phoneNumber);
        message.setText("[테스트] 인증번호는 " + verificationCode + " 입니다. 3분 내로 입력해주세요.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    public void verifyMessage(
            final String phoneNumber,
            final String verifyNumber,
            final String flow
    ) {
        // Redis에서 가져오기
        String redisVerifyNumber = redisTemplate.opsForValue().get("cert:" + phoneNumber);

        // 1) 만료 또는 존재하지 않는 경우 : 레디스 DB에 저장 X
        if(redisVerifyNumber == null) {
            throw BaseException.type(RedisErrorCode.EXPIRED_CERTIFICATION_NUMBER);
        }
        // 2) 인증번호 불일치
        if(!redisVerifyNumber.equals(verifyNumber)) {
            throw BaseException.type(RedisErrorCode.INVALID_CERTIFICATION_NUMBER);
        }
        // 모두 정상인 경우 : 레디스 DB 에서 삭제 후 휴대폰 번호 검증
        redisTemplate.delete("cert:" + phoneNumber);

        if(flow.equals("signup")) {
            // 회원가입의 경우 중복된 휴대폰 번호라면 예외 상황 : 이미 등록된 회원
            if(userRepository.existsByPhoneNumber(phoneNumber)) {
                throw BaseException.type(UserErrorCode.USER_ALREADY_REGISTERED);
            }
        } else if (flow.equals("signin")) {
            // 로그인의 경우 DB에 없는 휴대폰 번호라면 예외 상황 : DB에 없는 회원
            if(!userRepository.existsByPhoneNumber(phoneNumber)) {
                throw BaseException.type(UserErrorCode.USER_NOT_REGISTERED);
            }
        }
    }

    private String createRandomNumber() {
        Random random = new Random();
        int randomInt = 100000 + random.nextInt(900000);
        return String.valueOf(randomInt);
    }
}
