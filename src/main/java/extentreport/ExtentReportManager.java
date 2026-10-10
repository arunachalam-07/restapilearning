package extentreport;

import com.aventstack.extentreports.ExtentTest;

public class ExtentReportManager {
	private static ThreadLocal<ExtentTest> exTest = new ThreadLocal<ExtentTest>();

	public static void setTest(ExtentTest testItem) {
		exTest.set(testItem);
	}

	public static ExtentTest getTest() {
		return exTest.get();
	}

}
