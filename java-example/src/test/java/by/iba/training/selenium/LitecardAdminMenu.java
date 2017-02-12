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

public class LitecardAdminMenu {
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
        driver.get("http://localhost/litecard/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        ulMenu = driver.findElement(By.id("box-apps-menu"));
        List<WebElement> liList = ulMenu.findElements(By.tagName("li"));
        for (int i = 0; i < liList.size(); i++) {
            WebElement menuItem = driver.findElements(By.cssSelector("ul#box-apps-menu > li")).get(i);
            menuItem.click();

            menuItem = driver.findElements(By.cssSelector("ul#box-apps-menu > li")).get(i);
            List<WebElement> liSubList = menuItem.findElements(By.tagName("li"));
            for (int j = 0; j < liSubList.size(); j++) {
                driver.findElements(By.cssSelector("ul#box-apps-menu > li li")).get(j).click();
                assert(driver.findElements(By.tagName("h1")).size() > 0);
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }
}
