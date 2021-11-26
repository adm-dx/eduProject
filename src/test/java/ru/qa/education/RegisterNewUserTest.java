package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RegisterNewUserTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String email = getRandomPrefix() + "@testmail.ru";
    private String secret = "testPass";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void registerNewUserTest() {
        driver.get("http://localhost/litecart/en/create_account");
        driver.findElement(By.name("tax_id")).sendKeys("testData");
        driver.findElement(By.name("company")).sendKeys("testData");
        driver.findElement(By.name("firstname")).sendKeys("Tester");
        driver.findElement(By.name("lastname")).sendKeys("Tester");
        driver.findElement(By.name("address1")).sendKeys("testData");
        driver.findElement(By.name("address2")).sendKeys("testData");
        driver.findElement(By.name("postcode")).sendKeys("25415");
        driver.findElement(By.name("city")).sendKeys("Moscow");
        driver.findElement(By.xpath("//span[contains(@class, 'select2')]")).click();
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("United States");
        driver.findElement(By.xpath("//li[(text() ='United States')]")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys("+931231231");
        driver.findElement(By.name("password")).sendKeys(secret);
        driver.findElement(By.name("confirmed_password")).sendKeys(secret);
        driver.findElement(By.name("create_account")).click();
        WebElement logoutLink = wait.until(presenceOfElementLocated(By.xpath("//a[(text() = 'Logout')]")));
        logoutLink.click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(secret);
        driver.findElement(By.name("login")).click();
        logoutLink = wait.until(presenceOfElementLocated(By.xpath("//a[(text() = 'Logout')]")));
        logoutLink.click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private String getRandomPrefix(){
        Date date = new Date();
        Random rand = new Random();

        return String.valueOf(date.getTime()/1000 + rand.nextInt(2000));
    }
}
