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

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CheckAddAndRemoveItemsTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private int itemsCounter;
    private WebElement totalPriceElement;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkAddAndRemoveItems(){
        driver.get("http://localhost/litecart/en/");
        itemsCounter = Integer.parseInt(driver.findElement(By.xpath("//span[@class='quantity']")).getText());
        for (int i = 0; i < 3; i++) {
            driver.findElement(By.xpath("//li[contains(@class, 'product')]")).click();
            WebElement addToCartButton = wait.until(presenceOfElementLocated(By.name("add_cart_product")));
            if (driver.findElements(By.name("options[Size]")).size() > 0) {
                driver.findElement(By.name("options[Size]")).click();
                driver.findElement(By.xpath("//option[@value='Small']")).click();
            }
            wait.until(elementToBeClickable(addToCartButton)).click();
            wait.until(textToBe(By.xpath("//span[@class='quantity']"), String.valueOf(itemsCounter += 1)));
            driver.findElement(By.xpath("//div[@id='logotype-wrapper']")).click();
        }
        driver.findElement(By.xpath("//a[contains(text() , 'Checkout')]")).click();
        wait.until(presenceOfElementLocated(By.xpath("//div[@id='checkout-customer-wrapper']")));
        do {
            totalPriceElement = driver.findElement(By.xpath("//strong[contains(text(), '$')]"));
            driver.findElement(By.name("remove_cart_item")).click();
            wait.until(stalenessOf(totalPriceElement));
        } while (driver.findElements(By.name("remove_cart_item")).size() != 0);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
