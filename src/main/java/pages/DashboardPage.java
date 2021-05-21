package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.PageBase;
import utils.TestUtil;

public class DashboardPage extends PageBase {
	
	@FindBy(css ="#grouptab-2")
    private WebElement omTab;
	
	@FindBy(xpath ="//a[contains(text(),'Purchase Orders')]")
    private WebElement po;
	
	@FindBy(xpath = "//a[contains(text(),'Sales Orders')]")
	public WebElement salesOrdBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Quotes')]")
	public WebElement quotesBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Credit Notes')]")
	public WebElement creditNotesBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Invoice')]")
	public WebElement invoiceBtn;
	
	
	public DashboardPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public Object clickOnTab(String tabName) throws Throwable {
		
		TestUtil.mouseHover(omTab);
		
		if(tabName.equalsIgnoreCase("Purchase orders")) {
			waitForElementToAppear(po);
			po.click();
			return new PurchaseOrderPage(pbDriver);
		}
		
		else if(tabName.equalsIgnoreCase("Sales Order")) {
			waitForElementToAppear(salesOrdBtn);
			salesOrdBtn.click();
			return new SalesOrder(pbDriver);
		}
		
		else if(tabName.equalsIgnoreCase("Quotes")) {
			waitForElementToAppear(quotesBtn);
			quotesBtn.click();
			return new QuotesPage(pbDriver);
		}
		
		else if(tabName.equalsIgnoreCase("Credit Notes")) {
			waitForElementToAppear(creditNotesBtn);
			creditNotesBtn.click();
			return new CreditNotesPage(pbDriver);
		}
		
		else if(tabName.equalsIgnoreCase("Invoice")) {
			waitForElementToAppear(invoiceBtn);
			invoiceBtn.click();
			return new PaymentPage(pbDriver);
		}
		
		else {
			return false;
		}
	}

}
