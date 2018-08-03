package com.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.Base;

public class TestUtil extends Base {
	
	public static long PAGE_LOAD_TIMEOUT = 40;
	public static long IMPLICIT_WAIT = 5;
	public static long EXPLICIT_WAIT=15;
	
	public static String takeScreenshotAtEndOfTest(String methodName) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		
		Date date = new Date();
		SimpleDateFormat dateformat= new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
		String path = currentDir + "/screenshots/" +methodName + "_" + dateformat.format(date)+ ".png";
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" +methodName + "_" + dateformat.format(date)+ ".png"));
		return path;
		}
}
