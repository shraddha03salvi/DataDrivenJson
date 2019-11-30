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

public class JSONHandling {
	
	WebDriver driver;
	@BeforeTest
	public void beforeTest()
	{
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://demowebshop.tricentis.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}		
	
	@Test
	public void testAut() throws InterruptedException, IOException, Exception {
		
		readWriteJSON();
	}
	@AfterTest
	public void aftertest() {
		
		driver.close();
	}
	public String login(String username, String password) throws InterruptedException {
		driver.findElement(By.linkText("Log in")).click();
		Thread.sleep(2000);
		driver.findElement(By.name("Email")).sendKeys(username);
		Thread.sleep(2000);
		driver.findElement(By.name("Password")).sendKeys(password);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@class='button-1 login-button' and @value='Log in']")).click();
		Thread.sleep(2000);
		if(driver.findElements(By.xpath("//input[@id='vote-poll-1']")).size()>0)
			
		{
		String uname = driver.findElement(By.xpath("//a[@href='/customer/info']")) .getText();
		if(uname.equals(username))
		driver.findElement(By.xpath("//a[@href='/logout']")).click();
		}
		else 
		{
		driver.findElement(By.xpath("//a[@href='/login']")).click();
		return "Invalid User";
		}
		return "Valid User";
		}
	
	
		@SuppressWarnings("unchecked")
		public void readWriteJSON() throws InterruptedException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		try  {
		FileReader reader = new FileReader("Registration.json");
		//Read JSON file
		            Object obj = jsonParser.parse(reader);
		            JSONArray usersList = (JSONArray) obj;
		            System.out.println(usersList); //This prints the entire json file
		            for(int i=0;i<usersList.size();i++) {
		            JSONObject users = (JSONObject) usersList.get(i);
		            System.out.println(users);//This prints every block - one json object
		            JSONObject user = (JSONObject) users.get("users");
		            System.out.println(user); //This prints each data in the block
		            String username = (String) user.get("username");
		            String password = (String) user.get("password");
		            String result = login(username,password);
		            user.put("result", result);
		            //Write JSON file
		                try (FileWriter file = new FileWriter("Registration.json")) {
		                    file.append(usersList.toJSONString());
		                    file.flush();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            System.out.println(user);
		             }
		         } catch (FileNotFoundException e) {
		e.printStackTrace();
		}

}}
