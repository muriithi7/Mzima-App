package com.FarmProduce.FarmApp;

import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.service.userService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication()
@EnableWebSecurity
@EnableJpaRepositories

@OpenAPIDefinition(
		info = @Info(
				title = "Mzima App mobile app back end",
				version = "1.0.0",
				description = "a Mobile app for farm produce",
				contact = @Contact(
						name = "kelvin Wachira",
						email = "muriithi.wachira7@gmail.com"

				),
				license = @License(
						name = "@Muriithi7"
				)

		)
)
public class FarmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmAppApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
