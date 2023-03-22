package test;

import driver.driverFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class Test5 {
    // TESTCASE 1

    @Test
    public void TestCase5() throws IOException {
        WebDriver driver = driverFactory.getEdgeDriver();
        driver.get("https://nshopvn.com/");
        //        WebElement loginBtnElem = driver.findElement(By.cssSelector("button[type='submit']"));
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            //. Nhấp vào tai khoang

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#index > div:nth-child(1) > div.site-header-wrapper > header > div > div > div.site-header--end > nav > a:nth-child(2)")))).click();
            //nhap vao dang ky
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > a.button.block.is-primary.is-outlined.is-fullwidth")))).click();
            // dien day du thong tin can thiet
            WebElement FirstNameElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div:nth-child(1) > div > input"))));
            FirstNameElem.sendKeys("0772135921");
            WebElement MiddleNameElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div:nth-child(2) > div > input"))));
            MiddleNameElem.sendKeys("congvu2k.84772@gmail.com");
            WebElement LastNameElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div:nth-child(3) > div > input"))));
            LastNameElem.sendKeys("Vũ Đức Công");
            WebElement EmailElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div:nth-child(4) > div > input"))));
            EmailElem.sendKeys("123456");

            //click vao button
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > form > div.field.spacing-1-5 > button")))).click();

            //5. Xác minh Đăng ký đã hoàn tất. Dự kiến đăng ký tài khoản xong.

            Thread.sleep(7000);
            WebElement DKElem = driver.findElement(By.cssSelector("button[title='Register'] span span"));
            DKElem.click();
            Thread.sleep(100000);
        } catch (Exception ignored) {
        }
        driver.close();
        driver.quit();


    }}
