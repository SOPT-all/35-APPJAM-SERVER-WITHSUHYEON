package sopt.appjam.withsuhyeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WithsuhyeonApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithsuhyeonApplication.class, args);
	}

}
