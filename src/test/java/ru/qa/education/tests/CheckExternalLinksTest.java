package ru.qa.education.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.qa.education.pages.AdminPage;

public class CheckExternalLinksTest {
    private WebDriver driver;

    @Before
    public void start() {
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
    }

    @Test
    public void checkExternalLinksTest() {
        AdminPage adminPage = new AdminPage(driver);
        adminPage.loginToApp("admin", "admin");
        adminPage.goToCountries();
        adminPage.clickExternalLinks();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
