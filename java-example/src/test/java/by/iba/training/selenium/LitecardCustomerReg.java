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
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.nio.channels.SelectableChannel;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LitecardCustomerReg {
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
    public void CustomerReg () {
        //customer data
        String firstName = "Pavel";
        String lastName = "Veremenyuk";
        String address1 = "Selenium str, 1";
        String postCode = "12345";
        String city = "New York";
        String country = "United States";
        String state = "NY";
        String phone = "+123456789";
        String email = "p2.v@selenium.org";
        String password = "customer";

        driver.get("http://localhost/litecard/en/");

        //fill customer text fields
        driver.findElement(By.name("login_form")).findElement(By.cssSelector("tr:last-of-type")).click();
        driver.findElement(By.name("firstname")).sendKeys(firstName);
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("address1")).sendKeys(address1);
        driver.findElement(By.name("postcode")).sendKeys(postCode);
        driver.findElement(By.name("city")).sendKeys(city);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys(phone);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);

        //select country
        new Actions(driver).click(driver.findElement(By.cssSelector(".select2"))).sendKeys(country + Keys.ENTER).perform();

        //select state
        Select stateSelect = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        stateSelect.selectByValue(state);

        //create account
        driver.findElement(By.name("create_account")).click();

        //logout
        driver.findElement(By.cssSelector("#box-account li:last-of-type a")).click();

        //log in
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        //logout again
        driver.findElement(By.cssSelector("#box-account li:last-of-type a")).click();
    }

}
