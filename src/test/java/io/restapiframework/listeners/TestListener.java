package io.restapiframework.listeners;

import static extentreport.ExtentReportConfig.createTest;
import static extentreport.ExtentReportConfig.setupReport;
import static extentreport.ExtentReportConfig.tearDownReports;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import extentreport.ExtendLogger;

public class TestListener implements ITestListener , ISuiteListener {
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		ISuiteListener.super.onStart(suite);
		setupReport();
	}


	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		ISuiteListener.super.onFinish(suite);
		tearDownReports();
	}


	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		
		createTest(result.getMethod().getMethodName());
	}


	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		ExtendLogger.pass(result.getMethod().getMethodName()+" passed");
	}


	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		ExtendLogger.fail(result.getThrowable().toString());
	}


	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
		ExtendLogger.info(result.getMethod().getMethodName()+" skipped");
	}


}
