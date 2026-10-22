package extentreport;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtendLogger {
	
	public static void pass(String message)
	{
		ExtentReportManager.getTest().pass(message);
	}
	
	public static void fail(String message)
	{
		ExtentReportManager.getTest().fail(message);
	}
	
	public static void info(String message)
	{
		ExtentReportManager.getTest().info(message);
	}
	
	public static void logResponse(String message)
	{
		ExtentReportManager.getTest().info(MarkupHelper.createCodeBlock(message,CodeLanguage.JSON));
	}
	
	public static void addAuthor(String[] authors)
	{
		for(String author:authors)
		{
			ExtentReportManager.getTest().assignAuthor(author);
		}
	}
	
	public static void addCategory(String[] categories)
	{
		for(String category:categories)
		{
			ExtentReportManager.getTest().assignCategory(category);
		}
	}


}
