import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenBrowser {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void OpenBrowser() {
        driver.get("https://ya.ru/");
        driver.findElement(By.className("alice-fab")).click();
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
        }
    }
