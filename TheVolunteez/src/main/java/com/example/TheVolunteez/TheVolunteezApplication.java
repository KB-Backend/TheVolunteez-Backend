package com.example.TheVolunteez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TheVolunteezApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheVolunteezApplication.class, args);
	}
}
