package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 1/30/2017.
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class LitecardStickersCheckAtMain {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement ulMenu;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void LitecardAdminMenu () {
        driver.get("http://localhost/litecard");
        List<WebElement> productList = driver.findElements(By.cssSelector("li.product"));
        for (WebElement product: productList
             ) {
            assert(product.findElements(By.cssSelector("div[class^=sticker]")).size() == 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }
}
