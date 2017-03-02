package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 03/01/2017.
 */
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class LitecardErrorLogs {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }

    @Test
    public void ErrorLogs () {
        driver.get("http://localhost/litecard/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecard/admin/?app=catalog&doc=catalog&category_id=1");

        int numberOfProducts = driver.findElements(By.cssSelector(".dataTable tr.row")).size() - 3;

        for (int i = 0; i < numberOfProducts; i++) {
            List<WebElement> productRows = driver.findElements(By.cssSelector(".dataTable tr.row"));
            productRows = productRows.subList(3, productRows.size());
            productRows.get(i).findElements(By.tagName("td")).get(2).findElement(By.tagName("a")).click();

            driver.manage().logs().get("browser").forEach(m -> System.out.println(m));

            driver.navigate().back();
        }
    }
}
