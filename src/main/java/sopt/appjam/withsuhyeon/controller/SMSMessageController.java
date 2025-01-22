package sopt.appjam.withsuhyeon.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.appjam.withsuhyeon.dto.sms.req.CertificationPhoneNumberRequest;
import sopt.appjam.withsuhyeon.dto.sms.req.VerifyCertificationNumberRequest;
import sopt.appjam.withsuhyeon.service.SMSMessageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class SMSMessageController {
    private final SMSMessageService smsMessageService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendOne(
            @RequestBody @Valid final CertificationPhoneNumberRequest certificationPhoneNumberRequest
    ) {
        smsMessageService.sendMessage(certificationPhoneNumberRequest.phoneNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyMessage(
            @RequestBody @Valid final VerifyCertificationNumberRequest verifyCertificationNumberRequest,
            @RequestParam(value = "flow") @Pattern(regexp = "^(signin|signup)$", message = "flow는 signin 또는 signup이어야 합니다.") String flow
    ) {
        smsMessageService.verifyMessage(
                verifyCertificationNumberRequest.phoneNumber(),
                verifyCertificationNumberRequest.verifyNumber(),
                flow
        );
        return ResponseEntity.ok().build();
    }
}