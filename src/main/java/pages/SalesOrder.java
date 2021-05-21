package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;
import utils.TestUtil;

public class SalesOrder extends PageBase {

	
	@FindBy(xpath = "//div//button[contains(@class,'input-button first')]//child::div[1]")
	public WebElement createOrder;
	
	@FindBy(xpath = "//textarea[@id='DetailFormdescription-input']")
	public WebElement note;
	
	@FindBy(xpath = "//input[@id='DetailFormname-input']")      
	public WebElement soSubject;
	
	@FindBy(xpath = "//div[@class='input-label datetime-label text-number']")
	public WebElement dueDate;
	
	@FindBy(xpath = "//div[@class='input-label datetime-label text-number text-placeholder']")
	public WebElement delDate;
	
	@FindBy(xpath = "//div[@id='DetailFormbilling_account-input']")
	public WebElement billAccount;
	
	@FindBy(xpath = "//body/div[@id='DetailFormbilling_account-search']/div[1]/div[1]/div[1]/input[1]")
	public WebElement billAccountType;
	
	@FindBy(xpath = "//div[contains(text(),'24/7 Couriers')]")
	public WebElement billAccountList;
	
	@FindBy(xpath = "//div[@id='DetailFormcurrency-input']")
	public WebElement currencyType;
	
	@FindBy(xpath = "//div[contains(text(),'US Dollar: $')]")
	public WebElement currencyTypeList;
	
	@FindBy(xpath = "//div[@id='DetailFormshipping_provider-input']")
	public WebElement shippingProvider;
	
	@FindBy(xpath = "//div[contains(text(),'UPS')]")
	public WebElement shippingProviderType;
		
	@FindBy(xpath = "//body/div[@id='outer-body']/div[@id='main-body']/div[@id='content-main']/div[1]/form[1]/div[3]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]")
	public WebElement stdTax;
	
	@FindBy(xpath = "//body/div[@id='outer-body']/div[@id='main-body']/div[@id='content-main']/div[1]/form[1]/div[3]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]//div[2]")
	public WebElement stdTaxList;
	
	@FindBy(xpath ="//div[@class='tally-footer-links column']/div[1]/span[1]")
    private WebElement addProduct;
	
	@FindBy(xpath ="//div[contains(text(),'HP LaserJet Pro M476DW MFP')]")
    private WebElement product;
	
	@FindBy(xpath ="//body/div[@id='outer-body']/div[@id='main-body']/div[@id='content-main']/div[1]/form[1]/div[4]/div[1]/div[1]/div[1]/button[1]/div[1]")
    private WebElement saveBtn;
	
	@FindBy(xpath ="//body/div[@id='DetailFormdelivery_date-calendarpopup']/div[1]/div[1]/div[1]/div[1]/input[1]")
    private WebElement delDateType;
	
	@FindBy(xpath ="//body/div[@id='DetailFormdelivery_date-calendarpopup']/div[1]/div[1]/div[1]/div[5]")
    private WebElement delDateclick;
	
	@FindBy(xpath ="//body/div[@id='DetailFormcurrency-search']/div[1]/div[1]/div[1]/input[1]")
    private WebElement currencyEnter;
	
	@FindBy(xpath ="//div[@id='_form_subheader']//h4")
    private WebElement soStatus;
	
	@FindBy(xpath ="//span[@id='DetailForm_convert-label']")
    private WebElement convertBtn;
	
	@FindBy(xpath ="//div[contains(text(),'Create Sales Order')]")
    private WebElement convert2Sales;
	
	@FindBy(xpath ="//div[@id='_form_header']//h3")
    private WebElement soNo;
	
	@FindBy(xpath ="//tbody//tr//td/div")
    private List< WebElement> salesOrderNo;
	
	@FindBy(xpath ="//tbody//tr//td")
    private List< WebElement> salesOrderNoList;
	
	//tbody/tr//td
	
	public SalesOrder(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void createSalesOrder() throws Throwable {
		waitDriver();
		createOrder.click();
	}
	
	public void salesOrder(String subject, String soNote) {
		soSubject.sendKeys(subject+" Sales Order");
		note.sendKeys(soNote);
		delDate.click();
		delDateType.sendKeys(TestUtil.getLeaveDate(5));
		delDateclick.click();
	}
	
	public void billAccount(String billAc) throws Throwable {
		billAccount.click();
		billAccountType.sendKeys(billAc);
		waitDriver();
		billAccountList.click();
		currencyType.click();
		currencyEnter.sendKeys("US");
		waitDriver();
		currencyTypeList.click();
	}
	
	public boolean addProduct() throws Throwable {
		addProduct.click();
		waitDriver();
		product.click();
		waitDriver();
		saveBtn.click();
		if(validateSoStatus().equalsIgnoreCase("Ordered")) {
			return true;
		}
		else
			return false;
	}
	
	public String validateSoStatus() {
		return soStatus.getText();
	}
	
	public ShippingAndInvoicePage convert2Sales(String subject) throws Throwable {
		waitDriver();
		waitForElementToClickable(convertBtn);
		convertBtn.click();
		convert2Sales.click();
		waitDriver();
		soSubject.clear();
		soSubject.sendKeys(subject+" Sales Order");
		note.sendKeys(subject+" Sales Order");
		saveBtn.click();
		if(validateSoStatus().equalsIgnoreCase("Ordered")) {
			return new ShippingAndInvoicePage(pbDriver);
		}
		else
			return null;
		
	}
	
	public String getSoNo() {
		return soNo.getText();
	}

	public void selectSalesOrderNo(String salesNumber) {
		salesNumber=salesNumber.replace(" — ", "");

		int rowCount  = salesOrderNoList.size();
		
		for(int i = 0;i<rowCount;i++) {
			
			String Value = salesOrderNoList.get(i).getText().replace("\n", "").replace("\r", "");
			if(Value.matches(salesNumber)) {
				
				WebElement tdValue = pbDriver.findElement(By.xpath("//tbody/tr[" + (i)+ "]/td[2]/div[1]/span[1]"));
				//tbody/tr[1]/td[2]/div[1]/span[1]
			
				tdValue.click();
				break;
			}
		}
	}
	
}
