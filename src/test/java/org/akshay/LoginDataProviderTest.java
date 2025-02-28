package org.akshay;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginDataProviderTest extends BaseTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp(ITestContext context) {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        extentTest = extentReports.createTest(context.getName());
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        String deviceInfo = capabilities.getBrowserName()+" "+capabilities.getBrowserVersion();
        String author = context.getCurrentXmlTest().getParameter("author");
        extentTest.assignAuthor(author);
        extentTest.assignDevice(deviceInfo);
    }


    @Test(dataProvider = "loginData", dataProviderClass = ExcelDataSupplier.class)
    public void testLogin(String username, String password, String expectedResult) {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password, Keys.ENTER);
        extentTest.info("Credentials Entered: Username = "+username+" & password = "+password+" with expected result : "+expectedResult);

        String isLoginSuccessful = "TRUE";
        try {
            WebElement dashboard = driver.findElement(By.xpath("//p[text()=\"Time at Work\"]"));
            isLoginSuccessful = String.valueOf(dashboard.isDisplayed()).toUpperCase();
        } catch (Exception e) {
            isLoginSuccessful = "FALSE";
        }

        Assert.assertEquals(isLoginSuccessful, expectedResult,
                "Login test failed for username: " + username);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

