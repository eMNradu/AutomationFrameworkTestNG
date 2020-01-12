package com.app.qa.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.app.qa.base.BaseClass;
import com.app.qa.pages.HomePage;

import junit.framework.Assert;

public class HomePageTest extends BaseClass{
	
	HomePage homePage;

	public HomePageTest() {
		super();
	}
	
	@BeforeMethod
	public void setupHomePageTest(){
		initialization();
		homePage = new HomePage();
	}
	
    
	@Test(priority=1)
	public void testHomePageTitle(){
		String title = "nopCommerce demo store";
		String returnTitle = homePage.validatePageTitle();
		Assert.assertEquals(title, returnTitle);	
	}
	
	@Test(priority=2)
	public void testLogoIsDisplayed(){
		Assert.assertTrue(homePage.logoIsDisplayed());
	}
	

	@Test(priority=3)
	public void testAvailableCurencyOptions() {
	String thisCurrency = "US Dollar Euro";
	Assert.assertTrue(homePage.currencySelectorIsAvailable().contains(thisCurrency));
	}
	
	
	@Test(priority=4)
	public void testSelectedCurency() {
		Assert.assertTrue(homePage.selectYourCurency());
	} 
	
	@Test(priority=5)
	public void testPopUpIsDisplayed() {
		String alert = homePage.verifyEmptySearchReturnsPopUp();
		Assert.assertEquals("Please enter some search keyword", alert);
		System.out.println(alert);
	} 
	
	@Test(priority=6)
	public void testSearchPageIsDisplayed() {
		String searchPageTitle = "nopCommerce demo store. Search";
		Assert.assertEquals(searchPageTitle,homePage.verifySearchPageIsDisplayed());
	} 
	
	@Test(priority=7)
	public void testAllMainMenuItems() {
		int size = 7;
		Assert.assertEquals(size, homePage.getAllMainMenuItems().size());
	}
	
	
	@Test(priority=8)
	public void testGetSubMenuItemsForTopMenu() {
		String test = "Desktops Notebooks Software";
		//String test = "Shoes Clothing Accessories";
		//String test = "Camera & photo Cell phones Others";
		Assert.assertTrue(homePage.getSubMenuItemsForTopMenu().contains(test));
	}
	
	@Test(priority=9)
	public void testClickOnSubMemnu(){ 
		homePage.clickOnSpecificSubMenu();
		String returnSubMenuPageTitle = homePage.validatePageTitle();
		Assert.assertEquals( "nopCommerce demo store. Desktops", returnSubMenuPageTitle);
	}
	
	@Test(priority=10)
	public void testGetAltForCategoryPictures() {
		homePage.getAltForCategoryPictures();
		Assert.assertTrue(homePage.getAltForCategoryPictures().get(0).contains("Picture for category Electronics"));
		Assert.assertTrue(homePage.getAltForCategoryPictures().get(1).contains("Picture for category Apparel"));
		Assert.assertTrue(homePage.getAltForCategoryPictures().get(2).contains("Picture for category Digital downloads"));
	}
	
	@Test(priority=10)
	public void pictureIsDisplayed() {
		Assert.assertTrue(homePage.pictureIsDisplayed());
	}
	
	@Test(priority=11)
	public void testFeaturedProductsPictures() {
		Assert.assertTrue(homePage.featuredProductsPictures());	
	}
	
	
	@Test(priority=11)
	public void testLinks() {
		Assert.assertTrue(homePage.checkBrokenlLinksForMainMenu());
	}
	
	@AfterMethod 
	public void tearDown() {
		driver.close();
	}
	

}
