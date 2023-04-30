import org.openqa.selenium.support.Color;
import java.lang.String;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CorrectPageChecker {
    private class Item {
        public String name;
        public Integer normalPrice;
        public Integer discountPrice;
        public Boolean isNormalPriceDashed;
        public Boolean isNormalPriceGray;
        public Boolean isDiscountPriceBold;
        public Boolean isDiscountPriceRed;
        public Boolean isDiscountPriceBiggerThanNormalPrice;
    }
    private WebDriver driver;

    @BeforeMethod
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ruaaaye/Tools/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        login();
    }

    public void login() {
        driver.navigate().to("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void correctChecking() {
        driver.navigate().to("http://localhost/litecart/en/");

        WebElement mainPageElement = driver.findElement(By.xpath("//div[@id='box-campaigns']/div/ul/li"));
        Item mainPageItem = new Item();
        mainPageItem.name = mainPageElement.findElement(By.xpath(".//div[@class='name']")).getText();
        fillItemInfo(mainPageItem, mainPageElement);

        driver.navigate().to(mainPageElement.findElement(By.tagName("a")).getAttribute("href"));

        WebElement itemPageElement = driver.findElement(By.xpath("//div[@id='box-product']/div[@class='content']/div[@class='information']"));
        Item itemPageItem = new Item();
        itemPageItem.name = driver.findElement(By.tagName("h1")).getText();
        fillItemInfo(itemPageItem, itemPageElement);

//        а) на главной странице и на странице товара совпадает текст названия товара
        assert mainPageItem.name.equals(itemPageItem.name);
//        б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        assert mainPageItem.normalPrice.equals(itemPageItem.normalPrice);
        assert mainPageItem.discountPrice.equals(itemPageItem.discountPrice);
//        в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        assert mainPageItem.isNormalPriceDashed;
        assert mainPageItem.isNormalPriceGray;
        assert itemPageItem.isNormalPriceDashed;
        assert itemPageItem.isNormalPriceGray;
//        г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
//        (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
        assert mainPageItem.isDiscountPriceBold;
        assert mainPageItem.isDiscountPriceRed;
        assert itemPageItem.isDiscountPriceBold;
        assert itemPageItem.isDiscountPriceRed;
//        д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
        assert mainPageItem.isDiscountPriceBiggerThanNormalPrice;
        assert itemPageItem.isDiscountPriceBiggerThanNormalPrice;
    }

    public void fillItemInfo(Item item, WebElement element) {
        WebElement priceElement = element.findElement(By.className("price-wrapper"));
        WebElement normalPriceElement = priceElement.findElement(By.className("regular-price"));
        WebElement discountPriceElement = priceElement.findElement(By.className("campaign-price"));

        String normalPriceElementColor = normalPriceElement.getCssValue("color");
        String discountPriceElementColor = discountPriceElement.getCssValue("color");
        String normalizedNormalPriceElementColor = Color.fromString(normalPriceElementColor).asHex().substring(1);
        String normalizedDiscountPriceElementColor = Color.fromString(discountPriceElementColor).asHex().substring(1);

        String normalPriceElementFont = normalPriceElement.getCssValue("font-size");
        String discountPriceElementFont = discountPriceElement.getCssValue("font-size");
        String normalizedNormalPriceElementFont = normalPriceElementFont.replace("px", "");
        String normalizedDiscountPriceElementFont =   discountPriceElementFont.replace("px", "");

        item.normalPrice = Integer.parseInt(normalPriceElement.getText().substring(1));
        item.discountPrice = Integer.parseInt(discountPriceElement.getText().substring(1));
        item.isNormalPriceDashed = normalPriceElement.getTagName().equals("s");
        item.isNormalPriceGray = normalizedNormalPriceElementColor.codePoints().distinct().count() == 1;
        item.isDiscountPriceBold = discountPriceElement.getTagName().equals("strong");
        item.isDiscountPriceRed = normalizedDiscountPriceElementColor.substring(2, 4).equals("00")
                &&
                normalizedDiscountPriceElementColor.substring(4, 6).equals("00");
        item.isDiscountPriceBiggerThanNormalPrice = Float.parseFloat(normalizedDiscountPriceElementFont)
                >
                Float.parseFloat(normalizedNormalPriceElementFont);
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
