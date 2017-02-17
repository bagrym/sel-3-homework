package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 2/172017.
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

public class LitecardGeoZonesSorting {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void LitecardGeoZonesSorting () {
        driver.get("http://localhost/litecard/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecard/admin/?app=geo_zones&doc=geo_zones");

        List<WebElement> countryRows = driver.findElements(By.cssSelector("tr.row"));
        for (int i = 0; i < countryRows.size(); i++) {
            countryRows = driver.findElements(By.cssSelector("tr.row"));

            String pageLink = countryRows.get(i).findElements(By.cssSelector("td")).get(2).findElement(By.cssSelector("a")).getAttribute("href");
            driver.get(pageLink);

            List<WebElement> zonesList = driver.findElements((By.cssSelector("select[name*=zone_code]")));
            List<String> zones = new ArrayList<>();
            for (WebElement zone: zonesList) {
                zones.add(zone.findElement(By.cssSelector("option[selected=selected]")).getAttribute("textContent").toString());
            }

            for (int j = 1; j < zones.size(); j++) {
                String zone1 = zones.get(j);
                String zone2 = zones.get(j-1);
                assert(zone1.compareTo(zone2) > 0);
            }

            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }
}
