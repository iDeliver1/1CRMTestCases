package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimeZone;
import java.util.jar.Attributes.Name;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelLibraries {
	
	static String [][] Data;
	static String excelPath = TestUtil.reportFolderPath+"\\Excel"+TestUtil.getTimeStamp()+".xlsx";
	static XSSFWorkbook workBook;
	static String reporter;
	static int iSheetIndex=0, iRowCount;
	public static int iActiveRow=1;
	static int rowCount;
	static int cellCount;
	static int iSetRow=1;
	static Sheet currentSheet;
	static Row newRow, nextRow;
	static XSSFFont customFont;
	static XSSFCellStyle style;
	static Name name;
	static CreationHelper createHelper;
	static XSSFCell cell;
	public static String testCaseName,testData ,strTestCaseName,strColName;
	
	public static  String createExcel(String Reportname) throws InvalidFormatException, IOException, Throwable{
	testCaseName = Reportname;
		try {
			reporter = Reportname;
					
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		 workBook = new XSSFWorkbook();
		if ((new File(excelPath)).exists()==false) {	
			
		
			workBook.createSheet("Sheet1").createRow(0).createCell(0);
			
			FileOutputStream fout;
			try {
				fout = new FileOutputStream(new File(excelPath));
				workBook.write(fout);
				fout.close();
				
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
			
	FileInputStream fin = new FileInputStream(excelPath);
	workBook=(XSSFWorkbook) WorkbookFactory.create(fin);
	currentSheet = workBook.getSheetAt(0);
	
	customFont =   workBook.createFont();
	customFont.setBold(true);
	style = workBook.createCellStyle();
	style.setFont(customFont);
	
	currentSheet.setDefaultColumnWidth(35);
	iRowCount = currentSheet.getLastRowNum();
	newRow = currentSheet.createRow(iRowCount+2);       //(iRowCount+1)
	newRow.createCell(0).setCellValue("Test Name - "+reporter);
	newRow.getCell(0).setCellStyle(style);
	
	String timeZone = TimeZone.getDefault().getID().toString();
	newRow.createCell(1).setCellValue("Time Zone: "+timeZone);
	
	newRow = currentSheet.createRow(iRowCount+3);      //(iRowCount+2)
	newRow.createCell(0).setCellValue("System Name - "+System.getenv("COMPUTERNAME"));
	newRow.getCell(0).setCellStyle(style);
	
	newRow.createCell(1).setCellValue("Start Time: "+TestUtil.getDatebyFormat("hh:mm:ss aa", 0));
	
	String[] Attribute = {"Step details","Expected","Actual","Status","Date"};
	//Row newRow = currentSheet.createRow(iRowCount+3);
	
	//new code
	nextRow = currentSheet.createRow(1);
	//end new code
	
  	for(int j=0;j<Attribute.length;j++)
  	{	
		//newRow.createCell(j).setCellValue(Attribute[j]);
		//newRow.getCell(j).setCellStyle(style);
  		
  		nextRow.createCell(j).setCellValue(Attribute[j]);
  		nextRow.getCell(j).setCellStyle(style);
  	}
	 
	FileOutputStream fout = new FileOutputStream(excelPath);
	workBook.write(fout);
	fout.close();
	iSheetIndex= iSheetIndex+1;
	return reporter;	
}
	
	

//----------------------------------------------Excel Reporting-------------------------------------------------------	   
	 public static void fExcelReporter(String Step_details,String Actual,String Expected,String Status,String Date) throws Throwable
		{ 
		 workBook = new XSSFWorkbook();
			
				try{
					FileInputStream fin=new FileInputStream(excelPath);
					
					workBook=(XSSFWorkbook) WorkbookFactory.create(fin);
					}
				catch(Exception e){
					System.out.println(e);
					}
			Sheet sh = workBook.getSheet("Sheet1");
			  	customFont =   workBook.createFont();
				customFont.setBold(true);
				style = workBook.createCellStyle();
			String Attribute_value[] =  {Step_details,Expected,Actual,Status,Date};
			
			Row newRow1 = sh.createRow(sh.getLastRowNum()+1);
				for(int i=0;i<=4;i++)
					{
					if(Attribute_value[i].equalsIgnoreCase("Pass")){
						customFont.setColor(IndexedColors.GREEN.getIndex());
						style.setFont(customFont);
						newRow1.createCell(i).setCellValue(Attribute_value[i]);
						newRow1.getCell(i).setCellStyle(style);
					}
					else if(Attribute_value[i].equalsIgnoreCase("Fail")){
						customFont.setColor(IndexedColors.RED.getIndex());
						style.setFont(customFont);
						newRow1.createCell(i).setCellValue(Attribute_value[i]);
						newRow1.getCell(i).setCellStyle(style);
					}
					
					//new code
					
					else if(Attribute_value[i].equalsIgnoreCase("Skipped")){
						customFont.setColor(IndexedColors.DARK_YELLOW.getIndex());
						style.setFont(customFont);
						newRow1.createCell(i).setCellValue(Attribute_value[i]);
						newRow1.getCell(i).setCellStyle(style);
					}
					
					//end code
					else{
					newRow1.createCell(i).setCellValue(Attribute_value[i]);
					}
					}	
				
			FileOutputStream fout = new FileOutputStream(excelPath);
			workBook.write(fout);
			fout.close();
		}
	   
	

	

	  
//-----------------------------Function for data provider with excel---------------------------------------	   
	   
	public static Object[][] getTableArray(String testCaseName)throws Exception{   
		String[][] tabArray = null;
	      
		try{
	      FileInputStream ExcelFile = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/"
					+ "config/dataFile.xlsx");
	      
	      workBook = new XSSFWorkbook(ExcelFile);
	      currentSheet = workBook.getSheet("sheet1");
		   
	      int startCol = 1;
	      int ci=0,cj=0;
	      int totalRows = 1;
	      int totalCols = 10;
	      tabArray=new String[totalRows][totalCols];
		      
		  int iTestCaseRow = ExcelLibraries.getRowContains(testCaseName,0);
	   
	      for (int j=startCol;j<=totalCols;j++, cj++)
	      {
		      tabArray[ci][cj]=getCellData(iTestCaseRow,j);
		      //System.out.println(tabArray[ci][cj]);
	      }
	   
	 }
		catch (FileNotFoundException e){
		   System.out.println("Could not read the Excel sheet");
		   e.printStackTrace();
	 }
		catch (IOException e){
		   System.out.println("Could not read the Excel sheet");
		   e.printStackTrace();
	   }
	  return(tabArray); 
	}
	   
//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	   
	public static String getCellData(int RowNum, int ColNum) throws Exception{   
	   try{
	     cell = (XSSFCell) currentSheet.getRow(RowNum).getCell(ColNum);
	     String CellData = cell.getStringCellValue();
	     return CellData;
	   }
	   catch (Exception e){
		 return"";
	   }
	}
	   
	   
	public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
	   int i;
	   try {
		   int rowCount = currentSheet.getLastRowNum();
		   for ( i=0 ; i<rowCount; i++){
			   if  (ExcelLibraries.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
				  break;
			   }
		   }
		   return i;
	   }
	   catch (Exception e){
		   throw(e);
	   }
	}
	
	public static boolean getTestStatus(String sTestCaseName) throws Throwable {
		
		 FileInputStream ExcelFile = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/"
					+ "config/dataFile.xlsx");
	      
	      workBook = new XSSFWorkbook(ExcelFile);
	      currentSheet = workBook.getSheet("sheet1");
	      
	      int rowCount = currentSheet.getLastRowNum();
	      
	      for(int i =0;i<=rowCount;i++) {
	    	  if(ExcelLibraries.getCellData(i,0).equalsIgnoreCase(sTestCaseName)) {
	    		  return Boolean.valueOf(ExcelLibraries.getCellData(i, 11));
	    	  }
	      }
	     return false;
	}
	
//----------------Function For Start and end time feed in excel report------------------------------
	
	public static void setEndTime() throws Throwable {
		
		workBook = new XSSFWorkbook();
		try{
			FileInputStream fin=new FileInputStream(excelPath);
			workBook=(XSSFWorkbook) WorkbookFactory.create(fin);
		}
		catch(Exception e){
			System.out.println(e);
		}
	      currentSheet = workBook.getSheet("sheet1");
	      
	      int rowNo = getRowNo("Start Time:");
	      newRow = currentSheet.getRow(rowNo);  //(rowNo-1)
	      newRow.createCell(2).setCellValue("End Time: "+TestUtil.getDatebyFormat("hh:mm:ss aa", 0));
	      
	      FileOutputStream fout = new FileOutputStream(excelPath);
	      workBook.write(fout);
	      fout.close();
	}
	    
	public static int getRowNo(String searchText) throws Exception{
		   int i,searchCount=0;
		   try {
			   int rowCount = currentSheet.getLastRowNum();
			   for ( i=0 ; i<rowCount; i++){
				   if  (ExcelLibraries.getCellData(i,1).contains(searchText)){
					  //break;   //ExcelLibraries.getCellData(i,0).equalsIgnoreCase(searchText)
					 searchCount = i;
				   }
			   }
			   return searchCount;
		   }
		   catch (Exception e){
			   throw(e);
		   }
		}
	
	//Setting Excel Output 
	
	public static void setExcelOutput(String ColumnName,String Output) throws Throwable {
		
		workBook = new XSSFWorkbook();
		try{
			FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/"
					+ "config/dataFile.xlsx");
			workBook=(XSSFWorkbook) WorkbookFactory.create(fin);
		}
		catch(Exception e){
			System.out.println(e);
		}
			
		currentSheet = workBook.getSheet("Sheet2");
		
		try {
			rowCount = currentSheet.getRow(0).getLastCellNum();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	      for(int i =1;i<=rowCount;i++) {
	    	
	    	 
	    	 if(currentSheet.getRow(0).getCell(i)!=null) {
	    		 
	    		 if(currentSheet.getRow(0).getCell(i).toString().equalsIgnoreCase(testCaseName)) {
	    			 
	 	    		
	 	    		
	 	    		if(currentSheet.getRow(iActiveRow)==null) {
	 	    			currentSheet.createRow(iActiveRow);
	 	    		}
	 	    		
	 	    		if(currentSheet.getRow(iActiveRow).getCell(i-1)==null) {
	 	    			currentSheet.getRow(iActiveRow).createCell(i-1);
	 	    		}
	 	    		
	 	    		if(currentSheet.getRow(iActiveRow).getCell(i)==null) {
	 	    			currentSheet.getRow(iActiveRow).createCell(i);
	 	    		}
	 	    		
	    			try { 
	 	    		 currentSheet.getRow(iActiveRow).getCell(i-1).setCellValue(ColumnName);// Setting Column Name
	 	    		 
	 	    		  currentSheet.getRow(iActiveRow).getCell(i).setCellValue(Output); // Setting Output 
	    			}
	    			catch(Exception e) {
	    				e.printStackTrace();
	    			}
	 	    		  break;
	 	    	  }
	    	 }else {
	    		 i=i+1;// Null
	    	 }
	    	  
	      }
	      
	      iActiveRow=iActiveRow+1;
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")+ "/src/main/java/"
				+ "config/dataFile.xlsx");
		workBook.write(fout);
		fout.close();
	}
	
	
	//Getting Excel Output with same Test case Name & Different test case Name
	public static String getExcelOutput(String ColumnName) throws Throwable {
		
		workBook = new XSSFWorkbook();
		try{
			FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/"
					+ "config/dataFile.xlsx");
			workBook=(XSSFWorkbook) WorkbookFactory.create(fin);
		}
		catch(Exception e){
			System.out.println(e);
		}
			
	
		currentSheet = workBook.getSheet("Sheet2");
		
		try {
			cellCount = currentSheet.getRow(0).getLastCellNum();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		

		if(ColumnName.contains(";")) {
		String[] strSplitCol = ColumnName.split(";");
		testCaseName=  strSplitCol[0];
	      ColumnName =  strSplitCol[1];
	      System.out.println(testCaseName+ "   -------    "+ColumnName);
		}

	      for(int i =0;i<=cellCount;i++) {
	    	  
				if(currentSheet.getRow(0).getCell(i)!=null) {
					  if(currentSheet.getRow(0).getCell(i).toString().equalsIgnoreCase(testCaseName)) {
			    		  rowCount = currentSheet.getLastRowNum();
			    		
				    		for(int j=1;j<=rowCount;j++) {
				    			String testValue = currentSheet.getRow(j).getCell(i-1).toString();
				    			if(testValue.equalsIgnoreCase(ColumnName)) {
				    				testData=currentSheet.getRow(j).getCell(i).toString();
				    				break;
				    			}
				    		}
				    		break;
			    	  } 
				}else {
					i=i+1;
				}

	      }
	     iSetRow = iSetRow+1;
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")+ "/src/main/java/"
				+ "config/dataFile.xlsx");
		workBook.write(fout);
		fout.close();
		testCaseName = reporter;
		return testData;
	}
	
	
	public static String getTestColValue(String ColumnName) throws Throwable {
		
		workBook = new XSSFWorkbook();
		try{
			FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/"
					+ "config/dataFile.xlsx");
			workBook=(XSSFWorkbook) WorkbookFactory.create(fin);
		}
		catch(Exception e){
			System.out.println(e);
		}
			
	
		currentSheet = workBook.getSheet("Sheet1");
		
		try {
			cellCount = currentSheet.getRow(0).getLastCellNum();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	      for(int i =0;i<=cellCount;i++) {
	    	  
	    	 
	    	  if(currentSheet.getRow(0).getCell(i).toString().equalsIgnoreCase(ColumnName)) {
	    		  rowCount = currentSheet.getLastRowNum();
	    		
		    		for(int j=1;j<=rowCount;j++) {
		    			String testValue = currentSheet.getRow(j).getCell(0).toString();
		    			if(testValue.equalsIgnoreCase(testCaseName)) {
		    				testData=currentSheet.getRow(j).getCell(i).toString();
		    				break;
		    			}
		    		}
		    		break;
	    	  } 
	      }
	 
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")+ "/src/main/java/"
				+ "config/dataFile.xlsx");
		workBook.write(fout);
		fout.close();
		return testData;
	}
	

	
	public static void resetCount() {
		iActiveRow=1;
	}
	
	
}
