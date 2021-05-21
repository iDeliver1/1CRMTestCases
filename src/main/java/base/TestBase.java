package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelLibraries;
import utils.ExtentReport;
import utils.TestUtil;
import utils.WebEventListener;

public class TestBase {
	protected static WebDriver driver;
	protected static PageBase basePage;
	public static Properties prop;
	public boolean checkBlnMethod;
	public boolean testStatus;
	static Logger LOGGER =    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/"
					+ "config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@BeforeSuite
	public void extentFileCreation() {
	
		ExtentReport.createReportFile(prop.getProperty("reportTitle"));
	}
	
	@BeforeTest
    public void launchApplication(ITestContext context) throws Throwable {


	     
		ExcelLibraries.createExcel(getClass().getSimpleName());
		ExtentReport.updateReportName(getClass().getSimpleName());
		testStatus = ExcelLibraries.getTestStatus(getClass().getSimpleName());
		if(!testStatus) {
			ExtentReport.skipReport();
			throw new SkipException(getClass().getSimpleName()+" has been skipped");
		}
		
		setDriverProperty();
		driver.get(prop.getProperty("AppUrl"));
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
	}

	
	public void reporting(String desc,String exp,String actual,String status) throws Throwable {
		
		if(status.equalsIgnoreCase("PASS")) {
			ExtentReport.Report("Pass", desc, actual, exp);
		}else {
			ExtentReport.Report("Fail", desc, actual, exp);
		}
	}
	
	public static void log(String data) {
		
		LOGGER.info(data);
		Reporter.log(data);
	}
	
    @AfterTest
    public static void closeBrowser() throws Throwable {
    	ExcelLibraries.setEndTime();
    	ExcelLibraries.resetCount();
        driver.close();
        driver.quit();
    }
    
    private static void setDriverProperty() throws Throwable{
    	String BrowserVersion = TestUtil.getBrowserVersion();	
		System.out.println("Browser Version- "+BrowserVersion);
		WebDriverManager.chromedriver().version(BrowserVersion).setup();
		driver = new ChromeDriver(); 
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
}
