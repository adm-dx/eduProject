package ru.qa.education.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.qa.education.pages.CheckoutPage;
import ru.qa.education.pages.ItemPage;
import ru.qa.education.pages.MainPage;

public class CheckAddAndRemoveItemsTest {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkAddAndRemoveItems() {
        MainPage mainPage = new MainPage(driver);
        ItemPage itemPage = new ItemPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        mainPage.goToMainPage();
        for (int i = 0; i < 3; i++) {
            mainPage.goToProductPage();
            itemPage.addItemToCart();
            itemPage.goToMainPage();
        }
        mainPage.goToCheckout();
        checkoutPage.removeItemsFromCartTillZero();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
