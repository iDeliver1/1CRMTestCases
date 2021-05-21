package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;

public class QuotesPage extends PageBase {
	
	@FindBy(xpath = "//div//button[contains(@class,'input-button first')]//child::div[1]")
	public WebElement createBtn;
	
	@FindBy(xpath = "//input[@id='DetailFormname-input']")
	public WebElement quoteSubject;
	
	@FindBy(xpath = "//textarea[@id='DetailFormdescription-input']")
	public WebElement note;
	
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
	
	@FindBy(xpath ="//div[@id='DetailFormvalid_until-input']")
    private WebElement validUntil;
	
	@FindBy(xpath ="//body/div[@id='DetailFormvalid_until-calendarpopup']/div[1]/div[1]/div[1]/div[1]/input[1]")
    private WebElement validUntilType;
	
	@FindBy(xpath ="//body/div[@id='DetailFormvalid_until-calendarpopup']/div[1]/div[1]/div[1]/div[5]")
    private WebElement validUntilClick;
	
	@FindBy(xpath ="//div[@id='_form_subheader']//h4")
    private WebElement quoteStatus;
	
	@FindBy(xpath ="//div[@id='_form_header']//h3")
    private WebElement quoteNo;
	
	
	public QuotesPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void createQuoteSubject(String subject) throws Throwable {
		waitDriver();
		createBtn.click();
		quoteSubject.sendKeys(subject+" quote");
		note.sendKeys(subject+" quote");
	}
	
	public void billAccount(String billAc) throws Throwable {
		waitDriver();
		billAccount.click();
		billAccountType.sendKeys(billAc);
		waitDriver();
		billAccountList.click();
	}
	
	public void addProduct() throws Throwable {
		addProduct.click();
		waitDriver();
		product.click();
		waitDriver();
		saveBtn.click();
	}
	
	public SalesOrder createQuotes(String subject, String billAccount) throws Throwable {
		createQuoteSubject(subject);
		billAccount(billAccount);
		addProduct();
		if(validateStatus().equalsIgnoreCase("Draft")) {
			return new SalesOrder(pbDriver);
		}
		else
			return null;
		
	}
	
	public String validateStatus() {
		return quoteStatus.getText();
	}
	
	public String getQuoteNo() {
		return quoteNo.getText();
	}

}
