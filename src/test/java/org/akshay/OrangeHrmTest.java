package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrangeHrmTest extends BaseTest {

    @Test(priority = 1)
    public void launchApp() {
        System.out.println("Navigating to OrangeHRM website");
        String targetUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
        driver.get(targetUrl);
    }

    @Test(priority = 2)
    public void adminLogin() {
        String username = "Admin";
        String password = "admin123";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password, Keys.ENTER);
        System.out.println("Credentials Entered");
    }

    @Test(priority = 3)
    public void verifyLogin() {
        WebElement element = driver.findElement(By.xpath("//p[text()=\"Time at Work\"]"));
        Assert.assertTrue(element.isDisplayed(), "DashBoard Not Displayed");
    }

    @Test(priority = 4)
    public void NavToMyInfo() {
        System.out.println("Navigating to My Info Page");
        driver.findElement(By.xpath("//a[span[text()=\"My Info\"]]")).click();
    }

    @Test(priority = 5)
    public void verifyMyInfo() {
        WebElement infoDetails = driver.findElement(By.xpath("//h6[text()=\"Personal Details\"]"));
        Assert.assertTrue(infoDetails.isDisplayed(), "Personal Details Not Displayed");
    }
}
