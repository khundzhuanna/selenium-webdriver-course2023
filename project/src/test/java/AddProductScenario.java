import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AddProductScenario {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    public void login() {
        driver.navigate().to("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void addingProduct() {
        login();
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        driver.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product']")).click();
        driver.findElement(By.xpath("//input[@type='radio'][@value='1']")).click(); //status

        driver.findElement(By.xpath("//input[@name='name[en]']")).sendKeys("Bunny"); //name
        driver.findElement(By.xpath("//input[@name='code']")).sendKeys("12345678"); //code
        //driver.findElement(By.xpath("//input[@name='product_groups[]' and @value='1-3']")).click(); //categories
        driver.findElement(By.xpath("//input[@name='product_groups[]' and @value='1-3']")).click(); //product groups

        driver.findElement(By.xpath("//input[@name='quantity']")).clear(); //quantity
        driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("2"); //quantity 2

        WebElement uploadElement = driver.findElement(By.xpath("//input[@name='new_images[]']"));
        uploadElement.sendKeys(System.getProperty("user.dir") + "/src/test/java/image.png");

        driver.findElement(By.xpath("//input[@name='date_valid_from']")).click();
        ((JavascriptExecutor) driver).executeScript("document.getElementsByName('date_valid_from')[0].value='2023-05-01'");

        driver.findElement(By.xpath("//input[@name='date_valid_to']")).click();
        ((JavascriptExecutor) driver).executeScript("document.getElementsByName('date_valid_from')[0].value='2023-06-01'");

        driver.findElement(By.xpath("//li/a[@href='#tab-information']")).click(); //Information Page

        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("//select[@name='manufacturer_id']")).click();
        driver.findElement(By.xpath("//option[text()='ACME Corp.']")).click();
        driver.findElement(By.xpath("//input[@name='keywords']")).sendKeys("toy");
        driver.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys("fluffy toy");
        driver.findElement(By.xpath("//div[@class='trumbowyg-editor']")).sendKeys("Fluffy toy Bunny very happy");
        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("Fluffy Bunny");
        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("Fluffy Bunny");
        driver.findElement(By.xpath("//input[@name='meta_description[en]']")).sendKeys("Fluffy Bunny Toy");

        driver.findElement(By.xpath("//li/a[@href='#tab-prices']")).click(); //Prices Page

        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("//input[@name='purchase_price']")).clear();
        driver.findElement(By.xpath("//input[@name='purchase_price']")).sendKeys("19");
        driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']")).click();
        driver.findElement(By.xpath("//option[@value='USD']")).click();
        driver.findElement(By.xpath("//input[@name='gross_prices[USD]']")).clear();
        driver.findElement(By.xpath("//input[@name='gross_prices[USD]']")).sendKeys("21");
        driver.findElement(By.xpath("//button[@name='save']")).click();
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
