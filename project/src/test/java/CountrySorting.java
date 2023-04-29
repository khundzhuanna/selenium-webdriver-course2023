import java.util.Collections;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CountrySorting {
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        login();
    }

    //а) проверяет, что страны расположены в алфавитном порядке
    //
    //б) для тех стран, у которых количество зон отлично от нуля -- открывает страницу этой страны
    // и там проверяет, что геозоны расположены в алфавитном порядке

    public void login() {
        driver.navigate().to("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void geozonesSorting() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<WebElement> countriesElements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[5]"));
        List<WebElement> geozonesElements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[6]"));

        for (int i = 0; i < geozonesElements.size(); i++) {
            if (!geozonesElements.get(i).getText().equals("0")) {
                String countryURL = countriesElements.get(i).findElement(By.tagName("a")).getAttribute("href");
                driver.navigate().to(countryURL);

                List<WebElement> nestedGeozonesElements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[3]"));
                nestedGeozonesElements.remove(nestedGeozonesElements.size() - 1);
                if (!isSorted(nestedGeozonesElements)) {
                    throw new RuntimeException();
                }

                driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
            countriesElements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[5]"));
            geozonesElements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[6]"));
        }
    }

    @Test
    public void countrySorting() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<WebElement> countriesElements = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[5]"));

        if (!isSorted(countriesElements)) {
            throw new RuntimeException();
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
