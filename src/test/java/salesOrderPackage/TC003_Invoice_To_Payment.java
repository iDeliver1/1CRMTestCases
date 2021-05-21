package salesOrderPackage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import pages.*;
import utils.ExcelLibraries;

public class TC003_Invoice_To_Payment extends TestBase {
	PaymentPage pgPay;
	DashboardPage pgDashboard;
	LoginPage pgLogin;
	
	@DataProvider
	public Object[][] testData() throws Throwable {
		Object[][] testObjArray = ExcelLibraries.getTableArray(getClass().getSimpleName());
		return testObjArray;
	}
	
	@Test(dataProvider = "testData")
	public void convertInvoiceToPayment(String userName, String password, String subject, String note, String billAccount,String shippingAc, String product, String itemCount, String cost, String slipNo) throws Throwable {
		//Login
				 pgLogin = new LoginPage(driver);
				 pgDashboard = pgLogin.userLogin(userName,password);
				try {
					Assert.assertNotNull(pgDashboard);
					reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
					
				}catch(AssertionError E) {
					reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
				}
				
				
			 pgPay =  (PaymentPage) pgDashboard.clickOnTab("Invoice");
			String orderNo = ExcelLibraries.getExcelOutput(ExcelLibraries.getTestColValue("Order Number"));
			 pgPay.selectInvoiceNumber(orderNo);
			 checkBlnMethod =  pgPay.createPayment();
			 
			 try {
				 	Assert.assertEquals(true, checkBlnMethod);
				 
				 	ExcelLibraries.setExcelOutput("Payment  Number", orderNo);
					
					ExcelLibraries.setExcelOutput("Payment  Status", pgPay.validatePaymentStatus());
				 
			 }catch(Exception E) {
				 
			 }
	 
	}
}
