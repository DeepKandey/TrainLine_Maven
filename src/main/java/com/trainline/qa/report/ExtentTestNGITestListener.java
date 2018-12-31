package com.trainline.qa.report;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.trainline.qa.util.TestUtil;

public class ExtentTestNGITestListener  implements ITestListener {

	// Extent Report Declarations
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test Case Passed");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		try {
			TestUtil.takeScreenshotAtEndOfTest(result.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			test.get().fail("Test Case Failed", MediaEntityBuilder
					.createScreenCaptureFromPath(TestUtil.takeScreenshotAtEndOfTest(result.getName())).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.get().fail(result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip("Test Case Skipped");
		test.get().skip(result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	public void onStart(ITestContext context) {
		System.out.println("Extent Reports Version 3 Test Suite started!");
	}

	public void onFinish(ITestContext context) {
		System.out.println("Extent Reports Version 3  Test Suite is ending!");
		extent.flush();
	}
}
