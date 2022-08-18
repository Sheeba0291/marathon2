package marathon;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/*
01) Launch https://login.salesforce.com/ in Chrome
02) Login to Salesforce with your username and password
03) Click on the App Launcher (dots)
04) Click View All
05) Type Content on the Search box
06) Click Content Link
07) Click on Chatter Tab
08) Verify Chatter title on the page
09) Click Question tab
10) Type Question with data (coming from excel)
11) Type Details with data (coming from excel)
12) Click Ask
13) Confirm the question appears
14) Close the browser
 */

public class SalesForce extends BaseClass_SalesForce
{
	@DataProvider (name = "questions")
	public String[][] fetch() throws IOException
	{
		String[][] readData = ReadExcel.readData("Marathon_Questions");
		return readData;
		
	}

	@Test (dataProvider = "questions")
	public void chatterTab(String question, String details) throws InterruptedException {
		
		// 05) Type Content on the Search box
		driver.findElement(By.xpath("(//input[@type='search'])[3]")).sendKeys("Content");
		
		// 06) Click Content Link
		driver.findElement(By.xpath("//p[@class='slds-truncate']")).click();
		
		// 07) Click on Chatter Tab
		WebElement chatter = driver.findElement(By.xpath("//span[text()='Chatter']"));
		driver.executeScript("arguments[0].click();", chatter);
		
		// 08) Verify Chatter title on the page
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleContains("Chatter"));
		
		String title = driver.getTitle();
		
		System.out.println(title);
		
		if(title.contains("Chatter"))
			System.out.println("Page Title is verified for Chatter");
		else
			System.out.println("Page title MIS-MATCH");
		
		// 09) Click Question tab
		driver.findElement(By.xpath("//span[text()='Question']")).click();
		
		// 10) Type Question with data (coming from excel)
		// String question = "How are you?";
		driver.findElement(By.xpath("//textarea[@class='cuf-questionTitleField textarea']")).sendKeys(question);
		System.out.println("Question Entered: "+question);
		
		// 11) Type Details with data (coming from excel)
		// String details = "Fine";
		driver.findElement(By.xpath("//div[contains(@class,'ql-editor ql-blank slds-rich-text-area__content')]")).sendKeys(details);
		System.out.println("Details Entered : "+details);
		
		// 12) Click Ask
		driver.findElement(By.xpath("//button[@title='Click, or press Ctrl+Enter']")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//span[@class='uiOutputText'])[3]"))));
		
		// 13) Confirm the question appears
		String output = driver.findElement(By.xpath("(//span[@class='uiOutputText'])[3]")).getText();
		
		if (output.equals(question))
			System.out.println("Question appears");
		else
			System.out.println("Question DOES NOT APPEAR");
		
		System.out.println("TC-001 Completed");
		
		
	}

}
