import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeftMenuChecker {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void LeftMenuChecker() {
        driver.navigate().to("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> elements = driver.findElements(By.xpath("//ul[@class='list-vertical']/li"));
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).click();
            List<WebElement> nestedElements = driver.findElements(By.xpath("//ul[@class='list-vertical']/li/ul[@class='docs']/li"));
            for (int j = 0; j < nestedElements.size(); j++) {
                nestedElements.get(j).click();
                driver.findElement(By.tagName("h1"));
                nestedElements = driver.findElements(By.xpath("//ul[@class='list-vertical']/li/ul[@class='docs']/li"));
            }
            driver.findElement(By.tagName("h1"));
            elements = driver.findElements(By.xpath("//ul[@class='list-vertical']/li"));
        }
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
