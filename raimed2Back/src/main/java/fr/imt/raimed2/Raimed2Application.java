package fr.imt.raimed2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class Raimed2Application {

	public static void main(String[] args) {
		SpringApplication.run(Raimed2Application.class, args);
	}

}
