package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.PageBase;

public class PoNewRecordPage extends PageBase {
	
	@FindBy(xpath ="//input[@id='DetailFormname-input']")
    private WebElement poSubject;
	
	@FindBy(xpath ="//div[@id='DetailFormsupplier-input']")
    private WebElement supplier;
	
	@FindBy(xpath ="//div[@id='DetailFormshipping_account-search-text']")
    private WebElement supplierSearch;
	
	@FindBy(xpath ="//input[@id='DetailFormdrop_ship-input-checkbox']")
    private WebElement addDropShip;
	
	@FindBy(xpath ="//textarea[@id='DetailFormdescription-input']")
    private WebElement note;
	
	@FindBy(xpath ="//div[@id='DetailFormshipping_account-input']")
    private WebElement shippingAccount;
	
	@FindBy(xpath ="//div[@id='DetailFormcurrency-input']")
    private WebElement currency;
	
	@FindBy(xpath ="//div[@id='DetailFormshipping_provider-input']")
    private WebElement shippingProvider;
	
	@FindBy(xpath ="//body/div[@id='line_items-name-newgroup~1-newline~3-search']/div[1]/div[1]/div[1]/input[1]")
    private WebElement product;
	
	@FindBy(xpath ="//body/div[@id='outer-body']/div[@id='main-body']/div[@id='content-main']/div[1]/form[1]/div[4]/div[1]/div[1]/div[1]/button[1]/div[1]")
    private WebElement saveBtn;
	
	@FindBy(xpath ="//div[@class='tally-footer-links column']/div[1]/span[1]")
    private WebElement addProduct;
	
	@FindBy(xpath ="//div[@class='input-field input-field-group rbullet input-block']")
    private WebElement taxCode;
	
	@FindBy(xpath ="/div[@class='tally-col-qty column text-right']//input[1]")
    private WebElement qty;
	
	@FindBy(xpath ="//div[@id='DetailFormsupplier-search-list']")
    private WebElement supplierList;
	
	@FindBy(xpath ="//div[@id='DetailFormshipping_account-search-list']//div//child::div[2]//div[2]")
    private WebElement shippingAccountList;
	
	@FindBy(xpath ="//div[contains(text(),'USPS Ground')]")
    private WebElement shippingProviderList;
	
	@FindBy(xpath ="//div[@id='DetailFormshipping_account-search-text']//child::input")
    private WebElement shippingAccountSearch;
	
	@FindBy(xpath ="//body/div[@id='line_items-name-newgroup~1-newline~3-search']/div[@id='line_items-name-newgroup~1-newline~3-search-list']/div[1]/div[2]")
    private WebElement productList;
	
	@FindBy(xpath ="//div[contains(text(),'Draft')]")
    private WebElement draft;
	
	@FindBy(xpath ="//div[@id='_form_header']//h3")
    private WebElement poNo;
	

	public PoNewRecordPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public void enterPoSubject(String subject, String poNote) throws Throwable {
		poSubject.sendKeys(subject+" Purchase Order");
		note.sendKeys(poNote);
		waitDriver();
	}
	
	public void enterSupplier(String poSupplier) throws Throwable {
		supplier.sendKeys(poSupplier);
		waitDriver();
		supplierList.click();
		addDropShip.click();
		waitDriver();
	}
	
	public void enterShippingAccount(String shippingAc) throws Throwable {
		shippingAccount.click();
		shippingAccountSearch.sendKeys(shippingAc);
		waitDriver();
		shippingAccountList.click();
	}
	
	public void enterShippingProvider() {
		shippingProvider.click();
		shippingProviderList.click();
	}
	
	public void addProduct(String poProduct) throws Throwable {
		addProduct.click();
		waitDriver();
		product.sendKeys(poProduct);
		productList.click();
	}
	
	public ReceivePoPage createNewPo(String subject, String note, String supplier, String shippingAc, String product) throws Throwable {
		enterPoSubject(subject,note);
		enterSupplier(supplier);
		enterShippingAccount(shippingAc);
		enterShippingProvider();
		addProduct(product);
		saveBtn.click();
		if(validatePo()==true)
			return new ReceivePoPage(pbDriver);	
		else
			return null;
	}
	
	public boolean validatePo() {
		//System.out.println(draft.getText());
		return draft.isDisplayed();	
	}
	
	public String poStatus() {
		return draft.getText();
	}
	
	public String getPoNo() {
		return poNo.getText();
	}

}
