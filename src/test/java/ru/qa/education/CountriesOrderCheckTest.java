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
    private List<WebElement> countriesNamesList, countriesZonesList, zoneNameList;
    private ArrayList<String> countriesList = new ArrayList<>();
    private ArrayList<String> countriesListSorted = new ArrayList<>();
    private ArrayList<String> zonesList = new ArrayList<>();
    private ArrayList<String> zonesListSorted = new ArrayList<>();

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
        countriesNamesList = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[5]/a"));
        for (WebElement item: countriesNamesList) {
            String str = item.getText();
            countriesList.add(str);
        }
        countriesListSorted = (ArrayList<String>) countriesList.clone();
        Collections.sort(countriesListSorted);
        assert countriesList.equals(countriesListSorted);
        countriesZonesList = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[6]"));
        for (int i = 0; i < countriesZonesList.size(); i++) {
            int quantity = Integer.parseInt(countriesZonesList.get(i).getText());
            if (quantity > 0) {
                driver.findElement(By.xpath("//tr[contains(@class, 'row')]//td[6]/preceding-sibling::td/a")).click();
                zoneNameList = driver.findElements(By.xpath("//table[@id='table-zones']//tr[not(contains(@class,'header'))]/td[3]/input"));
                for (WebElement zoneNameElement: zoneNameList) {
                    zonesList.add(zoneNameElement.getText());
                }
                zonesListSorted = (ArrayList<String>) zonesList.clone();
                Collections.sort(zonesListSorted);
                assert zonesListSorted.equals(zonesList);
                zonesList.clear();
                zonesListSorted.clear();
                driver.findElement(By.xpath("//button[@name='cancel']")).click();
                countriesZonesList = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[6]"));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
