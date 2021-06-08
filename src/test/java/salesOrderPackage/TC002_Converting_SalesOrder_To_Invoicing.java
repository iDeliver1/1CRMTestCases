package salesOrderPackage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SalesOrder;
import pages.ShippingAndInvoicePage;
import utils.ExcelLibraries;

public class TC002_Converting_SalesOrder_To_Invoicing extends TestBase {
	
	LoginPage pgLogin;
	DashboardPage pgDashboard;
	SalesOrder pgSales;
	ShippingAndInvoicePage pgShip;
	
	String soStatus,orderNo,status;
	
	@DataProvider
	public Object[][] testData() throws Throwable {
		Object[][] testObjArray = ExcelLibraries.getTableArray(getClass().getSimpleName());
		return testObjArray;
	}
	
	@Test(dataProvider = "testData")
	public void salesOrderTest(String userName, String password, String subject, String note, String billAccount,String shippingAc, String product, String itemCount, String cost, String slipNo) throws Throwable {
		
		//Checking Browser is Open  or not 
		
				try {
					Assert.assertEquals("Login | 1CRM System", driver.getTitle());
					reporting("Launch Application Validation", "1CRM Login Page should be displayed ", "Application Launched Successfully", "Pass");
					
				}catch(Exception e) {
					reporting("Launch Application Validation", "1CRM Login Page should be displayed ", "Unable to launch Application ", "Fail");
				}
		
		//Login
				try {
		pgLogin = new LoginPage(driver);
		pgDashboard = pgLogin.userLogin(userName,password);
		
			Assert.assertNotNull(pgDashboard);
			reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
			
		}catch(Throwable E) {
			reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
		}
	
		//click Sales order
		try {
		pgSales = (SalesOrder) pgDashboard.clickOnTab("Sales Order");
		orderNo = ExcelLibraries.getExcelOutput(ExcelLibraries.getTestColValue("Order Number"));
		Thread.sleep(5000);
		pgSales.selectSalesOrderNo(orderNo);

	
		Assert.assertNotNull(pgSales);
		pgShip = new ShippingAndInvoicePage(driver);
		
		
		}catch(Throwable E) {
			E.printStackTrace();
		}
		
		
		
		
		try {
		pgShip.convertShipping(subject);
		checkBlnMethod = pgShip.convertStatus();
		System.out.println(ExcelLibraries.testCaseName);
		
			
			Assert.assertEquals(checkBlnMethod, true);
	
			status =pgShip.validateSoStatus();
			orderNo = pgShip.getShipNo();
			ExcelLibraries.setExcelOutput("shipping Number", orderNo);
			
			ExcelLibraries.setExcelOutput("shipping Status", status);
			
			reporting("Shipping Validation", "Dynamic Shippment Number should be Generated ", "Generated  Shippment Number - "+orderNo, "Pass");
			
		}catch(Throwable E) {
			reporting("Shipping Validation", "Dynamic Shippment Number should be Generated", "Failed to Generate Shippment Number", "Fail");
		}
		try {
		//Convert to invoice
		checkBlnMethod = pgShip.createInvoice(subject);
		
		
			Assert.assertEquals(checkBlnMethod, true);
		
			status =pgShip.validateSoStatus();
			orderNo = pgShip.getInvoiceNo();
			ExcelLibraries.setExcelOutput("Invoice Number", orderNo);
			ExcelLibraries.setExcelOutput("Invoice Status", status);
			reporting("Create Invoice Validation", "Dynamic Invoice Number should be generated ", "Generated Invoice Number - "+orderNo, "Pass");
			
		}catch(Throwable E) {
			reporting("Create Invoice Validation", "Dynamic Invoice Number should be generated", "Failed to generate Inovice Number", "Fail");
		}
		
	
		
		
		
	}
	
	
	
	

}
