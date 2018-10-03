package com.qa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.Base;
import com.qa.pageobjects.CheckOutPage;
import com.qa.pageobjects.MatrixPage;
import com.qa.pageobjects.RegisterPage;
import com.qa.pageobjects.SearchPage;
import com.qa.util.TestUtil;

public class TicketFareTest extends Base {

	public SearchPage searchPage;
	public MatrixPage matrixPage;
	public RegisterPage registerPage;
	public CheckOutPage checkOutPage;

	@BeforeMethod
	public void setUp() {
		initialization();
		driver.get(prop.getProperty("url"));
		searchPage = new SearchPage();
		matrixPage = new MatrixPage();
		registerPage = new RegisterPage();
		checkOutPage = new CheckOutPage();
	}

	@AfterTest
	public void tearDown() {
		if (driver != null)
			driver.quit();
		driver = null;
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		Object userData[][] = TestUtil.getExcelData(TestUtil.TEST_DATA_PATH, "Sheet2");
		return userData;
	}

	// @Test(retryAnalyzer=RetryAnalyzer.class)
	@Test(dataProvider = "getData")
	public void verifyTicketFare(String addressData1, String addressData2) throws InterruptedException {
		searchPage.enterJourneyDetails();
		searchPage.clickOnSearchBtn();
		matrixPage.clickOnFirstClassOption();
		registerPage = matrixPage.clickOnRegisterLink();
		registerPage.enterRegistrationDetails(addressData1, addressData2);
		registerPage.clickOnRegisterBtn();
		searchPage.clickOnSearchBtn();
		matrixPage.clickOnFirstClassOption();
		String fareOnMatrixPage = matrixPage.getFareOnMatrixPage();
		Thread.sleep(3000);
		matrixPage.clickOnChckOut();
		checkOutPage.enterDetailsforCheckOut();
		String fareOnCheckOutPage = checkOutPage.getFareOnChckOutPage();
		if (fareOnCheckOutPage.equals(fareOnMatrixPage)) {
			System.out.println("Ticket fares are matching");
		}

		Assert.assertEquals(fareOnCheckOutPage, fareOnMatrixPage, "Fares do not match");
	}
}
