package testcase;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.TestBase;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PoNewRecordPage;
import pages.PurchaseOrderPage;
import pages.ReceivePoPage;
import utils.ExcelLibraries;

public class TC001_OMPurchaseOrder extends TestBase{
	
	LoginPage pgLogin;
	DashboardPage pgDashboard;
	PurchaseOrderPage pgPurchaseOrder;
	PoNewRecordPage pgPoNewRecord;
	ReceivePoPage pgReceive;
	
	String poStatus,orderNo;
	
	@DataProvider
	public Object[][] testData() throws Throwable {
		Object[][] testObjArray = ExcelLibraries.getTableArray(getClass().getSimpleName());
		return testObjArray;
	}
	
	@Test(dataProvider = "testData")
	public void OMPurchaseOrder(String userName, String password, String subject, String note, String supplier, String shippingAc, String product, String itemCount, String cost, String slipNo) throws Throwable {
		//Login
	
		pgLogin = new LoginPage(driver);
		pgDashboard = pgLogin.userLogin(userName,password);
		try {
			Assert.assertNotNull(pgDashboard);
			reporting("Login Validation", "User should log in", "User Logged in Successfully", "Pass");
			
		}catch(AssertionError E) {
			reporting("Login Validation", "User should log in", "User Login Failed", "Fail");
		}
		
		//click purchase order
		pgPurchaseOrder = (PurchaseOrderPage) pgDashboard.clickOnTab("Purchase Orders");
		
		//create purchase order
		pgPoNewRecord = pgPurchaseOrder.createPurchaseOrder();
		
		//Fill details and save new PO
		pgReceive = pgPoNewRecord.createNewPo(subject,note,supplier,shippingAc,product);
		
		try {
			Assert.assertNotNull(pgReceive);
			poStatus = pgPoNewRecord.poStatus();
			orderNo = pgPoNewRecord.getPoNo();
			ExcelLibraries.setExcelOutput("PO Status", poStatus);
			System.out.println("Output is "+ExcelLibraries.getExcelOutput("PO Status"));
			reporting("Purchase Order Validation", "Purchase Order should be created", "Purchase Order created Successfully with PO No- "+orderNo, "Pass");
			
		}catch(AssertionError E) {
			reporting("Purchase Order Validation", "Purchase Order should be created", "Purchase Order creation Failed", "Fail");
		}
		
		//Receive purchase order
		 checkBlnMethod = pgReceive.recievePo(itemCount, cost, slipNo);
		 try {
			 Assert.assertEquals(checkBlnMethod, true);
			 poStatus =pgReceive.poStatus();
			 ExcelLibraries.setExcelOutput("PO Status 1", poStatus);
			 System.out.println("Output is "+ExcelLibraries.getExcelOutput("PO Status 1"));
			 reporting("Receive Order Validation", "Purchase Order should be Received", "Order Received with PO status "+poStatus, "Pass");
		 }
		 catch(AssertionError E) {
			 reporting("Receive Order Validation", "Purchase Order should be Received", "Order Receive Failed", "Fail");
			 E.printStackTrace();
		 }
		
	}

}
