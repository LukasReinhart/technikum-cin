package at.technikumwien;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

// integration test

@SpringBootTest
@ActiveProfiles("test")
@Tag("extended")
@Transactional   // rollbacks transaction after each test method
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // for demonstration purpose only !!!
public class PersonRepositoryTest {
	@Autowired
	private PersonRepository personRepository;
	
	@Test
	@Order(1)   // for demonstration purpose only !!!
	public void testSave() {
		var person = new Person(Sex.MALE, "Maxi", "Musterkind", LocalDate.of(2010, 12, 24), true);
		person = personRepository.save(person);
		
		assertNotNull(person.getId());
		assertEquals(3, personRepository.count()); 
	}
	
	@Test
	@Order(2)   // for demonstration purpose only !!!
	public void testFindAllByActiveTrue() {
		var persons = personRepository.findAllByActiveTrue();
		
		assertEquals(1, persons.size()); 
	}
	
	// TODO add more tests here ;-)
}
