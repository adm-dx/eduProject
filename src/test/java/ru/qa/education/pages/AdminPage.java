package ru.qa.education.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class AdminPage {

    private WebDriver driver;
    private String itemName = generateItemName();
    private String filePath = generateFilePath();

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginToApp(String username, String password) {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }

    public void addNewProductToCatalog() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        wait.until(elementToBeClickable(By.xpath("//a[contains(text(), ' Add New Product')]"))).click();
        wait.until(elementToBeClickable(By.xpath("//label[contains(text(), 'Enabled')]/input"))).click();
        driver.findElement(By.name("name[en]")).sendKeys(itemName);
        driver.findElement(By.name("code")).sendKeys("testItemCode");
        driver.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).click();
        driver.findElement(By.xpath("//input[@data-name='Subcategory']")).click();
        List<WebElement> genderInputsList = driver.findElements(By.name("product_groups[]"));
        Random rand = new Random();
        genderInputsList.get(rand.nextInt(genderInputsList.size())).click();
        driver.findElement(By.name("quantity")).sendKeys(String.valueOf(rand.nextInt(100)));
        driver.findElement(By.name("new_images[]")).sendKeys(filePath);
        if (driver instanceof FirefoxDriver) {
            driver.findElement(By.name("date_valid_from")).sendKeys("2020-01-01");
            driver.findElement(By.name("date_valid_to")).sendKeys("2022-01-01");
        } else {
            driver.findElement(By.name("date_valid_from")).sendKeys("01012020");
            driver.findElement(By.name("date_valid_to")).sendKeys("01012022");
        }
        driver.findElement(By.xpath("//a[contains(text(), 'Information')]")).click();
        wait.until(elementToBeClickable(By.name("manufacturer_id"))).click();
        driver.findElement(By.xpath("//option[contains(text(), 'ACME')]")).click();
        driver.findElement(By.name("keywords")).sendKeys("testData");
        driver.findElement(By.name("short_description[en]")).sendKeys("testData_shortItemDescription");
        driver.findElement(By.name("description[en]")).sendKeys("testItem_LongDescription");
        driver.findElement(By.name("head_title[en]")).sendKeys("testItem_HeadTitle");
        driver.findElement(By.name("meta_description[en]")).sendKeys("testItem_MetaDescription");
        driver.findElement(By.xpath("//a[contains(text(), 'Prices')]")).click();
        wait.until(visibilityOfElementLocated(By.name("purchase_price"))).sendKeys(String.valueOf(rand.nextInt(10)));
        driver.findElement(By.name("purchase_price_currency_code")).click();
        wait.until(elementToBeClickable(By.xpath("//option[@data-prefix='$']"))).click();
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("50");
        driver.findElement(By.name("gross_prices[EUR]")).sendKeys("40");
        driver.findElement(By.name("save")).click();
        wait.until(presenceOfElementLocated(By.xpath("//table[@class='dataTable']//a[contains(text(), '" + itemName + "')]")));
    }

    private String generateItemName() {
        Date date = new Date();
        Random rand = new Random();

        return "testItem" + (date.getTime() / 100000 + rand.nextInt(2000));
    }

    private String generateFilePath() {
        File img = new File("src/test/pictures/d5cf98eedcc655db4d344c5d2b2dc9f3.jpg");
        String s = img.getAbsolutePath();
        return s;
    }

    public void goToCountries() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(presenceOfElementLocated(By.xpath("//span[text()='Countries']"))).click();
        wait.until(elementToBeClickable(By.xpath("//a[contains(text(), ' Add New Country')]"))).click();
    }

    public void clickExternalLinks() {
        List<WebElement> externalLinksList;
        WebDriverWait wait = new WebDriverWait(driver, 10);
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
}
