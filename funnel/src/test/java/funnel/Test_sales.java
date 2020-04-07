package funnel;

//Importing all packages here.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class Test_sales {
	
	String result = "";
	InputStream inputStream;
	WebDriver driver ; //Declaring Webdriver object. 
	
	@BeforeMethod
	public void setup() throws IOException {
		FileReader reader;
		Properties p=new Properties();  
		try {
			reader = new FileReader("/Users/nishantmakkar/eclipse-workspace/funnel/src/test/java/funnel/test.prop");
			
		    p.load(reader);  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
		String browser = p.getProperty("browser");
		if (browser.toUpperCase().equals("chrome".toUpperCase())) {
		System.setProperty("webdriver.chrome.driver", "~/chromedriver"); //SetChromeDriver Location
		//Open, Maximize ChromeDriver and got to a specific URL.
		driver = new ChromeDriver();
		}
		
		if (browser.toUpperCase().equals("firefox".toUpperCase())) {
		System.setProperty("webdriver.gecko.driver","/geckodriver");
		 driver = new FirefoxDriver();
		}
		
		if (browser.toUpperCase().equals("ie".toUpperCase())) {
			System.setProperty("webdriver.ie.driver","/IEdriver");
			 driver = new InternetExplorerDriver();
			}
		
		driver.manage().window().maximize();
		driver.get("https://hello.friday.de/quote/selectPrecondition");		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		//Add 5 second implicit wait for finding webelements.
		
	}
	
	
	@Test(dataProvider="German") //Test Case with different data set.
	public void test_cars(String WebElement_key , String[] Webelement_value) {
		
		
		driver.findElement(By.id("uc-btn-accept-banner")).click(); //Accepting alert.
		
		
		for (String webelement : Webelement_value) {
			driver.findElement(By.xpath(".//*[text()='" + webelement + "']")).click(); //Navigating via selecting Car details.
		}
		
		WebElement dob_element = driver.findElement(By.name("monthYearFirstRegistered")); //DOB WebElement
		
		
		if (dob_element.isDisplayed()) { //If DOB is found , return .
			return;
			
		}
		
		
	}
	@Test
	public void test_cars_negative() {
		driver.findElement(By.id("uc-btn-accept-banner")).click(); //Accepting alert.
		
		//Checking Scenario with empty date
		driver.findElement(By.name("inceptionDate")).clear();
		Assert.assertEquals(true, driver.findElement(By.xpath(".//*[text()='Erforderlich']")).isDisplayed());
		
		//Checking Scenario with past date
		driver.findElement(By.name("inceptionDate")).sendKeys("01.01.2020");;
		Assert.assertEquals(true, driver.findElement(By.xpath(".//*[text()='Hups! Dieses Datum liegt in der Vergangenheit. Bitte überprüfe deine Eingabe.']")).isDisplayed());

	}
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
	 
	// Here will compare if test is failing then only it will enter into if condition
	if(ITestResult.FAILURE==result.getStatus())
	{
	try 
	{
	// Create refernce of TakesScreenshot
	TakesScreenshot ts=(TakesScreenshot)driver;
	 
	// Call method to capture screenshot
	File source=ts.getScreenshotAs(OutputType.FILE);
	 
	// Copy files to specific location here it will save all screenshot in our project home directory and
	// result.getName() will return name of test case so that screenshot name will be same
	FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));
	
	 
	System.out.println("Screenshot taken");
	} 
	catch (Exception e)
	{
	 
	System.out.println("Exception while taking screenshot "+e.getMessage());
	} 
	 
	 
	 
	}
	// close application
	//driver.quit();
	}
	
@DataProvider(name="German")
public static Object[][] getDataFromDataprovider(){
	            return new Object[][] {
	            	
	            	//3 cars data set with different brands and models.
	            	
	                { "data1",new String[]{ "Weiter","Weiter","BMW","5er","Limousine","Hybrid","225 kW / 306 PS","HSN: 0005, TSN: BHF"}},
	                { "data2",new String[]{ "Weiter","Weiter","HONDA","Jazz","Hybrid","JAZZ HYBRID 1.4"}},
	                { "data3",new String[]{ "Weiter","Weiter","HYUNDAI","Pony","43 kW / 58 PS","PONY 1,3"}},
	               
	            };  
	}

}
