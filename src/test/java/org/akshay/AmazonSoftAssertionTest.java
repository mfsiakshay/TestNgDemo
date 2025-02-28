package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class AmazonSoftAssertionTest extends BaseTest {

    @Test
    public void validateAmazonUIElements() {
        extentTest.info("Navigating to Amazon website");
        driver.get("https://www.amazon.com");
        SoftAssert softAssert = new SoftAssert();

        extentTest.info("Validate page title");
        String expectedTitle = "Amazon.com. Spend less. smile more.";
        String actualTitle = driver.getTitle();
        softAssert.assertEquals(actualTitle, expectedTitle, "Page title mismatch!");

        extentTest.info("Verify the presence of the search box");
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        softAssert.assertTrue(searchBox.isDisplayed(), "Search box is not displayed!");

        extentTest.info("Verify the presence of the cart icon");
        WebElement cartIcon = driver.findElement(By.id("nav-cart"));
        softAssert.assertTrue(cartIcon.isDisplayed(), "Cart icon is not displayed!");

        extentTest.info("Validate the text of the sign-in button");
        WebElement signInButton = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
        String expectedSignInText = "Hello, sign in";
        String actualSignInText = signInButton.getText();
        softAssert.assertEquals(actualSignInText, expectedSignInText, "Sign-in button text mismatch!");

        softAssert.assertAll();
    }

}

