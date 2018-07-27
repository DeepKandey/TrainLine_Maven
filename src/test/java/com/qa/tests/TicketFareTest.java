package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.Base;
import com.qa.pageobjects.CheckOutPage;
import com.qa.pageobjects.MatrixPage;
import com.qa.pageobjects.RegisterPage;
import com.qa.pageobjects.SearchPage;

public class TicketFareTest extends Base{
	
	public SearchPage searchPage;
	public MatrixPage matrixPage;
	public RegisterPage registerPage;
	public CheckOutPage checkOutPage;
	
	@BeforeMethod
	public void setUp() {
		initialization();
		searchPage = new SearchPage();
    	matrixPage = new MatrixPage();
    	registerPage = new RegisterPage();	
    	checkOutPage = new CheckOutPage();
	}

	@AfterTest
	public void tearDown() {
		if(driver!=null)
			driver.quit();
	}
	
	@Test
	public void verifyTicketFare() throws InterruptedException {
		searchPage.enterJourneyDetails();
		searchPage.clickOnSearchBtn();
		matrixPage.clickOnFirstClassOption();
		registerPage=matrixPage.clickOnRegister();
		registerPage.enterRegistrationDetails();
		searchPage=registerPage.clickOnRegisterBtn();
		searchPage.clickOnSearchBtn();
		matrixPage.clickOnFirstClassOption();
		String fareOnMatrixPage= matrixPage.getFareOnMatrixPage();
		Thread.sleep(3000);
		matrixPage.clickOnChckOut();
		checkOutPage.enterDetailsforCheckOut();
		String fareOnCheckOutPage=checkOutPage.getFareOnChckOut();
		
		Assert.assertEquals(fareOnCheckOutPage, fareOnMatrixPage,"Fares do not match");
		
		if(fareOnCheckOutPage.equals(fareOnMatrixPage)) {
			System.out.println("Ticket fares are matching");
		}
	}
}
