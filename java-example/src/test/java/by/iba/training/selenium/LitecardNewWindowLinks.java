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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class LitecardNewWindowLinks {
    private WebDriver driver;
    private WebDriverWait wait;

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> openedWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(openedWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

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
    public void LitecardNewWindow () {
        driver.get("http://localhost/litecard/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecard/admin/?app=countries&doc=countries");

        driver.findElement(By.cssSelector("a.button")).click();

        List<WebElement> externalLinks = driver.findElements(By.cssSelector("i.fa-external-link"));
        String originalWindow = driver.getWindowHandle();
        Set<String> openedWindows = driver.getWindowHandles();

        for (WebElement link: externalLinks
             ) {
            link.click();
            String NewWindow = wait.until(anyWindowOtherThan(openedWindows));
            driver.switchTo().window(NewWindow);
            driver.getTitle();
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}
