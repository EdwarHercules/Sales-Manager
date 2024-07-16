package com.juice.top;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class JuicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuicesApplication.class, args);
	}

}
