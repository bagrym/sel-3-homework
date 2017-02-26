package by.iba.training.selenium;

/**
 * Created by VEREMENYUK_P on 2/26/2017.
 */
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LitecardAddProduct {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
    }

    @After
    public void stop() {
        driver.quit();
        driver=null;
    }

    @Test
    public void AddProduct () {
        //enter site admin page
        driver.get("http://localhost/litecard/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //enter Catalog
        driver.findElement(By.cssSelector("#box-apps-menu li#app- > a[href$=catalog]")).click();

        //click Add new product
        driver.findElement(By.cssSelector("a.button[href$=edit_product]")).click();

        //manage general tab
        driver.findElement(By.cssSelector("input[type=radio][value='1']")).click();
        driver.findElement(By.cssSelector(("input[name^=name]"))).sendKeys("Rubber goose");
        driver.findElement(By.cssSelector("input[type=checkbox][value='1-3']")).click();
        new Actions(driver).doubleClick(driver.findElement(By.name("quantity"))).sendKeys(Keys.DELETE).sendKeys("5").perform();

        //image file path processing
        Path currentFolder = Paths.get("");
        String imgFilePath = currentFolder.toAbsolutePath().toString();
        imgFilePath += "\\rubber goose.jpg";
        driver.findElement(By.cssSelector("input[name^=new_images]")).sendKeys(imgFilePath);

        //validity dates processing
        new Actions(driver).moveToElement(driver.findElement(By.cssSelector("input[name=date_valid_from]")),3,3).click().sendKeys("02262017").perform();
        new Actions(driver).moveToElement(driver.findElement(By.cssSelector("input[name=date_valid_to]")),3,3).click().sendKeys("12312017").perform();

        //manage information tab
        driver.findElement(By.cssSelector("a[href$=tab-information]")).click();

        Select manufacturerSelect = new Select(driver.findElement(By.name("manufacturer_id")));
        manufacturerSelect.selectByValue("1");

        driver.findElement(By.cssSelector("input[name^=short_description]")).sendKeys("rubber goose");
        driver.findElement(By.cssSelector("input[name^=head_title]")).sendKeys("rubber goose");

        //manage information tab
        driver.findElement(By.cssSelector("a[href$=tab-prices]")).click();
        new Actions(driver).doubleClick(driver.findElement(By.name("purchase_price"))).sendKeys(Keys.DELETE).sendKeys("20").perform();

        Select currencySelect = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        currencySelect.selectByValue("USD");

        //save new product
        driver.findElement(By.name("save")).click();

        //check new product has been added
        driver.findElement(By.cssSelector("#box-apps-menu li#app- > a[href$=catalog]")).click();

        boolean productPresent = false;

        List<WebElement> productList = driver.findElements(By.cssSelector("table.dataTable tr.row"));
        for (int i = 0; i < productList.size(); i++ ) {
            if (productList.get(i).findElements(By.tagName("td")).get(2).getAttribute("textContent").compareTo(" Rubber goose") == 0) {
                productPresent = true;
            }
        }
        assert(productPresent);
    }

}
