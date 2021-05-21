package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;

public class ReceivePoPage extends PageBase {
	
	@FindBy(xpath = "//span[@id='DetailForm_receive-label']")
    private WebElement receiveItBtn;
	
	@FindBy(xpath = "//input[@id='DetailFormnum_packages-input']")
    private WebElement noOfPackages;
	
	@FindBy(xpath = "//input[@id='DetailFormreceiving_cost-input']")
    private WebElement shippingCost;
	
	@FindBy(xpath = "//input[@id='DetailFormpacking_slip_num-input']")
    private WebElement packingSlipNo;
	
	@FindBy(xpath = "//button[@id='DetailForm_save2']")
    private WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='summary-subheader']//div[contains(text(),'Received')]")
    private WebElement receivedStatus;

	public ReceivePoPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void clickOnReceive() throws Throwable {
		waitDriver();
		receiveItBtn.click();
	}
	
	public void enterReceiveDetails(String itemCount, String cost, String slipNo) {
		noOfPackages.sendKeys("2");
		shippingCost.sendKeys("50");
		packingSlipNo.sendKeys(slipNo);
	}
	
	public boolean recievePo(String itemCount, String cost, String slipNo) throws Throwable {
		clickOnReceive();
		waitDriver();
		enterReceiveDetails(itemCount,cost,slipNo);
		saveBtn.click();
		waitDriver();
		if(validateReceive()==true)
			return true;
		else
			return false;
	}
	
	public boolean validateReceive() {
		//System.out.println(receivedStatus.getText());
		return receivedStatus.isDisplayed();
	}
	
	public String poStatus() {
		return receivedStatus.getText();
	}

}
