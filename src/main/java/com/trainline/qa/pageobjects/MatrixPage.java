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
		// Thread.sleep(1000);
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
		// return new RegisterPage(DriverFactory.getInstance().getDriver());
	}

	private void continueBtnLogic() throws InterruptedException {
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(continueLnk));
		continueLnk.click();
	}

	public void clickOnCheckOut() throws InterruptedException {
		try {
			continueBtnLogic();
			continueBtnLogic();
			continueBtnLogic();
		} catch (Exception e) {
			LoggerUtil.logMessage("Exception occured: " + e.getMessage());
			Thread.sleep(2000);

			continueBtnLogic();

			if (!DriverFactory.getInstance().getDriver().findElements(By.xpath("//span[@class='_eu1pe2']/span"))
					.isEmpty()) {
				continueBtnLogic();
			}
		}
	}

	public MatrixPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
