package salesOrderPackage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.TestBase;
import pages.DashboardPage;
import pages.LoginPage;
import pages.QuotesPage;
import pages.SalesOrder;
import pages.ShippingAndInvoicePage;
import utils.ExcelLibraries;

public class TC001_Creating_Qoute_Converting_To_SalesOrder extends TestBase {
	
	String status,orderNo;
	
	@DataProvider
	public Object[][] testData() throws Throwable {
		Object[][] testObjArray = ExcelLibraries.getTableArray(getClass().getSimpleName());
		return testObjArray;
	}
	
	@Test(dataProvider = "testData")
	public void createQuoteAndConvertToSalesOrder(String userName, String password, String subject, String note, String billAccount, String shippingAc, String product, String itemCount, String cost, String slipNo) throws Throwable {
		
		//Login
		LoginPage pgLogin = new LoginPage(driver);
		DashboardPage pgDashboard = pgLogin.userLogin(userName,password);
		try {
			Assert.assertNotNull(pgDashboard);
			reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
			
		}catch(AssertionError E) {
			reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
		}
	
	
		//click on quotes
		QuotesPage pgQuotes =  (QuotesPage) pgDashboard.clickOnTab("Quotes");
		
		//Create Quote
		SalesOrder pgSales = pgQuotes.createQuotes(subject,billAccount);
		try {
			Assert.assertNotNull(pgSales);
			status =pgQuotes.validateStatus();
			orderNo = pgQuotes.getQuoteNo();
			ExcelLibraries.setExcelOutput("Quote Order Number", orderNo);
			
			ExcelLibraries.setExcelOutput("Quote Status", status);
			reporting("Quote Creation", "Quote should be created", "Quote created Successfully with Quote No - "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Quote Creation", "Quote should be created", "Quote creation Failed", "Fail");
		}
		
		//Convert to sales order
		ShippingAndInvoicePage pgShip = pgSales.convert2Sales(subject);
		try {
			Assert.assertNotNull(pgShip);
			status = pgSales.validateSoStatus();
			orderNo = pgSales.getSoNo();
			ExcelLibraries.setExcelOutput("Sales Order Number", orderNo);
			ExcelLibraries.setExcelOutput("SO Status", status);
			reporting("Sales Order Conversion", "Quote should convert to sales order", "Quote Converted to Sales Order with SO- "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Sales Order Conversion", "Quote should convert to sales order", "Sales Order conversion Failed", "Fail");
		}
		
		
		
		//----------------------------------------Next TestCase--------------------------------------------------
		
		//Create shipping
		/*pgShip.convertShipping(subject);
		checkBlnMethod = pgShip.convertStatus();
		
		try {
			Assert.assertEquals(checkBlnMethod, true);
			status =pgShip.validateSoStatus();
			orderNo = pgShip.getShipNo();
			
			ExcelLibraries.setExcelOutput("shipping Status", status);
			
			reporting("Shipping Validation", "Sales Order should be Shipped", "Sales Order shipped Successfully with shipping No- "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Shipping Validation", "Sales Order should be Shipped", "Sales Order shipping Failed", "Fail");
		}
		
		//Convert to invoice
		checkBlnMethod = pgShip.createInvoice(subject);
		
		try {
			Assert.assertEquals(checkBlnMethod, true);
			status =pgShip.validateSoStatus();
			orderNo = pgShip.getShipNo();
			ExcelLibraries.setExcelOutput("Invoice Status", status);
			reporting("Create Invoice Validation", "Sales Order Invoice should be created", "Invoice created Successfully with Invoice No- "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Create Invoice Validation", "Sales Order Invoice should be created", "Invoice creation Failed", "Fail");
		}

		
		String Value = ExcelLibraries.getTestColValue("Output");
		System.out.println("----"+ExcelLibraries.getExcelOutput(Value)+"---");*/
	}
}
