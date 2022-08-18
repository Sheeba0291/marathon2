package marathon;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/*
01) Launch https://login.salesforce.com/ in Edge
02) Login to Salesforce with your username and password
03) Click on the App Launcher (dots)
04) Click View All
05) Type Individuals on the Search box
06) Click Individuals Link
07) Click New
08) Select Salutation with data (coming from excel) 
09) Type Last Name 
10) Click Save
11) Click on the App Launcher (dots)
12) Click View All
13) Type Customers on the Search box
14) Click Customers Link
15) Click New
16) Type the same name provided in step 8 and confirm it appears
17) Close the browser
 */

public class SalesForce_Individual_n_Customer extends BaseClass_SalesForce{
	
	@BeforeTest
	public void setBrowser()
	{
		browserName = "Edge";
	}
	
	@DataProvider (name = "individuals")
	public String[][] fetch() throws IOException
	{
		String[][] readData = ReadExcel.readData("Marathon_IndividualName");
		return readData;
		
	}

	@Test (dataProvider = "individuals")
	public void individual(String salutation, String lastName) throws InterruptedException {

		// 05) Type Individuals on the Search box
		driver.findElement(By.xpath("(//input[@type='search'])[3]")).sendKeys("Individuals");

		// 06) Click Individuals Link
		driver.findElement(By.xpath("//p[@class='slds-truncate']")).click();

		// 07) Click New
		driver.findElement(By.xpath("//div[text()='New']")).click();

		// 08) Select Salutation with data (coming from excel) 
		// String salutation = "Ms.";
		driver.findElement(By.xpath("//a[@class='select']")).click();
		driver.findElement(By.xpath("//a[@title='"+salutation+"']")).click();
		System.out.println("Salutation Entered: "+salutation);

		// 09) Type Last Name 
		// String lastName = "Sheeba";
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(lastName);
		System.out.println("Name Entered : "+lastName);

		// 10) Click Save
		driver.findElement(By.xpath("(//span[text()='Save'])[2]")).click();

		// 11) Click on the App Launcher (dots)
		WebElement app = driver.findElement(By.xpath("//div[@class='slds-icon-waffle']"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(app));

		driver.executeScript("arguments[0].click();", app);

		// app.click();

		// 12) Click View All
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// 13) Type Customers on the Search box
		driver.findElement(By.xpath("(//input[@type='search'])[3]")).sendKeys("Customers");

		// 14) Click Customers Link
		driver.findElement(By.xpath("//p[@class='slds-truncate']/mark")).click();

		// 15) Click New
		
        Thread.sleep(2000);
        
//    15) Click New
        driver.findElement(By.xpath("//div[@title='New']")).click();

		// 16) Type the same name provided in step 8 and confirm it appears
		driver.findElement(By.xpath("//input[contains(@class,'default input uiInput')]")).sendKeys(lastName);

		List<WebElement> titles = driver.findElements(By.xpath("//div[contains(@class,'primaryLabel')]"));
		
		Thread.sleep(6000);

		for (int i=0; i<titles.size(); i++)
		{
			// wait.until(ExpectedConditions.stalenessOf(titles.get(0)));
			String text = titles.get(i).getText();
			// Thread.sleep(2000);
			if (text.equals(lastName))
			{
				driver.findElement(By.xpath("//div[@title='"+lastName+"']")).click();
				System.out.println("Name is Verified");
				break;
			}
			else
			{
				System.out.println("Name is MIS-MATCH");
			}
		}
		
		
		WebElement closeWindow = driver.findElement(By.xpath("//span[text()='Close this window']"));
		driver.executeScript("arguments[0].click();", closeWindow);
		
		System.out.println("TC-002 Completed");

	}

}
