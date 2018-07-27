package com.qa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.qa.base.Base;

public class CheckOutPage extends Base{ 
	
	@FindBy(id="email")
	private WebElement emailID;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(xpath="//button[@data-test='login-submit-button']")
	private WebElement submitBtn;
	
	@FindBy(xpath="//span[@data-test='trip-card-total']/span")
	private WebElement fareOnCheckOut;
	
	public void enterDetailsforCheckOut() {
		emailID.sendKeys(RegisterPage.randomGeneratedEmailId);
		password.sendKeys("123456");
		submitBtn.click();
	}
	
	public String getFareOnChckOut() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-test='trip-card-total']")));
		String fareOnChckOut=fareOnCheckOut.getAttribute("innerHTML");
		return fareOnChckOut;
	}
	
	public CheckOutPage() {
		PageFactory.initElements(driver,this);
	}
}
