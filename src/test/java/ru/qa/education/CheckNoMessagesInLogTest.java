package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class CheckNoMessagesInLogTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> catalogItemsElementsList;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkNoMessagesInLogTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        wait.until(elementToBeClickable(By.xpath("//a[text()='Rubber Ducks']"))).click();
        catalogItemsElementsList = driver.findElements(By.xpath("//tr[@class='row']//td[3]/a[contains(@href, 'edit_product')]"));
        for (int i = 0; i < catalogItemsElementsList.size(); i++) {
            catalogItemsElementsList.get(i).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Edit Product')]")));
            List<LogEntry> logEntries = driver.manage().logs().get("browser").getAll();
            assert logEntries.isEmpty();
            driver.findElement(By.name("cancel")).click();
            catalogItemsElementsList.clear();
            wait.until(elementToBeClickable(By.xpath("//a[text()='Rubber Ducks']"))).click();
            catalogItemsElementsList = driver.findElements(By.xpath("//tr[@class='row']//td[3]/a[contains(@href, 'edit_product')]"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
