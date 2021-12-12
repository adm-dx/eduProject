package ru.qa.education.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CheckoutPage {
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void removeItemsFromCartTillZero() {
        WebElement totalPriceElement;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        do {
            totalPriceElement = driver.findElement(By.xpath("//strong[contains(text(), '$')]"));
            driver.findElement(By.name("remove_cart_item")).click();
            wait.until(stalenessOf(totalPriceElement));
        } while (driver.findElements(By.name("remove_cart_item")).size() != 0);
    }
}
