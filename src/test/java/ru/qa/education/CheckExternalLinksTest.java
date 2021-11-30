package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class CheckExternalLinksTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> externalLinksList;

    @Before
    public void start() {
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkExternalLinksTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(presenceOfElementLocated(By.xpath("//span[text()='Countries']"))).click();
        wait.until(elementToBeClickable(By.xpath("//a[contains(text(), ' Add New Country')]"))).click();
        externalLinksList = driver.findElements(By.xpath("//td[@id='content']//table//a[@target='_blank']"));
        for (WebElement linkElement: externalLinksList) {
            String mainWindowID = driver.getWindowHandle();
            linkElement.click();
            ArrayList<String> newWindowID = new ArrayList<>(driver.getWindowHandles());
            newWindowID.remove(mainWindowID);
            driver.switchTo().window(newWindowID.get(0));
            wait.until(presenceOfElementLocated(By.tagName("h1")));
            driver.close();
            driver.switchTo().window(mainWindowID);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
