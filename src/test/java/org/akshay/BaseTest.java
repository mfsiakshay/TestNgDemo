package org.akshay;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    public static WebDriver driver;
    public static String screenshotSubFolderName;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;

    @BeforeSuite
    public void initialiseExtentReports(){
        extentReports = new ExtentReports();
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("Tests.html");
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("OS", System.getProperty("os.name")+System.getProperty("os.version"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("User Name",System.getProperty("user.name"));
    }

    @BeforeTest
    public void setUp(ITestContext context) throws MalformedURLException {
        extentTest = extentReports.createTest(context.getName());
        //String browserName = context.getCurrentXmlTest().getParameter("browserName");
        String browserName = System.getProperty("browser","chrome");
        boolean isRemote = Boolean.parseBoolean(System.getProperty("remote","false"));
//        if(browserName==null){
//            browserName="chrome";
//        }
        if(isRemote){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browserName.toLowerCase());
            driver = new RemoteWebDriver(new URL("http://selenium-hub:4444/wd/hub"), capabilities);
        }else {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    extentTest.info("Chrome Browser Started");
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    extentTest.info("MS Edge Browser Started");
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    extentTest.info("Firefox Browser Started");
                    break;
                default:
                    extentTest.warning("Browser Name is invalid");
                    break;
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        String deviceInfo = capabilities.getBrowserName()+" "+capabilities.getBrowserVersion();
        String author = context.getCurrentXmlTest().getParameter("author");
        if(author==null){
            author="Akshay";
        }
        extentTest.assignAuthor(author);
        extentTest.assignDevice(deviceInfo);
    }

    public String captureScreenshot(String fileName){
        if(screenshotSubFolderName == null){
            LocalDateTime currDateTime = LocalDateTime.now();
            DateTimeFormatter formalFormat = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            screenshotSubFolderName = currDateTime.format(formalFormat);
        }
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./Screenshots/"+screenshotSubFolderName+"/"+fileName+".jpg");
        try {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentTest.info("Screenshot saved successfully");
        return destFile.getPath();
    }

    @AfterMethod
    public void checkStatus(Method m, ITestResult result){
        if(result.getStatus()==ITestResult.FAILURE){
               String screenShotPath = captureScreenshot(result.getTestContext().getName()+"_"+result.getMethod().getMethodName());
               extentTest.addScreenCaptureFromPath(screenShotPath);
               extentTest.fail(result.getThrowable());
        } else if (result.getStatus()==ITestResult.SUCCESS) {
            extentTest.pass(m.getName() + " is Passed");
        } else if (result.getStatus()==ITestResult.SKIP) {
            extentTest.skip(m.getName() + " is Skipped");
        }
        extentTest.assignCategory(m.getAnnotation(Test.class).groups());
    }


    @AfterTest
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }

    @AfterSuite
    public void generateExtentReports() {
        extentReports.flush();
        //Desktop.getDesktop().browse(new File("Tests.html").toURI());
    }
}
