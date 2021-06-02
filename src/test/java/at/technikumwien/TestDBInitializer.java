package at.technikumwien;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("test")
public class TestDBInitializer {
	@Autowired
	private PersonRepository personRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	public void handleApplicationReady() {
		personRepository.saveAll(
			List.of(
				new Person(Sex.MALE, "Markus", "Mustermann", LocalDate.of(1990, 1, 1), true),
				new Person(Sex.FEMALE, "Martina", "Musterfrau", LocalDate.of(1991, 7, 1), false)
			)
		);
	}
}
