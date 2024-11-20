package com.ganzithon.go_farming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoFarmingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoFarmingApplication.class, args);
	}

}
