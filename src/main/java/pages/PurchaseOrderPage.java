package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;

public class PurchaseOrderPage extends PageBase{
	
	@FindBy(css ="#listView-4ea1-create")
    private WebElement createPO;
	
	@FindBy(xpath ="//button[starts-with(@id,'listView-') and contains(@name,'SubPanel_create')]")
    private List<WebElement> createPurchaseOrder;
	
	
	public PurchaseOrderPage(WebDriver driver) {
		setWebDriver(driver);
	}
	
	public PoNewRecordPage createPurchaseOrder() {
		createPurchaseOrder.get(0).click();
		return new PoNewRecordPage(pbDriver);
	}
	
	public PoNewRecordPage convertToPurchaseOrder() {
		return null;
		
	}

}
