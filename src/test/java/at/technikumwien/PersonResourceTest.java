package at.technikumwien;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// integration test

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("extended")
public class PersonResourceTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper om;
	
	@Test
	public void retrieve() throws Exception {
		var requestBuilder = MockMvcRequestBuilders
			.get("/resources/persons/1")
			.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
			.perform(requestBuilder)
			// variant 1: uses expect methods
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(1));
	}
	
	@Test
	public void retrieveAll() throws Exception {
		var requestBuilder = MockMvcRequestBuilders
			.get("/resources/persons")
			.accept(MediaType.APPLICATION_JSON);
		
		var response = mockMvc
			.perform(requestBuilder)
			.andReturn().getResponse();
		
		var jsonString = response.getContentAsString();
		var persons = om.readValue(
			jsonString,
			new TypeReference<List<Person>>() {}   // List<Person>
		);
		
		// variant 2: uses assert methods
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertThat(response.getContentType()).containsIgnoringCase(MediaType.APPLICATION_JSON_VALUE);
		assertEquals(1, persons.size());
	}
	
	// TODO add more tests here ;-)
}
