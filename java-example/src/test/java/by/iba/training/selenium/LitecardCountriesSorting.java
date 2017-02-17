package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 2/16/2017.
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

public class LitecardCountriesSorting {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void LitecardCountriesSorting () {
        driver.get("http://localhost/litecard/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecard/admin/?app=countries&doc=countries");

        //a.Check countries list sorting at main page first
        List<WebElement> contriesList = driver.findElements(By.cssSelector("tr.row a:not([title])"));
        for (int i = 1; i < contriesList.size(); i++) {
            String country1 = contriesList.get(i).getAttribute("textContent").toString();
            String country2 = contriesList.get(i-1).getAttribute("textContent").toString();
            assert(country1.compareTo(country2) > 0);
        }

        //б.Search for country rows with zones number > 0 and check zones sorting at new page
        for (int j = 0; j < driver.findElements(By.cssSelector("tr.row")).size(); j++) {
            List<WebElement> countryRows = driver.findElements(By.cssSelector("tr.row"));
            WebElement zonesCount = countryRows.get(j).findElements(By.cssSelector("td")).get(5);

            if (zonesCount.getAttribute("textContent").compareTo("0") != 0) {
                String pageLink = countryRows.get(j).findElements(By.cssSelector("td")).get(4).findElement(By.cssSelector("a")).getAttribute("href");
                driver.get(pageLink);

                List<WebElement> zonesList = driver.findElements(By.cssSelector("input[type=hidden][name*=name]"));
                for (int k = 1; k < zonesList.size(); k++) {
                    String zone1 = zonesList.get(k).getAttribute("value").toString();
                    String zone2 = zonesList.get(k-1).getAttribute("value").toString();
                    assert(zone1.compareTo(zone2) > 0);
                }

                driver.navigate().back();
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }
}
