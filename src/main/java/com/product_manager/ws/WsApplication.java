package com.product_manager.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//Redis annotation
@EnableCaching
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
