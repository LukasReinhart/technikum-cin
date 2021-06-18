package at.technikumwien;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("!test")
public class DBInitializer {
	@Autowired
	private PersonRepository personRepository;
	
	@PreDestroy
	public void done() throws Throwable {   // for demonstration purposes only
		finalize();   // violates SonarQube rule "The Object.finalize() method should not be called"
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void handleApplicationReady() {
		personRepository.saveAll(
			List.of(
				new Person(Sex.MALE, "Markus", "Mustermann", LocalDate.of(1990, 1, 1), true),
				new Person(Sex.FEMALE, "Martina", "Musterfrau", LocalDate.of(1991, 7, 1), true)
			)
		);
	}
}
