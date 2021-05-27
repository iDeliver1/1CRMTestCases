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
		//Login
	
		pgLogin = new LoginPage(driver);
		pgDashboard = pgLogin.userLogin(userName,password);
		try {
			Assert.assertNotNull(pgDashboard);
			reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
			
		}catch(AssertionError E) {
			reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
		}
	
		//click Sales order
		
		pgSales = (SalesOrder) pgDashboard.clickOnTab("Sales Order");
		orderNo = ExcelLibraries.getExcelOutput(ExcelLibraries.getTestColValue("Order Number"));
		Thread.sleep(5000);
		pgSales.selectSalesOrderNo(orderNo);

		try {
		Assert.assertNotNull(pgSales);
		pgShip = new ShippingAndInvoicePage(driver);
		
		
		}catch(Exception E) {
			E.printStackTrace();
		}
		
		pgShip.convertShipping(subject);
		checkBlnMethod = pgShip.convertStatus();
		System.out.println(ExcelLibraries.testCaseName);
		try {
			Assert.assertEquals(checkBlnMethod, true);
			orderNo = pgShip.getShipNo();
			status =pgShip.validateSoStatus();
			
			ExcelLibraries.setExcelOutput("shipping Number", orderNo);
			
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
			ExcelLibraries.setExcelOutput("Invoice Number", orderNo);
			ExcelLibraries.setExcelOutput("Invoice Status", status);
			reporting("Create Invoice Validation", "Sales Order Invoice should be created", "Invoice created Successfully with Invoice No- "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Create Invoice Validation", "Sales Order Invoice should be created", "Invoice creation Failed", "Fail");
		}
		
	
		
		
		
	}
	
	
	
	

}
