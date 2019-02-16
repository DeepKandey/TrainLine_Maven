package com.trainline.qa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.trainline.qa.base.DriverFactory;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.TestUtil;

public class MatrixPage {

	private String fareOnMatrixPage = null;

	@FindBy(xpath = "descendant::input[@type='radio']")
	private List<WebElement> ticketFareRadioBtn;

	@FindBy(xpath = "descendant::input[@type='radio'][5]/parent::div/following-sibling::div/span[2]/span")
	private WebElement ticketFareValue1;

	@FindBy(xpath = "//button[@class='_1vwjm4ai']")
	private WebElement changeLink;

	@FindBy(linkText = "Register")
	private WebElement registerLnk;

	@FindBy(xpath = "//button[@data-test='cjs-button-quick-buy']")
	private WebElement quickChckOut;

	@FindBy(xpath = "//span[@class='_eu1pe2']/span")
	private WebElement continueLnk;

	WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), TestUtil.EXPLICIT_WAIT);

	public void clickOnFirstClassOption() {
		wait.until(ExpectedConditions.elementToBeClickable(changeLink));
		try {
			clickOnRadioBtnLogic();
		} catch (StaleElementReferenceException e) {
			clickOnRadioBtnLogic();
		}
	}

	private void clickOnRadioBtnLogic() {
		String beforexPath = "descendant::input[@type='radio'][ ";
		String afterxPath = "]/parent::div/following-sibling::div/span[2]/span";

		if (ticketFareRadioBtn.size() < 5) {
			wait.until(ExpectedConditions.elementToBeClickable(ticketFareRadioBtn.get(2)));
			ticketFareRadioBtn.get(2).click();

			String fareXpath = beforexPath + 3 + afterxPath;
			fareOnMatrixPage = DriverFactory.getInstance().getDriver().findElement(By.xpath(fareXpath)).getText();
			LoggerUtil.logMessage("Fare on Matrix Page when size is less than 5 : " + fareOnMatrixPage);

		} else {
			wait.until(ExpectedConditions.elementToBeClickable(ticketFareRadioBtn.get(4)));
			ticketFareRadioBtn.get(4).click();

			String fareXpath = beforexPath + 5 + afterxPath;
			fareOnMatrixPage = DriverFactory.getInstance().getDriver().findElement(By.xpath(fareXpath)).getText();
			LoggerUtil.logMessage("Fare on Matrix Page when size is greater than 5 : " + fareOnMatrixPage);
		}
	}

	public String getFareOnMatrixPage() {
		return fareOnMatrixPage;
	}

	public void clickOnRegisterLink() {
		registerLnk.click();
	}

	private void continueBtnClickLogic() throws InterruptedException {
		Thread.sleep(1500);
		WebElement continueBtn = DriverFactory.getInstance().getDriver()
				.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
		wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		continueBtn.click();
		Thread.sleep(1500);
	}

	public void clickOnCheckOut() throws InterruptedException {
		try {
			continueBtnClickLogic();
			continueBtnClickLogic();
			continueBtnClickLogic();
		} catch (Exception e) {
			LoggerUtil.logMessage("Exception occured: " + e.getMessage());
			continueBtnClickLogic();
			if (!DriverFactory.getInstance().getDriver().findElements(By.xpath("//span[@class='_eu1pe2']/span"))
					.isEmpty()) {
				continueBtnClickLogic();
			}
		}
	}

	public MatrixPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
