package com.qa.pageobjects;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.Base;

public class RegisterPage extends Base{
	public static String randomGeneratedEmailId;
	
	@FindBy(id="email")
	private WebElement emailID;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="firstName")
	private WebElement firstName;
	
	@FindBy(id="surname")
	private WebElement surName;
	
	@FindBy(id="addressLine1")
	private WebElement addressLine1;
	
	@FindBy(id="addressLine2")
	private WebElement addressLine2;
	
	@FindBy(id="city")
	private WebElement city;
	
	@FindBy(id="state")
	private WebElement state;
	
	@FindBy(id="postcode")
	private WebElement postcode;
	
	@FindBy(xpath="//button[@data-test='register-button']")
	private WebElement registerBtn;
	
	@FindBy(xpath="//select[@data-test='address-country-dropdown']")
	private WebElement countryDropDown;
	
	@FindBy(xpath="//button[@data-test='manual-address-entry-button']")
	private WebElement manualAddressBtn;
		
	public void enterRegistrationDetails() {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
		String emailId = RandomStringUtils.randomAlphanumeric(10);
		randomGeneratedEmailId=emailId + "@gmail.com";
		emailID.sendKeys(randomGeneratedEmailId);
		password.sendKeys("123456");
		firstName.sendKeys("Harsh");
		surName.sendKeys("Shah");
		manualAddressBtn.click();
		Select select = new Select(countryDropDown);
		select.selectByVisibleText("India");
		addressLine1.sendKeys("asafkjhf");
		addressLine2.sendKeys("asafkjhf");
		city.sendKeys("Mumbai");
		state.sendKeys("Maharashtra");
		postcode.sendKeys("411207");
	}

	public SearchPage clickOnRegisterBtn() {
		registerBtn.click();
		return new SearchPage();
	}
	
	public RegisterPage() {
		PageFactory.initElements(driver,this);
	}

}