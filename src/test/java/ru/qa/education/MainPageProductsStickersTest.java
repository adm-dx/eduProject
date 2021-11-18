package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageProductsStickersTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> productList;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void mainPageProductsStickersTest() {
        driver.get("http://localhost/litecart/en/");
        productList = driver.findElements(By.xpath("//li[contains(@class, 'product')]"));
        for (WebElement product: productList) {
            product.findElement(By.xpath(".//*[contains(@class, 'sticker')]")).isDisplayed();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
