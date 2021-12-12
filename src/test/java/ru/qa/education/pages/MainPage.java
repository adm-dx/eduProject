package ru.qa.education.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToMainPage(){
        driver.get("http://localhost/litecart/en/");
    }

    public void goToProductPage() {
        driver.findElement(By.xpath("//li[contains(@class, 'product')]")).click();
    }

    public void goToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath("//a[contains(text() , 'Checkout')]")).click();
        wait.until(presenceOfElementLocated(By.xpath("//div[@id='checkout-customer-wrapper']")));
    }
}
