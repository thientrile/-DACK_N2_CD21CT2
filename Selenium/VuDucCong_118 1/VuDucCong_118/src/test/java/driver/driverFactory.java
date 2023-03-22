package driver;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

public class driverFactory {

    public static WebDriver getEdgeDriver() {
        String currentProjectLocation = System.getProperty("user.dir");
        String edgeDriverRelativePath = "/src/test/resources/drivers/msedgedriver.exe";
        String edgeDriverLocation = currentProjectLocation.concat(edgeDriverRelativePath);
        System.setProperty("webdriver.edge.driver", edgeDriverLocation);
        EdgeDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        return driver;
    }
}