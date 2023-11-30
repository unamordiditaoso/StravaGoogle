package es.Google;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.Google.dao.GoogleRepository;
import es.Google.model.User;

@SpringBootApplication
public class GoogleApplication {
	
	private static final Logger log = LoggerFactory.getLogger(GoogleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GoogleApplication.class, args);
	}
	
	@Bean
	CommandLineRunner demo(GoogleRepository repository) {
		return (args) -> {
			//Create some users
			repository.save(new User("John", "john.doe@example.com"));
			repository.save(new User("Ada", "ada.lovelace@example.com"));
			repository.save(new User("Alan",  "alan.turing@example.com"));
			repository.save(new User("Grace", "grace.hopper@example.com"));
			repository.save(new User("Linus", "linus.torvalds@example.com"));
			repository.save(new User("Margaret", "margaret.hamilton@example.com"));
			repository.save(new User("Steve", "steve.jobs@example.com"));
			repository.save(new User("Bill", "bill.gates@example.com"));
			repository.save(new User("Tim", "tim.bernerslee@example.com"));
			repository.save(new User("Suzanne", "suzanne.simard@example.com"));
						
			log.warn("USER Service available and waiting ...");
		};
	}
}
