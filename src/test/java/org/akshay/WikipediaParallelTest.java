package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WikipediaParallelTest extends BaseTest {

    @Test
    public void testSearchSelenium() {
        driver.get("https://www.wikipedia.org/");

        WebElement searchBox = driver.findElement(By.id("searchInput"));
        searchBox.sendKeys("Selenium (software)");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Selenium (software)"), "Page title does not match!");
    }

    @Test
    public void testSearchTestNG() {
        driver.get("https://www.wikipedia.org/");
        WebElement searchBox = driver.findElement(By.id("searchInput"));
        searchBox.sendKeys("TestNG");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("TestNG"), "Page title does not match!");
    }

}


