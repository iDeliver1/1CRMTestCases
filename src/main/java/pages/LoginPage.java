package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;
import utils.TestUtil;

public class LoginPage extends PageBase {
	
	@FindBy(xpath ="//span[contains(text(),'Login')]")
    private WebElement loginBtn;
	
	@FindBy(xpath = "//input[@id='login_pass']")
    private WebElement password;
	
	@FindBy(xpath = "//input[@id='login_user']")
    private WebElement userName;
	
	@FindBy(xpath = "//select[@id='login_theme']")
    private WebElement theme;
	
	@FindBy(xpath = "//div[@class='input-check']/input")
    private WebElement chkBox;
	//
	
	
	@FindBy(xpath = "//span[@id='login-error']")
    private WebElement errorBox;
	//span[@id='login-error']
	

	public LoginPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void setUserNameAndPassword(String user, String pass) {
		userName.sendKeys(user);
		password.sendKeys(pass);
	}
	
	public DashboardPage userLogin(String userName, String password) throws Throwable {
		setUserNameAndPassword(userName,password);
		TestUtil.selectItem(theme, 2);
		loginBtn.click();
		waitDriver();
		if(!errorBox.isDisplayed())
			return new DashboardPage(pbDriver);
		else
			return null;
	}
	
	public String loginValidation() {
		return pbDriver.getTitle();
	}
}

