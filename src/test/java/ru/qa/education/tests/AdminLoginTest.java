package ru.qa.education.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.qa.education.pages.AdminPage;

public class AdminLoginTest {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void adminLoginTest(){
        AdminPage adminPage = new AdminPage(driver);
        adminPage.loginToApp("admin", "admin");
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}