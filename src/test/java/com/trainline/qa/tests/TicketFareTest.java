package com.trainline.qa.tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trainline.qa.base.DriverFactory;
import com.trainline.qa.pageobjects.CheckOutPage;
import com.trainline.qa.pageobjects.MatrixPage;
import com.trainline.qa.pageobjects.RegisterPage;
import com.trainline.qa.pageobjects.SearchPage;
import com.trainline.qa.util.ExcelReader;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.TestUtil;

public class TicketFareTest {

	public SearchPage searchPage;
	public MatrixPage matrixPage;
	public RegisterPage registerPage;
	public CheckOutPage checkOutPage;

	@BeforeMethod
	public void setUp() {
		DriverFactory.getInstance().setDriver("chrome");
		WebDriver driver = TestUtil.webDriverEvents(DriverFactory.getInstance().getDriver());
		driver.get(TestUtil.APP_URL);

		searchPage = new SearchPage(driver);
		matrixPage = new MatrixPage(driver);
		registerPage = new RegisterPage(driver);
		checkOutPage = new CheckOutPage(driver);
	}

	@AfterTest
	public void tearDown() {
		DriverFactory.getInstance().removeDriver();
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		ExcelReader excelReader = new ExcelReader(TestUtil.TEST_DATA_PATH);
		Object userData[][] = excelReader.getExcelData("Sheet2");
		return userData;
	}

	// @Test(retryAnalyzer=RetryAnalyzer.class)
	@Test(dataProvider = "getData")
	public void verifyTicketFare(String addressData1, String addressData2) throws InterruptedException {
		searchPage.enterJourneyDetails();
		searchPage.clickOnSearchBtn();
		matrixPage.clickOnFirstClassOption();
		matrixPage.clickOnRegisterLink();
		registerPage.enterRegistrationDetails(addressData1, addressData2);
		registerPage.clickOnRegisterBtn();
		searchPage.clickOnSearchBtn();
		matrixPage.clickOnFirstClassOption();
		String fareOnMatrixPage = matrixPage.getFareOnMatrixPage();
		Thread.sleep(3000);
		matrixPage.clickOnCheckOut();
		String fareOnCheckOutPage = checkOutPage.getFareOnCheckOutPage();
		if (fareOnCheckOutPage.equals(fareOnMatrixPage)) {
			LoggerUtil.logMessage("Ticket fares are matching");
		}
		Assert.assertEquals(fareOnCheckOutPage, fareOnMatrixPage, "Fares do not match");
	}
}
