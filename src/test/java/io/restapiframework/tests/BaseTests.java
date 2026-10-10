package io.restapiframework.tests;
import org.testng.annotations.Listeners;

import io.restapiframework.listeners.TestListener;

@Listeners(TestListener.class)
public class BaseTests {
	
//	
//	@Override
//	public void onStart(ISuite suite) {
//		// TODO Auto-generated method stub
//		ISuiteListener.super.onStart(suite);
//		setupReport();
//	}
//
//
//	@Override
//	public void onFinish(ISuite suite) {
//		// TODO Auto-generated method stub
//		ISuiteListener.super.onFinish(suite);
//		tearDownReports();
//	}
//
//
//	@Override
//	public void onTestStart(ITestResult result) {
//		// TODO Auto-generated method stub
//		ITestListener.super.onTestStart(result);
//		
//		createTest(result.getMethod().getMethodName());
//	}
//
//
//	@Override
//	public void onTestSuccess(ITestResult result) {
//		// TODO Auto-generated method stub
//		ITestListener.super.onTestSuccess(result);
//		ExtendLogger.pass(result.getMethod().getMethodName()+" passed");
//	}
//
//
//	@Override
//	public void onTestFailure(ITestResult result) {
//		// TODO Auto-generated method stub
//		ITestListener.super.onTestFailure(result);
//		ExtendLogger.fail(result.getThrowable().toString());
//	}
//
//
//	@Override
//	public void onTestSkipped(ITestResult result) {
//		// TODO Auto-generated method stub
//		ITestListener.super.onTestSkipped(result);
//		ExtendLogger.info(result.getMethod().getMethodName()+" skipped");
//	}


//	@BeforeSuite
//	public void initateReport()
//	{
//		setupReport();
//	}
//
//	
//	@AfterSuite
//	public void tearDownReport()
//	{
//		tearDownReports();
//	}

//	@BeforeMethod
//	
//	public void iniateTest(Method m)
//	{
//		createTest(m.getName().toString());
//	}

//	@AfterMethod
//	public void testTearDown(ITestResult result)
//	{
//		if(result.getStatus()==ITestResult.SUCCESS)
//		{
//			
//		}
//		else if(result.getStatus()==ITestResult.FAILURE)
//		{
//			
//		}
//		else if(result.getStatus()==ITestResult.SKIP)
//		{
//			
//		}
//		
//
//	}

}
