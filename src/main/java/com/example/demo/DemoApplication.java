package com.example.demo;

import com.example.demo.user.appRole;
import com.example.demo.user.appRoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(appRoleRepo repository) {
		return args -> {
			if (repository.findByRoleName("USER") == null) {
				repository.save(new appRole("USER"));
			}
			if (repository.findByRoleName("ADMIN") == null) {
				repository.save(new appRole("ADMIN"));
			}
		};
	}

}
