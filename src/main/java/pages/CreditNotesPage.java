package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;

public class CreditNotesPage extends PageBase {
	
	@FindBy(xpath = "//div//button[contains(@class,'input-button first')]//child::div[1]")
	public WebElement createBtn;
	
	@FindBy(xpath = "//input[@id='DetailFormname-input']")
	public WebElement subject;
	
	@FindBy(xpath = "//div[@id='DetailForminvoice-input']")
	public WebElement creditForInvoice;
	
	@FindBy(xpath = "//div[@class='menu-option single'][1]")
	public WebElement creditForInvoiceList;
	
	@FindBy(xpath = "//input[@id='DetailFormapply_credit_note-input-checkbox']")
	public WebElement applyAgainstInvoiceBtn;
	
	@FindBy(xpath = "//div[@id='DetailFormbilling_account-input']")
	public WebElement billAccount;
	
	@FindBy(xpath = "//body/div[@id='DetailFormbilling_account-search']/div[1]/div[1]/div[1]/input[1]")
	public WebElement billAccountType;
	
	@FindBy(xpath = "//div[contains(text(),'24/7 Couriers')]")
	public WebElement billAccountList;
	
	@FindBy(xpath ="//div[@class='tally-footer-links column']/div[1]/span[1]")
    private WebElement addProduct;
	
	@FindBy(xpath ="//div[contains(text(),'HP LaserJet Pro M476DW MFP')]")
    private WebElement product;
	
	@FindBy(xpath ="//body/div[@id='outer-body']/div[@id='main-body']/div[@id='content-main']/div[1]/form[1]/div[4]/div[1]/div[1]/div[1]/button[1]/div[1]")
    private WebElement saveBtn;
	
	@FindBy(xpath ="//span[@id='DetailForm_invoice_info']")
    private WebElement invoiceInfo;
	
	@FindBy(xpath ="//div[@id='_form_header']//h3")
    private WebElement creditNo;
	

	public CreditNotesPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void enterCreditNotedetails(String creditSubject) throws Throwable {
		waitDriver();
		createBtn.click();
		waitDriver();
		subject.sendKeys(creditSubject+" Credit Note");
		creditForInvoice.click();
		creditForInvoiceList.click();
		applyAgainstInvoiceBtn.click();
	}
	
	public void billAccount(String billAc) throws Throwable {
		waitDriver();
		billAccount.click();
		billAccountType.sendKeys(billAc);
		waitDriver();
		billAccountList.click();
	}
	
	public boolean addProduct() throws Throwable {
		addProduct.click();
		waitDriver();
		product.click();
		waitDriver();
		saveBtn.click();
		waitDriver();
		if(validate()==true)
			return true;
		else
			return false;
	}
	
	public String getInvoiceInfo() {
		return invoiceInfo.getText();
	}
	
	public String getCreditNoteNo() {
		return creditNo.getText();
	}
	
	public boolean validate() {
		return creditNo.isDisplayed();
	}

}
