package com.ssafy.letcipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LetcipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetcipeApplication.class, args);
	}

}
