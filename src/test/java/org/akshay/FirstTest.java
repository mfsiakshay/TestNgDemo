package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.*;

public class FirstTest extends BaseTest{

    @Test
    public void googleTitle() {
        String url = "http://www.google.com";
        extentTest.info("Navigating to google");
        driver.get(url);
        String expectedTitle = "Google";
        String actualTitle = driver.getTitle();
        extentTest.info("Got title as "+driver.getTitle());
        Assert.assertEquals(actualTitle, expectedTitle, "Title Mismatch");
        extentTest.pass("Assertion Passed : Title Matched");
    }

    @Test
    public void facebookLogin(){
        String targetUrl = "https://www.facebook.com";
        extentTest.info("Navigating to facebook");
        driver.get(targetUrl);
        extentTest.info("entering email id");
        driver.findElement(By.id("email")).sendKeys("Akshay",Keys.ENTER);
        String foundUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(foundUrl, targetUrl,"Url Match");
        extentTest.pass("Assertion Passed : Url MisMatched");
    }

}
