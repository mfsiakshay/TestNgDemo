package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AmazonShoppingTest extends BaseTest {
    WebDriverWait wait;

    @Test
    public void testShoppingWorkflow() throws InterruptedException {
        extentTest.info("Navigating to Amazon website");
        driver.get("https://www.amazon.in");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        searchProduct("headphones");
        applyFilter("Over-Ear Headphones");
        addFirstProductToCart();
        verifyCartContents();
    }

    private void searchProduct(String productName) {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        extentTest.info("Searching for "+productName);
        searchBox.sendKeys(productName);
        searchBox.submit();
    }

    private void applyFilter(String filterName) throws InterruptedException {
        extentTest.info("Applying Filter: "+filterName);
        WebElement filter = driver.findElement(By.xpath("//span[text()='" + filterName + "']"));
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",filter);
        filter.click();
        Thread.sleep(3000); // Wait for filter to apply
    }

    private void addFirstProductToCart() {
        List<WebElement> products = driver.findElements(By.xpath("//div[@role='listitem']"));
        Assert.assertFalse(products.isEmpty(), "No products found.");
        extentTest.info("Adding 1st Product to cart");
        WebElement firstProduct = products.get(0);
        firstProduct.click();

        extentTest.info("Switching to New Tab");
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton.click();
    }

    private void verifyCartContents() {
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
        cartButton.click();
        extentTest.info("Verifying Item is present in cart");
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sc-list-item")));
        Assert.assertTrue(cartItem.isDisplayed(), "Cart item not found.");
        extentTest.info("Assertion Pass: Item Added to cart successfully ");
    }
}

