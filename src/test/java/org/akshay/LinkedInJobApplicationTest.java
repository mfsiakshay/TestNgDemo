package org.akshay;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class LinkedInJobApplicationTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get("https://www.linkedin.com/login");
    }

    @Test
    public void testJobApplicationWorkflow() throws InterruptedException {
        loginToLinkedIn("akshaysankhla01@gmail.com", "Akshay@7415");
        searchJobs();
        applyFilter();
        saveJob();
        verifySavedJobs();
    }

    private void loginToLinkedIn(String email, String password) {
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));

        usernameField.sendKeys(email);
        passwordField.sendKeys(password, Keys.ENTER);
    }

    private void searchJobs() {
        WebElement jobsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@type='job']")));
        jobsTab.click();

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jobs-search-box__text-input")));
        searchBox.sendKeys("Software Engineer", Keys.ENTER);
    }

    private void applyFilter() throws InterruptedException {
        driver.findElement(By.id("searchFilter_workplaceType")).click();
        driver.findElement(By.xpath("//span[text()='Remote']")).click();
        Thread.sleep(3000);
    }

    private void saveJob() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jobs-save-button")));
        saveButton.click();
    }


    private void verifySavedJobs() {
        WebElement myJobsTab = driver.findElement(By.xpath("//a[@href='/jobs/saved/']"));
        myJobsTab.click();

        WebElement savedJob = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".job-card-container")));
        Assert.assertTrue(savedJob.isDisplayed(), "Saved job not found.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

