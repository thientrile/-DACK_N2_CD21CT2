package test;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class Test2 {
    @Test
    public  void TestCase2() throws IOException {
        WebDriver driver = driverFactory.getEdgeDriver();
        try{
            driver.get("https://nshopvn.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("a[href='/products/']")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("img[title='Đế cảm biến hiện diện con người LD2410 220V 1 kênh']")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='product-summary--line product-a2c-button'] span:nth-child(2)")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='button is-info is-fullwidth']")))).click();
            Thread.sleep(2000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();

    }
}
