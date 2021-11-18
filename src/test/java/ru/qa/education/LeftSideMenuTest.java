package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LeftSideMenuTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> leftMenuItems;
    private List<WebElement> dropDownLeftMenuItems;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void adminLoginTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("box-apps-menu")));
        leftMenuItems = driver.findElements(By.xpath("//ul[@id='box-apps-menu']/li/a"));
        for (int i = 0; i < leftMenuItems.size(); i++) {
            leftMenuItems.get(i).click();
            dropDownLeftMenuItems = driver.findElements(By.xpath("//ul[@class='docs']/li/a"));
            for (int j = 0; j < dropDownLeftMenuItems.size(); j++) {
                dropDownLeftMenuItems.get(j).click();
                dropDownLeftMenuItems = driver.findElements(By.xpath("//ul[@class='docs']/li/a"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
                driver.findElement(By.tagName("h1")).isDisplayed();
            }
            leftMenuItems = driver.findElements(By.xpath("//ul[@id='box-apps-menu']/li/a"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
