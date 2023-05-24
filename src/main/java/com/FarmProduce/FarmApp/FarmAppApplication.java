package com.FarmProduce.FarmApp;

import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.service.userService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class FarmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmAppApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
