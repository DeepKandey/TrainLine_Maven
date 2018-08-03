package com.qa.report;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import com.qa.base.Base;
import com.qa.util.TestUtil;


public class ExtentTestNGITestListener extends Base implements ITestListener{

	//Extent Report Declarations
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println((result.getMethod().getMethodName() + " started!"));
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		 System.out.println((result.getMethod().getMethodName() + " passed!"));
	     test.get().pass("Test Case Passed");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		try {
			TestUtil.takeScreenshotAtEndOfTest(result.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			test.get().fail("Test Case Failed",MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtEndOfTest(result.getName())).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.get().fail(result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip("Test Case Skipped");
		test.get().skip(result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Extent Reports Version 3 Test Suite started!");	
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
        System.out.println("Extent Reports Version 3  Test Suite is ending!");
		extent.flush();
	}
}
