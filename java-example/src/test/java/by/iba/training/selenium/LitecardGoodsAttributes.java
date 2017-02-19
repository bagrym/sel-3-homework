package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 2/182017.
 */
import org.junit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class LitecardGoodsAttributes {
    private WebDriver driver;
    private String currentBrowser;

    //Method to conver font-size value to float
    private float getPxNumber (String pxString) {
        return Float.valueOf(pxString.substring(0, pxString.length() - 2));
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        currentBrowser = ((HasCapabilities) driver).getCapabilities().getBrowserName();
        //System.out.println(currentBrowser);
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }

    @Test
    public void CompareCoodsTitle () {
        driver.get("http://localhost/litecard");
        WebElement compaignGoodsMain = driver.findElement(By.cssSelector("div#box-campaigns li:first-child"));
        String titleMain = compaignGoodsMain.findElement(By.cssSelector("div.name")).getAttribute("textContent");

        compaignGoodsMain.click();
        String titleDetails = driver.findElement(By.cssSelector("h1.title")).getAttribute("textContent");
        assert(titleMain.compareTo(titleDetails) == 0);
    }

    @Test
    public void CompareGoodsPrices () {
        driver.get("http://localhost/litecard");
        WebElement compaignGoodsMain = driver.findElement(By.cssSelector("div#box-campaigns li:first-child"));
        String regularPriceMain = compaignGoodsMain.findElement(By.cssSelector("div.price-wrapper s.regular-price")).getAttribute("textContent");
        String campaignPriceMain = compaignGoodsMain.findElement(By.cssSelector("div.price-wrapper strong.campaign-price")).getAttribute("textContent");

        compaignGoodsMain.click();
        String regularPriceDetails = driver.findElement(By.cssSelector("div.content div.price-wrapper s.regular-price")).getAttribute("textContent");
        String campaignPriceDetails = driver.findElement(By.cssSelector("div.content div.price-wrapper strong.campaign-price")).getAttribute("textContent");

        assert(regularPriceMain.compareTo(regularPriceDetails) == 0);
        assert(campaignPriceMain.compareTo(campaignPriceDetails) == 0);
    }

    @Test
    public void CheckGoodsPricesStyle () {
        driver.get("http://localhost/litecard");
        WebElement compaignGoodsMain = driver.findElement(By.cssSelector("div#box-campaigns li:first-child"));
        WebElement regularPriceMain = compaignGoodsMain.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        WebElement campaignPriceMain = compaignGoodsMain.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));

        //Regular price styles checks at main
        assert(regularPriceMain.getCssValue("text-decoration").compareTo("line-through") == 0);
        if (currentBrowser.compareTo("Firefox") == 0) {
            assert(regularPriceMain.getCssValue("color").compareTo("rgb(119, 119, 119)") == 0);
        }
        else {
            assert(regularPriceMain.getCssValue("color").compareTo("rgba(119, 119, 119, 1)") == 0);
        }

        //Campaign price styles checks at main
        if (currentBrowser.compareTo("chrome") == 0) {
            assert(campaignPriceMain.getCssValue("font-weight").compareTo("bold") == 0);
            assert(campaignPriceMain.getCssValue("color").compareTo("rgba(204, 0, 0, 1)") == 0);
        }
        else if (currentBrowser.compareTo("Firefox") == 0) {
            assert(campaignPriceMain.getCssValue("font-weight").compareTo("900") == 0);
            assert(campaignPriceMain.getCssValue("color").compareTo("rgb(204, 0, 0)") == 0);
        }
        else {
            assert(campaignPriceMain.getCssValue("font-weight").compareTo("900") == 0);
            assert(campaignPriceMain.getCssValue("color").compareTo("rgba(204, 0, 0, 1)") == 0);
        }

        compaignGoodsMain.click();
        WebElement regularPriceDetails = driver.findElement(By.cssSelector("div.content div.price-wrapper s.regular-price"));
        WebElement campaignPriceDetails = driver.findElement(By.cssSelector("div.content div.price-wrapper strong.campaign-price"));

        //Regular price styles checks at details
        assert(regularPriceDetails.getCssValue("text-decoration").compareTo("line-through") == 0);
        if (currentBrowser.compareTo("Firefox") == 0) {
            assert(regularPriceDetails.getCssValue("color").compareTo("rgb(119, 119, 119)") == 0);
        }
        else {
            assert(regularPriceDetails.getCssValue("color").compareTo("rgba(102, 102, 102, 1)") == 0);
        }
        //Campaign price styles checks at details
        if (currentBrowser.compareTo("chrome") == 0) {
            assert(campaignPriceDetails.getCssValue("font-weight").compareTo("bold") == 0);
            assert(campaignPriceDetails.getCssValue("color").compareTo("rgba(204, 0, 0, 1)") == 0);
        }
        else if (currentBrowser.compareTo("Firefox") == 0) {
            System.out.println(campaignPriceDetails.getCssValue("font-weight"));
            assert(campaignPriceDetails.getCssValue("font-weight").compareTo("900") == 0);
            assert(campaignPriceDetails.getCssValue("color").compareTo("rgb(204, 0, 0)") == 0);
        }
        else {
            assert(campaignPriceDetails.getCssValue("font-weight").compareTo("700") == 0);
            assert(campaignPriceDetails.getCssValue("color").compareTo("rgba(204, 0, 0, 1)") == 0);
        }
    }

    @Test
    public void CheckGoodsPricesSize () {
        driver.get("http://localhost/litecard");
        WebElement compaignGoodsMain = driver.findElement(By.cssSelector("div#box-campaigns li:first-child"));
        WebElement regularPriceMain = compaignGoodsMain.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        WebElement campaignPriceMain = compaignGoodsMain.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));

        //Compare prices size at main
        assert(getPxNumber(regularPriceMain.getCssValue("font-size")) < getPxNumber(campaignPriceMain.getCssValue("font-size")));

        compaignGoodsMain.click();
        WebElement regularPriceDetails = driver.findElement(By.cssSelector("div.content div.price-wrapper s.regular-price"));
        WebElement campaignPriceDetails = driver.findElement(By.cssSelector("div.content div.price-wrapper strong.campaign-price"));

        //Compare prices size at details
        assert(getPxNumber(regularPriceDetails.getCssValue("font-size")) < getPxNumber(campaignPriceDetails.getCssValue("font-size")));

    }

}
