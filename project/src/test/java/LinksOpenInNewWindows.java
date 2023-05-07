import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LinksOpenInNewWindows {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void login() {
        driver.navigate().to("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void openingLinks() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        login();

        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=countries&doc=edit_country&country_code=AU' and @title]/i")).click();

        List<WebElement> linkElements = driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        for (int i = 0; i < linkElements.size(); i++) {
            WebElement link = linkElements.get(i);
            // workaround because Chrome doesn't respect "target='_blank'" for some reason
            shiftClick(link, driver);
            wait.until(driver -> driver.getWindowHandles().size() == 2);

            oldWindows = driver.getWindowHandles();
            oldWindows.remove(mainWindow);
            driver.switchTo().window(oldWindows.iterator().next());
            driver.close();

            oldWindows = driver.getWindowHandles();
            driver.switchTo().window(oldWindows.iterator().next());
        }
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }

    private static void shiftClick(WebElement element, WebDriver driver) {
        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .click(element)
                .keyUp(Keys.SHIFT)
                .perform();
    }
}
