package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.Base;

public class TestUtil extends Base {

	public static final long PAGE_LOAD_TIMEOUT = 40;
	public static final long IMPLICIT_WAIT = 5;
	public static final long EXPLICIT_WAIT = 15;
	public static final String TEST_DATA_PATH = "C:/Users/deepa/Downloads/TestDocument.xlsx";
	public static FileInputStream fis = null;
	public static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;

	public static String takeScreenshotAtEndOfTest(String methodName) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");

		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
		String path = currentDir + "/screenshots/" + methodName + "_" + dateformat.format(date) + ".png";
		FileUtils.copyFile(scrFile,
				new File(currentDir + "/screenshots/" + methodName + "_" + dateformat.format(date) + ".png"));
		return path;
	}

	// 1.return data from the excel
	public static String[][] getExcelData(String filePath, String sheetName) throws IOException {
		String[][] arrayData = null;
		try {
			fis = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);

			int totalNoRows = sheet.getLastRowNum();
			int totalNoCols = sheet.getRow(0).getLastCellNum();
			arrayData = new String[totalNoRows][totalNoCols];

			for (int i = 0; i < totalNoRows; i++) {
				for (int j = 0; j < totalNoCols; j++) {
					arrayData[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workbook.close();
			fis.close();
		}
		return arrayData;
	}
}
