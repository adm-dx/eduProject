package ru.qa.education.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ItemPage {
    private WebDriver driver;
    private int itemsCounter;

    public ItemPage(WebDriver driver){
        this.driver = driver;
    }

    public void addItemToCart(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        itemsCounter = Integer.parseInt(driver.findElement(By.xpath("//span[@class='quantity']")).getText());
        WebElement addToCartButton = wait.until(presenceOfElementLocated(By.name("add_cart_product")));
        if (driver.findElements(By.name("options[Size]")).size() > 0) {
            driver.findElement(By.name("options[Size]")).click();
            driver.findElement(By.xpath("//option[@value='Small']")).click();
        }
        wait.until(elementToBeClickable(addToCartButton)).click();
        wait.until(textToBe(By.xpath("//span[@class='quantity']"), String.valueOf(itemsCounter += 1)));
    }

    public void goToMainPage(){
        driver.findElement(By.xpath("//div[@id='logotype-wrapper']")).click();
    }
}
