package ru.avk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.avk.domain.User;
import ru.avk.service.UserService;

@Import(BaseDataConfig.class)
@Configuration
@EnableAutoConfiguration
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	InitializingBean initialize(UserService userService){
		return () -> {
			userService.save(new User("Jack", "Bauer"));
			userService.save(new User("Chloe", "O'Brian"));
			userService.save(new User("Kim", "Bauer"));
			userService.save(new User("David", "Palmer"));
			userService.save(new User("Michelle", "Dessler"));

			// fetch all Users
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User user : userService.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// fetch an individual User by ID
			User user = userService.findOne(1L);
			log.info("User found with findOne(1L):");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

			// fetch Users by last name
			log.info("User found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (User bauer : userService
					.findByLastNameStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");; ;
		};
	}
}
