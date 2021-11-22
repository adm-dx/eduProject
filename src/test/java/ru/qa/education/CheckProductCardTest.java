package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class CheckProductCardTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement cardItemElement, regularPriceElement, campaignPriceElement;
    private String cardName, regularPrice, campaignPrice;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkProductCardTest() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");
        cardItemElement = driver.findElement(By.xpath("//h3[contains(text(), 'Campaigns')]/following-sibling::div//li"));
        cardName = cardItemElement.findElement(By.xpath(".//div[@class='name']")).getText();
        regularPriceElement = cardItemElement.findElement(By.xpath(".//*[@class='regular-price']"));
        campaignPriceElement = cardItemElement.findElement(By.xpath(".//*[@class='campaign-price']"));
        regularPrice = regularPriceElement.getText();
        campaignPrice = campaignPriceElement.getText();

//        Main page assertions
        assert checkIsGrayColor(regularPriceElement.getCssValue("color"));
        assert checkIsRedColor(campaignPriceElement.getCssValue("color"));
        assert (Double.parseDouble(regularPriceElement.getCssValue("font-size").replaceAll("px", "")) < Double.parseDouble(campaignPriceElement.getCssValue("font-size").replaceAll("px", "")));
        assert (regularPriceElement.getTagName().equals("s"));
        assert (campaignPriceElement.getCssValue("font-weight").equals("700"));

//        Navigate to Item page and find elements
        cardItemElement.click();
        regularPriceElement = driver.findElement(By.xpath("//s[@class='regular-price']"));
        campaignPriceElement = driver.findElement(By.xpath("//strong[@class='campaign-price']"));

//        Item page assertions
        assert driver.findElement(By.tagName("h1")).getText().equals(cardName);
        assert regularPriceElement.getText().equals(regularPrice);
        assert campaignPriceElement.getText().equals(campaignPrice);
        assert checkIsRedColor(campaignPriceElement.getCssValue("color"));
        assert checkIsGrayColor(regularPriceElement.getCssValue("color"));
        assert (regularPriceElement.getTagName().equals("s"));
        assert (Double.parseDouble(regularPriceElement.getCssValue("font-size").replaceAll("px", "")) < Double.parseDouble(campaignPriceElement.getCssValue("font-size").replaceAll("px", "")));
        assert (campaignPriceElement.getCssValue("font-weight").equals("700"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    public boolean checkIsGrayColor(String color) {
        String rgb = Color.fromString(color).asRgb().replaceAll("\\s+", "");
        String[] rgbs = rgb.split("\\)")[0].split("\\(")[1].split(",");
        return (Objects.equals(rgbs[0], rgbs[1])) && (Objects.equals(rgbs[1], rgbs[2]));
    }

    public boolean checkIsRedColor(String color) {
        String[] rgbs = Color.fromString(color).asRgb().replaceAll("\\s+", "").split("\\)")[0].split("\\(")[1].split(",");
        return Integer.parseInt(rgbs[0]) != 0 && Integer.parseInt(rgbs[1]) == 0 && Integer.parseInt(rgbs[2]) == 0;
    }
}
