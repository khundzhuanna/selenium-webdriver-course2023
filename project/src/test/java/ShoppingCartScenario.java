import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ShoppingCartScenario {
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
    public void shoppingCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        login();
        addProductToCart();
        addProductToCart();
        addProductToCart();

        assert driver.findElement(By.xpath("//span[@class='quantity']")).getText().equals("3");
        driver.findElement(By.xpath("//a[@href='http://localhost/litecart/en/checkout' and text()='Checkout »']")).click();

        List<WebElement> duckyElements = driver.findElements(By.xpath("//div[@class='viewport']/ul/li"));
        while (duckyElements.size() > 0) {
            WebElement duckyElement = duckyElements.get(0);
            String duckyName = duckyElement.findElement(By.tagName("div")).findElement(By.tagName("strong")).getText();

            WebElement trElement = driver.findElement(By.xpath("//td[@class='item' and text()='" + duckyName + "']/.."));
            duckyElement.findElement(By.xpath("//button[@name='remove_cart_item']")).click();
            wait.until(ExpectedConditions.stalenessOf(trElement));
            duckyElements = driver.findElements(By.xpath("//div[@class='viewport']/ul/li"));
        }
    }

    public void addProductToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.navigate().to("http://localhost/litecart/en/");
        driver.findElement(By.xpath("//div[@id='box-most-popular']//ul//li[1]")).click();

        try {
            driver.findElement(By.tagName("select")).click();
            driver.findElement(By.xpath("//option[@value='Small']")).click();
        }
        catch (NoSuchElementException e) {}
        finally {
            WebElement cartCountElement = driver.findElement(By.xpath("//span[@class='quantity']"));
            Integer cartCount = Integer.parseInt(cartCountElement.getText());

            driver.findElement(By.xpath("//button[@name='add_cart_product']")).click();
            wait.until(ExpectedConditions.textToBePresentInElement(cartCountElement, String.valueOf(cartCount + 1)));
        }
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
