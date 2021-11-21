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

public class GeoZonesOrderCheckTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> countriesList;
    private List<WebElement> zonesList;
    private ArrayList<String> geoZonesList = new ArrayList<>();
    private ArrayList<String> geoZonesListSorted = new ArrayList<>();

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void geoZonesOrderCheckTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//ul[@id='box-apps-menu']//span[text()='Geo Zones']")).click();
        countriesList = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[3]/a"));
        for (int i = 0; i < countriesList.size(); i++) {
            countriesList.get(i).click();
            zonesList = driver.findElements(By.xpath("//table[@id='table-zones']//tr[not(contains(@class,'header'))]/td[3]//option[@selected='selected']"));
            for (WebElement zoneElement : zonesList) {
                geoZonesList.add(zoneElement.getText());
            }
            geoZonesListSorted = geoZonesList;
            Collections.sort(geoZonesListSorted);
            assert geoZonesList.equals(geoZonesListSorted);
            driver.findElement(By.xpath("//button[@name = 'cancel']")).click();
            countriesList = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[3]/a"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
