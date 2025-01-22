package sopt.appjam.withsuhyeon.config;


import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageServiceConfig {
    @Value("${sms.access-key}")
    private String accessKey;

    @Value("${sms.secret-key}")
    private String secretKey;

    @Value("${sms.endpoint}")
    private String endPoint;

    @Bean
    public DefaultMessageService messageService() {
        return NurigoApp.INSTANCE.initialize(
                accessKey, secretKey, endPoint
        );
    }
}
