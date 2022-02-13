package com.selenium.example.my_first_selenium_script;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	public String URL = "https://app.simpleshow.com/login-register";
	public static WebDriver driver;
	public String text ="T3ST123";
	public static Logger logger;
	public String invalidMessage = "Invalid e-mail address";
	boolean btnLoginStatus = false;
	
	@FindBy(id="mat-input-0")
	@CacheLookup
	WebElement txtEmailAddress;
	
	@FindBy(xpath="//button[@class='mat-focus-indicator btn-success mat-raised-button mat-button-base mat-button-disabled']")
	@CacheLookup
	WebElement btnLogin;
	
	@FindBy(className="ok")
	@CacheLookup
	WebElement btnCookies;
	
	@FindBy(xpath="//mat-error[@class='mat-error ng-tns-c79-0 ng-star-inserted']")
	@CacheLookup
	WebElement alertMessage; 
	
	
	// Driver configuration before the test
	@BeforeTest
	public void setup() {
		logger = Logger.getLogger("AppTest");
		PropertyConfigurator.configure("Log4j.properties");
		logger.info("initialization of driver");
		System.setProperty("webdriver.chrome.driver", "./Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(URL);
		logger.info("driver configuration successful");
	
	}
	
	// Testing for invalid email message and disabled login button
	@SuppressWarnings("unlikely-arg-type")
	@Test
    public void testEmailCredentials() throws InterruptedException
    {
    	logger.info("Initialize the webpage");
	 	PageFactory.initElements(driver, this);
	 		 	
	 	btnCookies.click();
	 	logger.info("agree to cookies page");
	 	Thread.sleep(1000);
	 	
	 	txtEmailAddress.sendKeys(text);
	 	logger.info("enter the string in text field");
	 	
	 	btnLogin.click();
		Thread.sleep(1000);
		
		btnLoginStatus = btnLogin.isEnabled();
						
		if(driver.findElement(By.xpath("//mat-error[@class='mat-error ng-tns-c79-0 ng-star-inserted']"))
				.equals(invalidMessage) && btnLoginStatus==false )
		{	    
			logger.info("invalid email message displayed and login button disabled");
			Assert.assertTrue(true);			
		}			
	 }
    
	// Driver tear-down
    @AfterTest
    public void tearDown() {
    	driver.quit();
    }
}
