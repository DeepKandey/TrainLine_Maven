package com.qa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.qa.base.Base;

public class SearchPage extends Base{
	@FindBy(id="from.text")
	WebElement departureStn;
	
	@FindBy(id="to.text")
	private WebElement destinationStn;
	
	@FindBy(xpath="//button[@data-test='submit-journey-search-button']")
	private WebElement searchButton;
	
	public SearchPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void enterJourneyDetails() {
	    departureStn.sendKeys("London");
		destinationStn.sendKeys("Manchester");
		JavascriptExecutor js =(JavascriptExecutor)driver;
		String scriptForDestination = "return document.getElementById(\"to.text\").value;";
		
		while(!js.executeScript(scriptForDestination).equals("Manchester")) {
			destinationStn.clear();
			destinationStn.sendKeys("Manchester");
		}
	    driver.findElement(By.id("to.text")).sendKeys(Keys.ENTER);
	}

	public MatrixPage clickOnSearchBtn() {  
	    wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();		
		return new MatrixPage();
	}
}
