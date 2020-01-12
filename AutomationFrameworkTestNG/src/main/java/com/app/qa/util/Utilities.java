package com.app.qa.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


import com.app.qa.base.BaseClass;


public class Utilities extends BaseClass {

	public static int PAGE_LOAD_TIMEOUT = 20;
	public static int IMPLICIT_TIMEOUT = 20;

	public static boolean isImageDisplayed(WebElement imageElement) {
		Boolean ImageIsDisplayed = (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				imageElement);
		if (!ImageIsDisplayed) {
			System.out.println("Image is not displayed.");
		} else{
			System.out.println("Image is displayed.");
		}
		return ImageIsDisplayed;
	}

			
	
	 public static void switchToFrame() {
		 driver.switchTo().frame("");
		 }

	
}
