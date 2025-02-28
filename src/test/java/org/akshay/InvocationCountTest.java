package org.akshay;
import org.openqa.selenium.By;
import org.testng.annotations.*;


public class InvocationCountTest extends BaseTest {

    @Test(invocationCount = 4, threadPoolSize = 2)
    public void randomUserTest() throws InterruptedException {
        driver.get("https://randomuser.me/");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[@data-label='name']")).click();
        extentTest.info("User name: "+ driver.findElement(By.id("user_value")).getText());

        driver.findElement(By.xpath("//li[@data-label='email']")).click();
        extentTest.info("Email address: "+driver.findElement(By.id("user_value")).getText());
    }
}
