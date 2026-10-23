package extentreport;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.restassured.http.Header;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

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
	
	public static void logHeaderresponse(RequestSpecification rs)
	{
		
		try {
			QueryableRequestSpecification query=SpecificationQuerier.query(rs);
			
			if(query.getHeaders()!=null && query.getHeaders().exist())
			{
				info("header details");
			for(Header header:query.getHeaders())
			{
				info(header.getName()+" : "+header.getValue());
			}
			}
			else
			{
				info("header details not present");
			}
			
			if(query.getBody()!=null )
			{
				info("body details");
			
			logResponse(query.getBody().toString());
		
			}
			else
			{
				info("body details is blank");
			}
		}
		catch(Exception e)
		{
			info(e.getMessage());
			e.printStackTrace();
		}
	}


}
