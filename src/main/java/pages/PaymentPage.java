package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.PageBase;

public class PaymentPage extends PageBase {
	
	@FindBy(xpath ="//span[@id='DetailForm_convert-label']")
    private WebElement convertBtn;
	
	@FindBy(xpath ="//div[contains(text(),'Create Payment')]")
    private WebElement createPayment;
	
	@FindBy(xpath ="//div[@id='DetailFormpayment_type-input']")
    private WebElement paymentMethod;
	
	@FindBy(xpath ="//div[@id='DetailFormpayment_type-input-popup']//div[1]//div[2]")
    private WebElement checkPayment;
	
	@FindBy(xpath ="//body/div[@id='outer-body']/div[@id='main-body']/div[@id='content-main']/div[1]/form[1]/div[4]/div[1]/div[1]/div[1]/button[1]/div[1]")
    private WebElement saveBtn;
	
	@FindBy(xpath ="//div[@id='_form_header']//h3")
    private WebElement paymentId;
	
	@FindBy(xpath ="//div[@id='_form_subheader']//h4")
    private WebElement orderStatus;
	
	@FindBy(xpath ="//tbody//tr[1]//td[2]//span[@class='detailLink']//a[contains(text(),'Test Invoice')]")
    private WebElement invoice;
	
	@FindBy(tagName = "iframe")
	private WebElement frame;
	
	@FindBy(xpath ="//tbody//tr//td")
    private List< WebElement> invoiceNoList;
	

	public PaymentPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public boolean createPayment() throws Throwable {
//		invoice.click();
		waitDriver();
		waitForElementToClickable(convertBtn);
		convertBtn.click();
		createPayment.click();
		waitDriver();
		paymentMethod.click();
		checkPayment.click();
		waitDriver();
		saveBtn.click();
		waitDriver();
		
		if(validate()==true)
			return true;
		else
			return false;
		
	}
	
	public String getPaymentId() {
		return paymentId.getText();
	}
	
	public boolean validate() {
		return paymentId.isDisplayed();
	}
	
	public String validatePaymentStatus() {
		return orderStatus.getText();
	}
	
	
	
	
	public void selectInvoiceNumber(String invoiceNumber) throws Throwable {
		waitDriver();
		invoiceNumber=invoiceNumber.replace(" — ", "");
		
int rowCount  = invoiceNoList.size();
		
		for(int i = 0;i<rowCount;i++) {
			
			String Value = invoiceNoList.get(i).getText().replace("\n", "").replace("\r", "");
			System.out.println(Value);
			if(Value.matches("invoiceNumber")) {
				
				WebElement tdValue = pbDriver.findElement(By.xpath("//tbody/tr[" + (i)+ "]/td[2]/div[1]/span[1]"));
				//tbody/tr[1]/td[2]/div[1]/span[1]
			
				tdValue.click();
				break;
			}
		}
		
	}

}
