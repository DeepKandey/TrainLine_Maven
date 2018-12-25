package com.trainline.qa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.trainline.qa.base.Base;
import com.trainline.qa.util.LoggerUtil;

public class MatrixPage extends Base {

	private String fareOnMatrixPage = null;

	@FindBy(xpath = "descendant::input[@type='radio']")
	private List<WebElement> ticketFareRadioBtn;

	@FindBy(xpath = "descendant::input[@type='radio'][5]/parent::div/following-sibling::div/span[2]/span")
	private WebElement ticketFareValue1;

	@FindBy(linkText = "Register")
	private WebElement registerLnk;

	@FindBy(xpath = "//button[@data-test='cjs-button-quick-buy']")
	private WebElement quickChckOut;

	public void clickOnFirstClassOption() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@class='_1vwjm4ai']"))));
		List<WebElement> radioBtnList = driver.findElements(By.xpath("descendant::input[@type='radio']"));
		String beforexPath = "descendant::input[@type='radio'][ ";
		String afterxPath = "]/parent::div/following-sibling::div/span[2]/span";
		try {
			if (radioBtnList.size() < 5) {
				wait.until(ExpectedConditions.elementToBeClickable(radioBtnList.get(2)));
				wait.until(ExpectedConditions.visibilityOf(radioBtnList.get(2)));
				radioBtnList.get(2).click();

				String fareXpath = beforexPath + 3 + afterxPath;
				fareOnMatrixPage = driver.findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage(fareOnMatrixPage);

			} else {
				wait.until(ExpectedConditions.elementToBeClickable(radioBtnList.get(4)));
				wait.until(ExpectedConditions.visibilityOf(radioBtnList.get(4)));
				radioBtnList.get(4).click();
				String fareXpath = beforexPath + 5 + afterxPath;
				fareOnMatrixPage = driver.findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage(fareOnMatrixPage);
			}
		} catch (StaleElementReferenceException e) {
			driver.navigate().refresh();
			List<WebElement> radioBtnList1 = driver.findElements(By.xpath("descendant::input[@type='radio']"));
			if (radioBtnList1.size() < 5) {
				wait.until(ExpectedConditions.elementToBeClickable(radioBtnList1.get(2)));
				radioBtnList1.get(2).click();
				String fareXpath = beforexPath + 3 + afterxPath;
				fareOnMatrixPage = driver.findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage(fareOnMatrixPage);

			} else {
				wait.until(ExpectedConditions.elementToBeClickable(radioBtnList1.get(4)));
				wait.until(ExpectedConditions.visibilityOf(radioBtnList1.get(4)));
				radioBtnList1.get(4).click();
				String fareXpath = beforexPath + 5 + afterxPath;
				fareOnMatrixPage = driver.findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage(fareOnMatrixPage);
			}
		}

	}

	public String getFareOnMatrixPage() {
		LoggerUtil.logMessage(fareOnMatrixPage);
		return fareOnMatrixPage;
	}

	public RegisterPage clickOnRegisterLink() {
		registerLnk.click();
		return new RegisterPage();
	}

	public void clickOnChckOut() throws InterruptedException {
		try {
			WebElement continueBtn1 = driver.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.elementToBeClickable(continueBtn1));
			continueBtn1.click();
			WebElement continueBtn2 = driver.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.visibilityOf(continueBtn2));
			continueBtn2.click();
			/*
			 * wait.until(ExpectedConditions.visibilityOf(quickChckOut));
			 * quickChckOut.click();
			 */
		} catch (Exception e) {
			LoggerUtil.logMessage("Exception occured: " + e.getMessage());
			Thread.sleep(2000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='_eu1pe2']/span")));
			WebElement continueBtn2 = driver.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.elementToBeClickable(continueBtn2));
			continueBtn2.click();
		}
	}

	public MatrixPage() {
		PageFactory.initElements(driver, this);
	}
}
