package com.staj2023backend.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

//	@Bean
//	CommandLineRunner createInitialUsers(UserService userService) {
//		return (args) -> {
//			Users user = new Users();
//			user.setUsername("user1");
//			user.setDisplayName("display1");
//			user.setPassword("P4ssword");
//			userService.save(user);
//
//		};
//	}

}
