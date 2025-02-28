package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;

public class EcommerceDependencyTest extends BaseTest {

    @Test
    public void testLoadHomePage() {
        driver.get("https://www.ebay.com");
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        Assert.assertTrue(searchBox.isDisplayed(), "Home page did not load correctly.");
        System.out.println("E-commerce home page loaded successfully.");
    }

    @Test(dependsOnMethods = {"testLoadHomePage"})
    public void testSearchProduct() {
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys("laptop");
        WebElement searchButton = driver.findElement(By.id("gh-btn"));
        searchButton.click();

        Assert.assertTrue(driver.getTitle().contains("laptop"), "Product search did not work.");
        System.out.println("Product search executed successfully.");
    }

    @Test(dependsOnMethods = {"testSearchProduct"})
    public void testValidateSearchResults() {
        List<WebElement> productTitles = driver.findElements(By.cssSelector(".s-item__title"));
        Assert.assertFalse(productTitles.isEmpty(), "No products found in search results.");

        System.out.println("Top 5 Search Results:");
        for (int i = 0; i < Math.min(5, productTitles.size()); i++) {
            System.out.println((i + 1) + ". " + productTitles.get(i).getText());
        }
        System.out.println("Product search results validated successfully.");
    }

}

