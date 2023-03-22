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

public class TestCase7 {
    @Test
    public  void TestCase7() throws IOException {
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
            /*7. Input phonenumber, input password, click on Login btn*/
            phonenumber.sendKeys("0834822676");
            password.sendKeys("123");
            login.click();
            /*8. Click on '@0919618654'*/
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("div[class='site-header-wrapper'] a:nth-child(2)")).click();
            /*9. Click on 'Đơn hàng'*/
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("a[href='/account/orders/']")).click();
            /*10. Click on 'Chi tiết đơn hàng'*/
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div.block-ribs > section > article > div.right")).click();
            /*11. Verify the previously created order is displayed in 'Order Details' table and status is Pending*/
            Thread.sleep(2000);
            String Orderid = driver.findElement(By.cssSelector("#__layout > div > div.container > div > main > div > section > div:nth-child(1) > p.value > strong")).getAttribute("innerHTML");
            System.out.println(Orderid.equals("#112048")?"OrderId: Pass":"OrderId: Fail");
            /*12. Show 'Print Order'*/
            Thread.sleep(2000);
            // Create JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Print
            js.executeScript("window.print();");
            Thread.sleep(5000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();

    }
}
