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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AddNewItemToCatalogTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> genderInputsList;
    private String itemName = generateItemName();
    private String filePath = generateFilePath();

    @Before
    public void start() {
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void addNewItemToCatalogTest() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        wait.until(elementToBeClickable(By.xpath("//a[contains(text(), ' Add New Product')]"))).click();
        wait.until(elementToBeClickable(By.xpath("//label[contains(text(), 'Enabled')]/input"))).click();
        driver.findElement(By.name("name[en]")).sendKeys(itemName);
        driver.findElement(By.name("code")).sendKeys("testItemCode");
        driver.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).click();
        driver.findElement(By.xpath("//input[@data-name='Subcategory']")).click();
        genderInputsList = driver.findElements(By.name("product_groups[]"));
        Random rand = new Random();
        genderInputsList.get(rand.nextInt(genderInputsList.size())).click();
        driver.findElement(By.name("quantity")).sendKeys(String.valueOf(rand.nextInt(100)));
        driver.findElement(By.name("new_images[]")).sendKeys(filePath);
        driver.findElement(By.name("date_valid_from")).sendKeys("2020-01-01");
        Thread.sleep(5000);
        driver.findElement(By.name("date_valid_to")).sendKeys("2022-01-01");
        driver.findElement(By.xpath("//a[contains(text(), 'Information')]")).click();
        wait.until(elementToBeClickable(By.name("manufacturer_id"))).click();
        driver.findElement(By.xpath("//option[contains(text(), 'ACME')]")).click();
        driver.findElement(By.name("keywords")).sendKeys("testData");
        driver.findElement(By.name("short_description[en]")).sendKeys("testData_shortItemDescription");
        driver.findElement(By.name("description[en]")).sendKeys("testItem_LongDescription");
        driver.findElement(By.name("head_title[en]")).sendKeys("testItem_HeadTitle");
        driver.findElement(By.name("meta_description[en]")).sendKeys("testItem_MetaDescription");
        driver.findElement(By.xpath("//a[contains(text(), 'Prices')]")).click();
        wait.until(presenceOfElementLocated(By.name("purchase_price"))).sendKeys(String.valueOf(rand.nextInt(10)));
        driver.findElement(By.name("purchase_price_currency_code")).click();
        wait.until(elementToBeClickable(By.xpath("//option[@data-prefix='$']"))).click();
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("50");
        driver.findElement(By.name("gross_prices[EUR]")).sendKeys("40");
        driver.findElement(By.name("save")).click();
        wait.until(presenceOfElementLocated(By.xpath("//table[@class='dataTable']//a[contains(text(), '" + itemName + "')]")));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private String generateItemName() {
        Date date = new Date();
        Random rand = new Random();

        return "testItem" + (date.getTime() / 100000 + rand.nextInt(2000));
    }

    private String generateFilePath(){
        File img = new File("src/test/pictures/d5cf98eedcc655db4d344c5d2b2dc9f3.jpg");
        String s = img.getAbsolutePath();
        return s;
    }
}
