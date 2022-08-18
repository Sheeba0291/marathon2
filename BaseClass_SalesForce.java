package marathon;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass_SalesForce {

	RemoteWebDriver driver;
	String browserName = "Chrome";

	@Parameters ({"url", "userName", "password"})
	@BeforeMethod
	public void setUp(String url, String userName, String password) throws InterruptedException
	{
		if(browserName.equalsIgnoreCase("Chrome"))
		{
			WebDriverManager.chromedriver().setup();

			// disable notifications
			ChromeOptions options = new ChromeOptions();
			options.addArguments("-disable-notifications");

			// Create the chromedriver object named driver
			driver = new ChromeDriver(options);
		}
		else if (browserName.equalsIgnoreCase("Edge"))
		{
			WebDriverManager.edgedriver().setup();

			// disable notifications
			EdgeOptions options = new EdgeOptions();
			options.addArguments("-disable-notifications");

			// Create the edgedriver object named driver
			driver = new EdgeDriver(options);
		}

		// 01) Launch https://login.salesforce.com/
		driver.get(url);

		// Maximize the window
		driver.manage().window().maximize();

		// 02) Login to Salesforce with your username and password 
		driver.findElement(By.id("username")).sendKeys(userName);
		driver.findElement(By.id("password")).sendKeys(password);

		// click on the login button
		driver.findElement(By.id("Login")).click();

		Thread.sleep(4000);

		// Implicit wait for the WebElement
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 03) Click on the App Launcher (dots)
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();

		// 04) Click View All
		driver.findElement(By.xpath("//button[text()='View All']")).click();
	}

	@AfterMethod
	public void tearDown()
	{
		// 14) Close the browser
		driver.close();

	}

}
