package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class GroupedTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.out.println("Before class");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test(groups = {"sanity"})
    public void testGoogleHomePageTitle() {
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        System.out.println("Google Home Page Title: " + title);
        assert title.contains("Google");
    }

    @Test(groups = {"sanity", "regression"})
    public void testAmazonSearch() {
        driver.get("https://www.amazon.com");
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("TestNG books");
        searchBox.submit();
        System.out.println("Amazon search executed successfully.");
    }

    @Test(groups = {"regression"})
    public void testGoogleSearch() {
        driver.get("https://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.submit();
        System.out.println("Google search executed successfully.");
    }

    @AfterTest
    public void tearDown() {
        System.out.println("After class");
        if (driver != null) {
            driver.quit();
        }
    }
}
