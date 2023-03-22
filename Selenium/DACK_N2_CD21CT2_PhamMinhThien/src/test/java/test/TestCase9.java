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

import java.io.IOException;
import java.time.Duration;

public class TestCase9 {
    @Test
    public  void TestCase9() throws IOException {
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
            WebElement phonenumber= wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Nhập số điện thoại']"))));
            /*5. Find password web element*/
            WebElement password= driver.findElement(By.cssSelector("input[placeholder='Nhập mật khẩu']"));
            /*6. Find login btn web element*/
            WebElement login = driver.findElement(By.cssSelector("button[class='button block is-info is-fullwidth']"));
            /*7. Input password, input password, click on Login btn*/
            phonenumber.sendKeys("0834822676");
            password.sendKeys("123");
            login.click();
            /*8. Select a product*/
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("img[title='Dimmer LED 30A điều chỉnh độ sáng bằng tay và Remote 12-24V']")).click();
            /*9. Click on 'Chọn mua'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//span[contains(text(),'Chọn mua')]")).click();
            /*10. Click on 'Xem giỏ hàng'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//a[@class='button is-info is-fullwidth']")).click();
            /*11. Click on 'Nhận tại cửa hàng'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//a[contains(text(),'Nhận tại cửa hàng')]")).click();
            /*12. Find name web element*/
            Thread.sleep(2000);
            WebElement nameElem = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Nhập họ tên']"))));
            /*13. Input name*/
            nameElem.clear();
            nameElem.sendKeys("Phạm Minh Thiện");
            /*14. Click on 'Chọn mã giảm giá'*/
            Thread.sleep(2000);
            driver.findElement(By.xpath("//a[@class='button is-small is-outlined is-info']")).click();
            Thread.sleep(2000);
            /*15. Find discountcode web element*/
            WebElement discountcodeElem = driver.findElement(By.cssSelector("input[placeholder='Nhập mã']"));
            WebElement checkElem = driver.findElement(By.cssSelector("a[class='button is-info']"));
            /*16. Input discountcode click on check btn*/
            discountcodeElem.sendKeys("NSHOP2426BDAY");
            Thread.sleep(2000);
            checkElem.click();
            /*SKIP*/
            Thread.sleep(5000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();

    }
}
