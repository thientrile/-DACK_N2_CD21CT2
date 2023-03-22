package test;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class Test1 {
    @Test
    public  void TestCase1() throws IOException {
        WebDriver driver = driverFactory.getEdgeDriver();
        try{
            driver.get("https://nshopvn.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("a[href='/products/']")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='dropdown filter'] span:nth-child(2)")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("a:nth-child(4) span:nth-child(2)")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='dropdown orderby is-right'] span:nth-child(2)")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > main:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > a:nth-child(2) > span:nth-child(1)")))).click();
            Thread.sleep(2000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();

    }
}
