package com.trainline.qa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trainline.qa.base.Base;
import com.trainline.qa.pageobjects.CheckOutPage;
import com.trainline.qa.pageobjects.MatrixPage;
import com.trainline.qa.pageobjects.RegisterPage;
import com.trainline.qa.pageobjects.SearchPage;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.TestUtil;

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
		// checkOutPage.enterDetailsForCheckOut();
		String fareOnCheckOutPage = checkOutPage.getFareOnChckOutPage();
		if (fareOnCheckOutPage.equals(fareOnMatrixPage)) {
			LoggerUtil.logMessage("Ticket fares are matching");
		}
		Assert.assertEquals(fareOnCheckOutPage, fareOnMatrixPage, "Fares do not match");
	}
}
