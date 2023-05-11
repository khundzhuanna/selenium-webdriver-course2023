import java.util.List;
import java.lang.RuntimeException;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class StickerChecker {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void stickerChecker() {
        driver.navigate().to("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.navigate().to("http://localhost/litecart/en/");

        List<WebElement> duckies = driver.findElements(By.xpath("//li[contains(@class, 'product')]"));
        for (int j = 0; j < duckies.size(); j++) {
            WebElement ducky = duckies.get(j); // - достаём уточку
            List<WebElement> stickerDucky = ducky.findElements(By.xpath(".//div[contains(@class, 'sticker')]"));

            if (stickerDucky.size() == 0) {
                throw new NoSuchElementException();
            } else if (stickerDucky.size() > 1) {
                throw new RuntimeException("More than one sticker!");
            }
        }
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}


