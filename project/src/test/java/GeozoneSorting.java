import java.lang.String;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeozoneSorting {
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
    public void geozoneSorting() {
        login();
        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        List<WebElement> elements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[3]"));
        for (int j = 0; j < elements.size(); j++) {
            WebElement country = elements.get(j);
            String countryURL = country.findElement(By.tagName("a")).getAttribute("href");
            driver.navigate().to(countryURL);

            List<WebElement> nestedElements = driver.findElements(By.xpath("//*[@id='table-zones']/tbody/tr/td[3]/select/option[@selected='selected']"));
            if (!isSorted(nestedElements)) {
                throw new RuntimeException();
            }
            driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            elements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[3]"));
        }
    }

    public boolean isSorted(List<WebElement> arr) {
        List<String> unsortedList = new ArrayList<>();
        List<String> sortedList = new ArrayList<>();
        for (int j = 0; j < arr.size(); j++) {
            unsortedList.add(arr.get(j).getText());
            sortedList.add(arr.get(j).getText());
        }
        Collections.sort(sortedList);
        for (int i = 0; i < sortedList.size(); i++) {
            if (!sortedList.get(i).equals(unsortedList.get(i))) {
                return false;
            }
        }
        return true;
    }


    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
