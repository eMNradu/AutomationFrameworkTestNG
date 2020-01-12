package com.app.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.app.qa.util.Utilities;

public class BaseClass {
	
	public static Properties prop;
	public static WebDriver driver;
	public static String currency;
	public static String subMenu;
	public static String topMenus;
	public static String url;
	
	//constructor for class BaseClass
	public BaseClass() {
		
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("./src/main/java/com/app/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}}
	
	
	public static void initialization() {
		//Select the browser via config file
		String browser = prop.getProperty("browser");
		System.out.println(browser);
		if(browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			driver = new ChromeDriver(options);
			
		}else{System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		       driver = new FirefoxDriver();
		       }
		
		/**
	     * Get window title. 
	     * 
	     * @return Window title. 
	     */ 
		
		//Select the url via config file 
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Utilities.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Utilities.IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		
		//Select the currency for the e-commerce site
		url = prop.getProperty("url");
		currency = prop.getProperty("currency");
		subMenu = prop.getProperty("subMenu");
		topMenus = prop.getProperty("topMenus");
		
	}
	
	
	
	
	
}
	
	


