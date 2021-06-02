package at.technikumwien;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;

// e2e test

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("extended")
public class IndexTest {
	@LocalServerPort
	private long port;
	
	private WebDriver driver;
	private Wait<WebDriver> wait;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		WebDriverManager.chromedriver().setup();
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
	}
	
	@BeforeEach
	public void setUp() {
		driver = new ChromeDriver(
			new ChromeOptions().setHeadless(true)   // runs browser without GUI
		);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 3);
	}
	
	@AfterEach
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void testIndex() {
		driver.get(getUrl(""));
		
		var button = driver.findElement(By.tagName("button"));   // WebElement
		assertEquals("alle Personen anzeigen", button.getText());
		
		var lis = driver.findElements(By.tagName("li"));   // List<WebElement>
		assertEquals(1, lis.size());
		
		button.submit();
		
		wait.until(
			ExpectedConditions.urlToBe(getUrl("?all=true"))
		);
	}
	
	@Test
	public void testIndexWithAll() {
		driver.get(getUrl("?all=true"));
		
		var button = driver.findElement(By.tagName("button"));   // WebElement
		assertEquals("nur aktivierte Personen anzeigen", button.getText());
		
		var lis = driver.findElements(By.tagName("li"));   // List<WebElement>
		assertEquals(2, lis.size());
		
		button.submit();
		
		wait.until(
			ExpectedConditions.urlToBe(getUrl("?"))
		);
	}	
	
	private String getUrl(String path) {
		return "http://localhost:" + port + "/" + path;
	}
	
	// add more tests here ;-)
}
