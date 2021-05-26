package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {
	protected static WebDriver pbDriver;
	private WebDriverWait wait;
	private static final int iTimeOut = 20; //seconds
	private  static final int iPolling = 100; //milliseconds
	
	    
	public void setWebDriver(WebDriver driver) {
		PageBase.pbDriver = driver;
		wait = new WebDriverWait(pbDriver, iTimeOut, iPolling);
	    PageFactory.initElements(new AjaxElementLocatorFactory(PageBase.pbDriver, iTimeOut), this);
	}
	
	protected void waitForElementToAppear(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
	
	public void waitDriver() throws Throwable {
		//pbDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		Thread.sleep(3000);
	}
	
	public void waitForElementToClickable(WebElement element) throws Throwable{
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	
}
