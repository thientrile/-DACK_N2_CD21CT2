package test;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Locale;

import static org.testng.AssertJUnit.assertEquals;

public class Test6 {
    public  String  Email= "";
    @Test
    public  void TestTechpanda6() throws IOException {
        WebDriver driver = driverFactory.getEdgeDriver();


        try{

            //link vao nshop
            driver.get("https://nshopvn.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


            // dang nhap
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#index > div:nth-child(1) > div.site-header-wrapper > header > div > div > div.site-header--end > nav > a:nth-child(2)")))).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > a:nth-child(2)")))).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div:nth-child(1) > div > input")))).sendKeys("0919618654");
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div:nth-child(2) > div > input")))).sendKeys("1");


            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div.field.spacing.is-1-5 > button")))).click();
        // them san pham vao gio hang
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#index > div.container > div > main > section:nth-child(3) > div > ul > li:nth-child(2)")))).click();
            Thread.sleep(2000);
        //tiến hành thêm sản phầm vào giỏ hàng
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > article > div > div.product-summary-content > div.product-offer > div > div.product-summary--line.product-a2c-button > div > button")))).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > article > div > div.overlay.overlay-bottom-sheet > div.overlay-content > div > footer > a")))).click();



//            WebElement DKElem = driver.findElement(By.cssSelector("button[title='Register'] span span"));
//            DKElem.click();
            Thread.sleep(100000);
        }
        catch (Exception e){
            System.out.println(e);
        }
        driver.quit();
    }
}
