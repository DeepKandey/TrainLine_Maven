package com.qa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.qa.base.Base;

public class MatrixPage extends Base{
	
	@FindBy(xpath="descendant::input[@type='radio'][5]")
	private WebElement ticketFareRadioBtn;
	
	@FindBy(xpath="descendant::input[@type='radio'][5]/parent::div/following-sibling::span/span")
	private WebElement ticketFareValue;
	
	@FindBy(linkText="Register")
	private WebElement registerLnk;
	
	@FindBy(xpath="//button[@data-test='cjs-button-quick-buy']")
	private WebElement quickChckOut;
	
	public void clickOnFirstClassOption() {
		wait.until(ExpectedConditions.elementToBeClickable(ticketFareRadioBtn));
		ticketFareRadioBtn.click();
		if(driver.findElements(By.xpath("descendant::input[@type='radio'][5]")).size() != 0){
			ticketFareRadioBtn.click();
		}
	}

	public String getFareOnMatrixPage() {
		String fareOnMatrixPage=ticketFareValue.getAttribute("innerHTML");
		return fareOnMatrixPage;
	}
	
	public RegisterPage clickOnRegisterLink() {
		registerLnk.click();
		return new RegisterPage();
	}
	
	public void clickOnChckOut() {
		wait.until(ExpectedConditions.visibilityOf(quickChckOut));
		quickChckOut.click();
	}
	
	public MatrixPage() {
		PageFactory.initElements(driver, this);
	}
}
