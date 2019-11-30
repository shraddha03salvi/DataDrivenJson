package DataDrivenFrameworkJson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Facebook_Json {

	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.facebook.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void testLogin() throws IOException, ParseException {
		readWriteJson();
	}

	@AfterTest
	public void afterTest() {

		driver.close();
	}

	public String Login(String email, String password) {

		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
		driver.findElement(By.id("loginbutton")).click();

		String Text = driver.findElement(By.xpath("//div[contains(text(),'News Feed')]")).getText();
		if (Text.equals("News Feed")) {

			return "ValidUser";
		} else {
			return "InvalidUser";
		}
	}

	public void readWriteJson() throws IOException, ParseException {

		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader("Login.json");
		// Read JSON file
		Object obj = jsonParser.parse(reader);
		JSONArray usersList = (JSONArray) obj;// This prints the entire json file
		System.out.println(usersList);
		for (int i = 0; i < usersList.size(); i++) {
			JSONObject users = (JSONObject) usersList.get(i);
            System.out.println(users);//This prints every block - one json object
            JSONObject user = (JSONObject) users.get("users");
            System.out.println(user); //This prints each data in the block
            String username = (String) user.get("email");
            String password = (String) user.get("password");
           String result =Login(username,password);
           user.put("result", result);
           //Write JSON file
           FileWriter write = new FileWriter("Login.json");
           write.append(usersList.toJSONString());
           write.flush();

		}

	}
}
