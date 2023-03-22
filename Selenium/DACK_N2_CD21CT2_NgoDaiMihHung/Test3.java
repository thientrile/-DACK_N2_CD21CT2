package test;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class Test3 {
    @Test
    public  void TestCase3() throws IOException {
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
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("img[title='Đồng hồ điện tử ANENG DT830G']")))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='product-summary--line product-a2c-button'] span:nth-child(2)")))).click();
            Thread.sleep(2000);
            WebElement quanlityElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[type='number']"))));
            WebElement clickElem = driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > article > div > div.overlay.overlay-bottom-sheet > div.overlay-content > div > footer > a"));
            quanlityElem.clear();
            quanlityElem.sendKeys("000");
            Thread.sleep(2000);
            clickElem.click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("div[class='line-remove'] span[class='icon']")).click();
            Thread.sleep(2000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();

    }
}
