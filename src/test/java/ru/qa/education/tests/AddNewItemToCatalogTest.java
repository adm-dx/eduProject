package ru.qa.education.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.qa.education.pages.AdminPage;

public class AddNewItemToCatalogTest {
    private WebDriver driver;

    @Before
    public void start() {
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
    }

    @Test
    public void addNewItemToCatalogTest() {
        AdminPage adminPage = new AdminPage(driver);
        adminPage.loginToApp("admin", "admin");
        adminPage.addNewProductToCatalog();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
