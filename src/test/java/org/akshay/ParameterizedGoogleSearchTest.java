package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;

public class ParameterizedGoogleSearchTest extends BaseTest {

    @Test
    @Parameters({"searchQuery1", "searchQuery2", "searchQuery3"})
    public void testGoogleSearch(@Optional("TestNG Tutorial") String query1, @Optional("Katalon Tutorial") String query2, @Optional("Maven Tutorial") String query3) {
        performSearch(query1);
        verifySearchResults(query1);

        performSearch(query2);
        verifySearchResults(query2);

        performSearch(query3);
        verifySearchResults(query3);
    }

    private void performSearch(String query) {
        driver.get("https://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear();
        searchBox.sendKeys(query);
        searchBox.submit();
    }

//    private void verifySearchResults(String query) {
//        WebElement resultStats = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result-stats")));
//        Assert.assertTrue(resultStats.isDisplayed(), "Result stats not displayed.");
//        System.out.println("Search for '" + query + "' executed successfully.");
//    }
private void verifySearchResults(String query) {
    WebElement resultsContainer = driver.findElement(By.id("search"));
    Assert.assertTrue(resultsContainer.isDisplayed(), "Search results container is not displayed.");

    List<WebElement> resultLinks = resultsContainer.findElements(By.cssSelector("a h3"));
    Assert.assertFalse(resultLinks.isEmpty(), "No search results found.");
    extentTest.info("Search for '" + query + "' executed successfully. Found " + resultLinks.size() + " results.");
}
}

