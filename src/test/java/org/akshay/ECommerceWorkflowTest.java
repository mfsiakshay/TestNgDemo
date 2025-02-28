package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ECommerceWorkflowTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    @Test
    public void testECommerceWorkflow() throws InterruptedException {
        // Login
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Add items to the cart
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));
        for (WebElement button : addToCartButtons) {
            button.click();
        }

        // Navigate to the cart
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        // Validate items in the cart
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(cartItems.size(), addToCartButtons.size(), "Item count mismatch in the cart");

        // Proceed to checkout
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();

        // Fill checkout form
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        driver.findElement(By.id("continue")).click();

        // Validate order summary
        WebElement totalPrice = driver.findElement(By.className("summary_total_label"));
        Assert.assertTrue(totalPrice.getText().contains("$"), "Total price missing or invalid");

        // Finish the order
        driver.findElement(By.id("finish")).click();

        // Confirm success message
        WebElement successMessage = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(successMessage.getText(), "Thank you for your order!", "Order success message not found");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

