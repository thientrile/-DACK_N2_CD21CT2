package test;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import java.time.Duration;

public class TestCase8 {
    @Test
    public  void TestCase8() throws IOException {
        WebDriver driver = driverFactory.getEdgeDriver();

        try{
            /*1. Go to 'https://nshopvn.com'*/
            driver.get("https://nshopvn.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            /*2. Click on 'Tài Khoản'*/
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='site-header-wrapper'] a:nth-child(2)")))).click();
            /*3. Click on 'Đăng nhập'*/
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("a[class='button block is-primary is-fullwidth']")))).click();
            /*4. Find phonenumber web element*/
            Thread.sleep(2000);
            WebElement phonenumber = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Nhập số điện thoại']"))));
            /*5. Find password web element*/
            WebElement password = driver.findElement(By.cssSelector("input[placeholder='Nhập mật khẩu']"));
            /*6. Find login btn web element*/
            WebElement login = driver.findElement(By.cssSelector("button[class='button block is-info is-fullwidth']"));
            /*7. Input password, input password, click on Login btn*/
            phonenumber.sendKeys("0834822676");
            password.sendKeys("123");
            login.click();
            /*8. Select a product*/
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("img[title='Nam châm cứu hộ siêu mạnh có móc treo']")).click();
            /*9. Click on 'Chọn mua'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//span[contains(text(),'Chọn mua')]")).click();
            /*10. Click on 'Xem giỏ hàng'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//a[@class='button is-info is-fullwidth']")).click();
            /*11. Change QTY*/
            Thread.sleep(2000);
            WebElement QTY = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/main[1]/div[1]/div[1]/ul[1]/li[1]/div[1]/div[2]/div[2]/div[1]/p[2]/button[1]/span[1]"));
            Thread.sleep(1000);
            QTY.click();
            Thread.sleep(1000);
            QTY.click();
            Thread.sleep(1000);
            QTY.click();
            /*12. Click on 'Đặt hàng'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//a[contains(text(),'Đặt hàng')]")).click();
            /*13. Find name web element*/
            Thread.sleep(2000);
            WebElement nameElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Nhập họ tên']"))));
            WebElement continueElem = driver.findElement(By.cssSelector("button[type='submit'] span:nth-child(2)"));
            /*14. Input name, click on continue btn*/
            nameElem.clear();
            nameElem.sendKeys("Phạm Minh Thiện");
            Thread.sleep(2000);
            continueElem.click();
            /*15. Choose information*/
            Thread.sleep(2000);
            Select dropdown1 = new Select(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/main[1]/form[1]/section[1]/div[1]/div[1]/div[1]/select[1]")))));
            dropdown1.selectByValue("Hồ Chí Minh");
            Select dropdown2 = new Select(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/main[1]/form[1]/section[1]/div[2]/div[1]/div[1]/select[1]")))));
            dropdown2.selectByValue("Quận Tân Phú");
            Select dropdown3 = new Select(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/main[1]/form[1]/section[1]/div[3]/div[1]/div[1]/select[1]")))));
            dropdown3.selectByValue("Phường Hòa Thạnh");
            /*16. Find address web element*/
            WebElement addressElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Nhập địa chỉ']"))));
            WebElement continue2BtnElem = driver.findElement(By.cssSelector("button[type='submit'] span:nth-child(2)"));
            /*17. Input address, click on continue2 btn*/
            addressElem.clear();
            addressElem.sendKeys("12 Trịnh Đình Thảo");
            Thread.sleep(2000);
            continue2BtnElem.click();
            /*18. Choose transfer/momo, Click on 'Tiếp tục'*/
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("label[for='payment_method_bacs'] p[class='_description']")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("button[type='submit'] span:nth-child(2)")).click();
            /*19. Verify the total amount temporarily*/
            Thread.sleep(1000);
            String TemTotal = driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > form > section > table > tbody > tr > td:nth-child(4) > p")).getAttribute("innerHTML");
            System.out.println(TemTotal.equals("148.000₫")?"TemTotal: Pass":"TemTotal: Fail");
            /*20. Verify Fee Shipping*/
            Thread.sleep(1000);
            String FeeShipping = driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > form > section > table > tfoot > tr:nth-child(1) > td")).getAttribute("innerHTML");
            System.out.println(FeeShipping.equals("16.500₫")?"FeeShipping: Pass":"FeeShipping: Fail");
            /*21. Verify Grand Total*/
            Thread.sleep(1000);
            String GrandTotal = driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > form > section > table > tfoot > tr:nth-child(2) > td > strong")).getAttribute("innerHTML");
            System.out.println(GrandTotal.equals("164.500₫")?"GrandTotal: Pass":"GrandTotal: Fail");
            /*22. Show 'Print Order'*/
            Thread.sleep(2000);
            // Create JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Print
            js.executeScript("window.print();");
            Thread.sleep(2000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();

    }
}
