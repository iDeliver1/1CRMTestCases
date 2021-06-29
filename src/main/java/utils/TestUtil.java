package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import base.TestBase;


public class TestUtil extends TestBase{
	static String rootdir;
	public static String brow;
	public static String reportFolderPath = System.getProperty("user.dir") + "/target/TestReports/";

	public static String getBrowserVersion() throws IOException {
		
		try {
			Runtime rt = Runtime.getRuntime();
			try {
				rootdir = System.getProperty("user.dir");
				rt.exec("cmd  /K \"dir /B/AD \"C:/Program Files (x86)/Google/Chrome/Application/\"|findstr /R /C:\"^[0-9].*\\..*[0-9]$\" > "+ rootdir +"\\version.txt\"");
				brow = getVersion();
			} 
			catch (IOException e) {
				rootdir = System.getProperty("user.dir");
				rt.exec("cmd  /K \"dir /B/AD \"C:/Program Files/Google/Chrome/Application/\"|findstr /R /C:\"^[0-9].*\\..*[0-9]$\" > "+ rootdir +"\\version.txt\"");
				brow = getVersion();
				e.printStackTrace();
			}
			return brow.substring(0, brow.length() - 4);
		}
		catch(Exception e){
			brow = e.toString();
			return brow;
		}
	}

//--------------------Return Stored value of Chrome Browser Version----------------------------
	public static String getVersion() {
		 String data = "";
		try {
		  File myObj = new File(rootdir+"/version.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	         data = myReader.nextLine();
	         break;
	      }
	      myReader.close();
	      return data;
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
		return null;
	}
	
	public static String getCurrentDate()
	{
		Date date = new Date();  
	    SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");  
	    String strDate = dateformat.format(date); 
	    return strDate;
	}
	
	public static String getTimeStamp()
	{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy-hh-mm-ss");
		String time = dateFormat.format(now);
		return time.replace("-", "");
	}
	
	
	//-------------------------------------------TimeStamp Function----------------------------------	
	public static String getAttendenceTime(int hour)
	{
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date());              
		cal.add(Calendar.HOUR_OF_DAY, hour);  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dateFormat.format(cal.getTime());
		return time;
	}
	
	
	//---------------------------------Function For Current Date---------------------------------		
	public static String getLeaveDate(int date)
	{
	    Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, date);
	    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");  
	    String strDate = dateformat.format( cal.getTime()); 
	    return strDate;
	}
	
	//---------------------------------------Select Item--------------------------------------------
	public static void selectItem(WebElement element,int LeaveFormat) {
		Select Leave = new Select(element);
		Leave.selectByIndex(LeaveFormat);
	}
	
	
	
	public static void selectItemByVisibleText(WebElement element,String visibleText) {
		Select list = new Select(element);
		list.selectByVisibleText(visibleText);
	}
	
	//------------------------get date by format---------------------------------------------------
	public static String getDatebyFormat(String dateFormat ,int addToDate)
	{
	    Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addToDate);
	    SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat);  
	    String strDate = dateformat.format( cal.getTime()); 
	    return strDate;
	}
	
	public static void mouseHover(WebElement element) throws Throwable {
		Actions action = new Actions(driver);
		Thread.sleep(1000);
		action.moveToElement(element).perform();
	}
	
	
	public static String getScreenhot(String Status) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/TestsScreenshots/"+Status+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	public static String createLogFile() {
		
		try {
		      File myObj = new File(System.getProperty("user.dir") + "/Reports/Log_Reports/loggerReport"+getTimeStamp()+".log");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        return myObj.getName();
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return null;
	}
}
	
