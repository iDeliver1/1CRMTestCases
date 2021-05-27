package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;
import utils.TestUtil;

public class ShippingAndInvoicePage extends PageBase {
	
	@FindBy(xpath = "//input[@id='DetailFormname-input']")      
	public WebElement soSubject;
	
	@FindBy(xpath ="//span[@id='DetailForm_convert-label']")
    private WebElement convertBtn;
	
	@FindBy(xpath ="//div[contains(text(),'Create Shipping')]")
    private WebElement createShipping;
	
	@FindBy(xpath = "//input[@id='DetailFormshipping_cost-input']")
    private WebElement shippingCost;
	
	@FindBy(xpath = "//button[@id='DetailForm_save2']")
    private WebElement saveBtn;
	
	@FindBy(xpath = "//div[3]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/button[1]/span[1]")
    private WebElement editBtn;
	
	@FindBy(xpath = "//div[3]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]")
    private WebElement statusDropDown;
	
	@FindBy(xpath = "//div[contains(text(),'Shipped')]")
    private WebElement statusShipped;
	
	@FindBy(xpath ="//div[contains(text(),'Create Invoice')]")
    private WebElement createInvoice;
	
	@FindBy(xpath ="//tbody//tr[1]//td[2]//span[@class='detailLink']//a[@class='listViewNameLink']")
    private WebElement shippingOrder;
	
	@FindBy(xpath ="//span[@id='DetailForm_so_info']")
    private WebElement soInfo;
	
	@FindBy(xpath ="//div[@id='_form_subheader']//h4")
    private WebElement orderStatus;
	
	@FindBy(xpath ="//div[@id='_form_header']//h3")
    private WebElement shipNo;
	
	@FindBy(xpath ="//div[@id='DetailFormassigned_user-input']")
    private WebElement assignedUser;
	
	@FindBy(xpath ="//body/div[@id='DetailFormassigned_user-search']/div[1]/div[1]/div[1]/input[1]")
    private WebElement assignedUserType;
	
	@FindBy(xpath ="//div[contains(text(),'ProcessBillingUser')]")
    private WebElement assignedUserSelect;
	
	//div[contains(text(),'ProcessBillingUser')]


	public ShippingAndInvoicePage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void convertShipping(String subject) throws Throwable {
		waitDriver();
		waitForElementToClickable(convertBtn);
		convertBtn.click();
		createShipping.click();
		soSubject.clear();
		soSubject.sendKeys(subject+" Shipping");
		shippingCost.sendKeys("50");
		saveBtn.click();
	}
	
	public boolean convertStatus() throws Throwable {
		waitDriver();
		   
		waitForElementToAppear(shippingOrder);
		shippingOrder.click();
		waitDriver();
	jsExecutorClickOn(editBtn);
	
		waitDriver();
		statusDropDown.click();
		waitDriver();
		
		
		jsExecutorClickOn(statusShipped);
		
	
		waitDriver();
		saveBtn.click();
		waitDriver();
		if(validate()==true) {
			return true;
		}
		else
			return false;
		
	}
	
	public boolean createInvoice(String subject) throws Throwable {
		waitDriver();
		
		soInfo.click();
		waitDriver();
		waitForElementToClickable(convertBtn);
		convertBtn.click();
		createInvoice.click();
		waitDriver();
		assignedUser.click();
		assignedUserType.sendKeys("Bill");
		waitDriver();
		assignedUserSelect.click();
		soSubject.clear();
		soSubject.sendKeys(subject+" Invoice");
		saveBtn.click();
		waitDriver();
		if(validate()==true) {
			return true;
		}
		else
			return false;
	}
	
	public boolean validate() {
		return orderStatus.isDisplayed();
	}
	
	public String validateSoStatus() {
		return orderStatus.getText();
	}
	
	public String getShipNo() throws Throwable {
		waitDriver();
		return shipNo.getText();
		
	}

}
