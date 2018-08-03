package com.qa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.Base;


public class DemoTest extends Base{
	
	@BeforeMethod
	public void setup() {
		initialization();
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		driver.quit();
	}
	
	@Test
	public void demoTest() {
		driver.get("https://www.google.com");
		Assert.assertEquals(true, false);
	}
}
