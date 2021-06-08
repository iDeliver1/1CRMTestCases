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
	QuotesPage pgQuotes;
	SalesOrder pgSales;
	
	@DataProvider
	public Object[][] testData() throws Throwable {
		Object[][] testObjArray = ExcelLibraries.getTableArray(getClass().getSimpleName());
		return testObjArray;
	}
	
	@Test(dataProvider = "testData")
	public void createQuoteAndConvertToSalesOrder(String userName, String password, String subject, String note, String billAccount, String shippingAc, String product, String itemCount, String cost, String slipNo) throws Throwable {
		//Checking Browser is Open  or not 
		
		try {
			Assert.assertEquals("Login | 1CRM System", driver.getTitle());
			reporting("Launch Application Validation", "1CRM Login Page should be displayed ", "Application Launched Successfully", "Pass");
			
		}catch(Exception e) {
			reporting("Launch Application Validation", "1CRM Login Page should be displayed ", "Unable to launch Application ", "Fail");
		}
		
		//Login
		LoginPage pgLogin = new LoginPage(driver);
		DashboardPage pgDashboard = pgLogin.userLogin(userName,password);
		try {
			Assert.assertNotNull(pgDashboard);
			reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
			
		}catch(AssertionError E) {
			reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
		}
	
		try {
		//click on quotes
		 pgQuotes =  (QuotesPage) pgDashboard.clickOnTab("Quotes");
		
		//Create Quote
		 pgSales = pgQuotes.createQuotes(subject,billAccount);
		
			Assert.assertNotNull(pgSales);
			status =pgQuotes.validateStatus();
			orderNo = pgQuotes.getQuoteNo();
			ExcelLibraries.setExcelOutput("Quote Order Number", orderNo);
			
			ExcelLibraries.setExcelOutput("Quote Status", status);
			reporting("Quote Creation", "Dynamic Qoute Number should be generate", "Generated  Quote No - "+orderNo, "Pass");
			
		}catch(Throwable E) {
			reporting("Quote Creation", "Dynamic Qoute Number should be generate", "Failed to Generate Quote Number", "Fail");
		}
		
		try {//Convert to sales order
		ShippingAndInvoicePage pgShip = pgSales.convert2Sales(subject);
		
			Assert.assertNotNull(pgShip);
			status = pgSales.validateSoStatus();
			orderNo = pgSales.getSoNo();
			ExcelLibraries.setExcelOutput("Sales Order Number", orderNo);
			ExcelLibraries.setExcelOutput("SO Status", status);
			reporting("Sales Order Conversion", "Dynamic Sales Order Number should be generated ", "Generated Sales Order with SO- "+orderNo, "Pass");
			
		}catch(Throwable E) {
			reporting("Sales Order Conversion", "Dynamic Sales Order Number should be generated", "Failed to Generate Sales Order Number", "Fail");
		}	
	}
}
