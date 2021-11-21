package ru.qa.education;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountriesOrderCheckTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> list;
    private ArrayList<String> countriesList = new ArrayList<>();
    private ArrayList<String> countriesListSorted = new ArrayList<>();

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void countriesOrderCheckTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//ul[@id='box-apps-menu']//span[text()='Countries']")).click();
        list = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[5]/a"));
        for (WebElement item: list) {
            String str = item.getText();
            countriesList.add(str);
        }
        countriesListSorted =  countriesList;
        Collections.sort(countriesListSorted);
        assert countriesList.equals(countriesListSorted);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
