package extentreport;

import java.lang.reflect.Method;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import constants.FrameworkConstantSingleton;

public class ExtentReportConfig {
	
	private ExtentReportConfig() {}
	
	private static ExtentReports extentReport;
	private static ExtentSparkReporter spark;
	
	private static ExtentTest test;
	

	
	public static void setupReport()
	{
		extentReport=new ExtentReports();
		spark=new ExtentSparkReporter(FrameworkConstantSingleton.getInstance().getReportpath());
		extentReport.attachReporter(spark);
	}
	
	public static void tearDownReports()
	{
		extentReport.flush();
	}
	public static void createTest(String s)
	{
		test=extentReport.createTest(s);
		ExtentReportManager.setTest(test);

	}



}
