package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 2/27/2017.
 */
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LitecardAddGoodsToCart {
    private WebDriver driver;
    private WebDriverWait wait;

    private int cartCount = 0;

    //number items to add to cart
    private int itemsToCart = 3;

    //method to add item to cart
    public void addFirstItemToCart() {
        driver.findElement(By.cssSelector("div#box-most-popular li:first-child")).click();
        WebElement quantity = driver.findElement(By.cssSelector("span.quantity"));

        //some items require size selcetion
        if (driver.findElements(By.cssSelector("select[name^=options]")).size() > 0) {
            Select sizeSelect = new Select(driver.findElement(By.cssSelector("select[name^=options]")));
            sizeSelect.selectByValue("Small");
        }

        driver.findElement(By.name("add_cart_product")).click();
        cartCount++;

        //waiting until text in quantity span changes
        wait.until(textToBePresentInElement(quantity, String.valueOf(cartCount)));

        driver.navigate().back();
    }

    //method to remove item from cart
    public void removeItemFromCart() {
        //store current table size
        int initialTableSize = driver.findElements(By.cssSelector("table.dataTable tr")).size();
        driver.findElement(By.name("remove_cart_item")).click();

        if (driver.findElements(By.cssSelector("table.dataTable")).size() > 0 ) {
            //waiting until table size changes
            wait.until((WebDriver d) -> d.findElements(By.cssSelector("table.dataTable tr")).size() < initialTableSize);
        }
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }

    @Test
    public void LitecardAddGoodsToCart () {

        driver.get("http://localhost/litecard");

        int count = 0;
        while (count < itemsToCart) {
            addFirstItemToCart();
            count++;
        }

        driver.findElement(By.cssSelector("div#cart a.link")).click();
        int tableSize = driver.findElements(By.cssSelector("table.dataTable tr")).size() - 5;

        //run remove method [tableSize] times
        count = 0;
        while (count < tableSize) {
            removeItemFromCart();
            count++;
        }
    }
}
