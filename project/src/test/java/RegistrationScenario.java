import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class RegistrationScenario {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void clientRegister() {
        driver.navigate().to("http://localhost/litecart/en/");
        driver.findElement(By.xpath("//tr/td/a")).click();

        driver.findElement(By.name("tax_id")).sendKeys("6677 890393");
        driver.findElement(By.name("company")).sendKeys("Nokia");
        driver.findElement(By.name("firstname")).sendKeys("Ben");
        driver.findElement(By.name("lastname")).sendKeys("Dover");
        driver.findElement(By.name("address1")).sendKeys("LA, White City, 78");
        driver.findElement(By.name("address2")).sendKeys("none");
        driver.findElement(By.name("postcode")).sendKeys("12345");
        driver.findElement(By.name("city")).sendKeys("White City");
        driver.findElement(By.xpath("//span[@class='select2-selection__rendered']")).click();
        driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("United States" + Keys.ENTER);
        String randomEmail = UUID.randomUUID().toString().substring(0, 10);
        driver.findElement(By.name("email")).sendKeys(randomEmail + "@mail.us");
        String randomPhone = String.valueOf(Math.random()).substring(2, 12);
        driver.findElement(By.name("phone")).sendKeys("+" + randomPhone);
        driver.findElement(By.name("password")).sendKeys("who_askeD4");
        driver.findElement(By.name("confirmed_password")).sendKeys("who_askeD4");
        driver.findElement(By.name("create_account")).click();

        driver.findElement(By.xpath("//div/ul[@class='list-vertical']/li/a[@href='http://localhost/litecart/en/logout']")).click();

        driver.findElement(By.name("email")).sendKeys(randomEmail + "@mail.us");
        driver.findElement(By.name("password")).sendKeys("who_askeD4");

        driver.findElement(By.xpath("//button[@name='login']")).click();
        driver.findElement(By.xpath("//div/ul[@class='list-vertical']/li/a[@href='http://localhost/litecart/en/logout']")).click();
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}

