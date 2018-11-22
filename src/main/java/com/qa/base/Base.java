package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.util.LoggerUtil;
import com.qa.util.TestUtil;
import com.qa.util.WebEventListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	public static WebDriver driver = null;
	public static WebDriverWait wait;
	public static Properties prop;
	public static EventFiringWebDriver eDriver;
	public static WebEventListener eventListener;

	public Base() {
		prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\config\\config.properties");
		try {
			FileInputStream fis = new FileInputStream(file);
			prop.load(fis);
		} catch (Exception e) {
			LoggerUtil.logMessage("Exception occured: " + e);
		}
	}

	public void initialization() {
		if (driver == null) {
			/*
			 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver();
			 */
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		eDriver = new EventFiringWebDriver(driver);
		// Now create object of EventListenerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		eDriver.register(eventListener);

		driver = eDriver;
		driver.manage().window().maximize();
		;
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, TestUtil.EXPLICIT_WAIT);
	}
}
