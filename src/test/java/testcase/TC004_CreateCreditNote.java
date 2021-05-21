package testcase;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.TestBase;
import pages.CreditNotesPage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ExcelLibraries;

public class TC004_CreateCreditNote extends TestBase {
	
	String status,orderNo;

	@DataProvider
	public Object[][] testData() throws Throwable {
		Object[][] testObjArray = ExcelLibraries.getTableArray(getClass().getSimpleName());
		return testObjArray;
	}
	
	@Test(dataProvider = "testData")
	public void CreateCreditNote(String userName, String password, String subject, String note, String billAccount, String shippingAc, String product, String itemCount, String cost, String slipNo) throws Throwable {
		
		
	
		
		//Login
		LoginPage pgLogin = new LoginPage(driver);
		DashboardPage pgDashboard = pgLogin.userLogin(userName,password);
		try {
			Assert.assertNotNull(pgDashboard);
			reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
			
		}catch(AssertionError E) {
			reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
		}
		
		//Click on credit Notes
		CreditNotesPage pgCredit =  (CreditNotesPage) pgDashboard.clickOnTab("Credit Notes");
		
		//Enter subject and details
		pgCredit.enterCreditNotedetails(subject);
		
		//Enter bill Account
		pgCredit.billAccount(billAccount);
		
		//Add Product and Save
		checkBlnMethod = pgCredit.addProduct();
		
		try {
			Assert.assertEquals(checkBlnMethod, true);
			orderNo = pgCredit.getInvoiceInfo();
			reporting("Credit Note Validation", "Credit Note should be created", "Credit Note created Successfully against Invoice No- "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Credit Note Validation", "Credit Note should be created", "Creadit Note creation Failed", "Fail");
		}
		
		
		String Value = ExcelLibraries.getTestColValue("Output");
		System.out.println("----"+ExcelLibraries.getExcelOutput(Value)+"---");
	}

}
