package com.qa.tests;

import org.testng.Assert;
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
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void demoTest() {
		driver.get("https://www.google.com");
		Assert.assertEquals(true, false);
	}
}
