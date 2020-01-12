package com.app.qa.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import io.restassured.RestAssured;

import com.app.qa.base.BaseClass;
import com.app.qa.util.Utilities;

public class HomePage extends BaseClass {

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public List<WebElement> mainMenuList;

	@FindBy(xpath = "//img[@alt ='nopCommerce demo store']")
	WebElement logo;

	@FindBy(xpath = "//img[@class='nivo-main-image']")
	WebElement image;

	@FindBy(xpath = "//select[@id ='customerCurrency']")
	WebElement selectCurency;

	@FindBy(xpath = "//input[@id = 'small-searchterms']")
	WebElement searchBox;

	@FindBy(xpath = "//input[@value ='Search']")
	WebElement searchBoxButton;

	@FindBy(xpath = "//ul[@class ='top-menu']/li[*]")
	WebElement topMenu;

	@FindBy(xpath = "//ul[@class='top-menu']/li//a[contains(text(),'Computers')]")
	WebElement firstElementInMenu;

	@FindBy(xpath = "//div[@class = 'topic-block-title']/h2")
	WebElement topicBlockTitle;

	@FindBy(xpath = "//h2[contains(text(), 'Welcome to our store')]")
	WebElement welcomeBlockTitle;

	@FindBy(xpath = "//div[@class ='title']/strong[contains(text(), 'Featured products')]")
	WebElement featuredProductTitle;

	@FindBy(xpath = "//h2[@class='title']/a[contains(@title, 'Show products in category Electronics')]")
	WebElement electronicsTitle;

	@FindBy(xpath = "//img[@alt ='Picture for category Electronics']")
	WebElement pictureForCatElectronics;

	/****************************************************************************/

	public String validatePageTitle() {
		return driver.getTitle();
	}

	public boolean logoIsDisplayed() {
		logo.isDisplayed();
		return true;
	}

	public String currencySelectorIsAvailable() {
		String curencyOptions = "";
		if (selectCurency.isDisplayed()) {
			Select dropDown = new Select(selectCurency);
			List<WebElement> curencyCount = dropDown.getOptions();
			int dropdownSize = curencyCount.size();
			for (int i = 0; i < dropdownSize; i++) {
				curencyOptions = curencyOptions + "" + curencyCount.get(i).getText() + " ";
			}
		}
		System.out.println("Your currency options are: " + curencyOptions);
		return curencyOptions;
	}

	public boolean selectYourCurency() {
		if (selectCurency.isDisplayed()) {
			Select curencyDropDown = new Select(selectCurency);
			curencyDropDown.selectByVisibleText(currency);
		}
		return true;
	}

	public String verifyEmptySearchReturnsPopUp() {
		String alertText = "";
		searchBoxButton.click();
		Alert alert = driver.switchTo().alert();
		if (alert != null) {
			alertText = driver.switchTo().alert().getText();
			alert.accept();
		}
		return alertText;
	}

	public String verifySearchPageIsDisplayed() {
		searchBox.sendKeys("Apple");
		searchBoxButton.click();
		return driver.getTitle();
	}

	public List<WebElement> getAllMainMenuItems() {
		List<WebElement> mainMenuList = driver.findElements(By.xpath("//ul[@class ='top-menu']/li[*]"));
		String menuOptions = "";
		Iterator<WebElement> itr = mainMenuList.iterator();
		while (itr.hasNext()) {
			menuOptions = menuOptions + "" + itr.next().getText() + " ";
		}
		System.out.println(menuOptions);
		return mainMenuList;
	}

	public void hoverOverMainMenuItem() {
		List<WebElement> mainMenu = driver.findElements(By.xpath("//ul[@class ='top-menu']/li[*]"));
		for (WebElement menuOption : mainMenu) {
			if (menuOption.getText().equals(topMenus)) {
				Actions action = new Actions(driver);
				action.moveToElement(menuOption).perform();
				break;
			}
		}
	}

	public boolean checkBrokenlLinksForMainMenu() {
		int statusCode;
		List<WebElement> allPageLinks = driver.findElements(By.xpath("//ul[@class ='top-menu']/li/a"));
		for (WebElement link : allPageLinks) {
			String href = link.getAttribute("href");
			if (href != null) {
				statusCode = RestAssured.get(href).statusCode();
				System.out.println(href + " gave a response code of " + statusCode);
				return true;
			} else {
				String error = link.getText().toString();
				System.out.println("This is an error " + error);
			}
		}
		return false;
	}

	public String getSubMenuItemsForTopMenu() {
		hoverOverMainMenuItem();
		String subMenuOptions = "";
		List<WebElement> subMenuList = driver.findElements(By.xpath("//ul[@class='sublist first-level']/li"));
		Iterator<WebElement> itr = subMenuList.iterator();
		while (itr.hasNext()) {
			subMenuOptions = subMenuOptions + "" + itr.next().getText() + " ";
		}
		System.out.println(subMenuOptions);
		return subMenuOptions;
	}

	public void clickOnSpecificSubMenu() {
		try {
			hoverOverMainMenuItem();
			List<WebElement> allSubMenus = driver
					.findElements(By.xpath("//ul[@class='top-menu']/li//ul[@class='sublist first-level']/li"));
			for (WebElement option : allSubMenus) {
				if (!option.getText().equals(subMenu)) {
					throw new Exception("Submenu item: " + subMenu + " does not exist for main menu: " + topMenus
							+ ".Please check the config file");
				} else {
					option.click();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<String> getAltForCategoryPictures() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", electronicsTitle);
		List<WebElement> categoryImages = driver
				.findElements(By.xpath("//div[@class='category-item']//div[@class='picture']/a/img"));
		ArrayList<String> altImageCategory = new ArrayList<String>();
		for (WebElement categoryImage : categoryImages) {
			String ImageAlt = categoryImage.getAttribute("alt");
			altImageCategory.add(ImageAlt);
		}
		return altImageCategory;
	}

	// generic method for testing if (each) image is displayed
	public boolean pictureIsDisplayed() {
		boolean imageIsDisplayed = Utilities.isImageDisplayed(pictureForCatElectronics);
		return imageIsDisplayed;
	}

	public boolean featuredProductsPictures() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", featuredProductTitle);
		boolean productImagesAreDisplayed = false;
		List<WebElement> productImages = driver.findElements(By.xpath("//div[@class = 'product-item']//a/img"));
		for (WebElement productImage : productImages) {
			productImagesAreDisplayed = Utilities.isImageDisplayed(productImage);
			int width = productImage.getSize().getWidth();
			int hight = productImage.getSize().getHeight();
			if (productImagesAreDisplayed && width == 288 && hight == 288) {
				String productImageAlt = productImage.getAttribute("alt");
				System.out.println(productImageAlt + " has width - " + width + "px" + " and height - " + hight + "px");
			} else {
				System.out.println("Picture is not displayed or incorrect value for width/height ");
			}
		}
		return productImagesAreDisplayed;
	}

}
