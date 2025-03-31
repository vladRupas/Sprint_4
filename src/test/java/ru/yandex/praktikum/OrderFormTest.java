package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFormTest {
    private WebDriver driver;
    protected final String metroStation;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phoneNumber;

    public OrderFormTest (String firstName,
                         String lastName,
                         String address,
                         String metroStation,
                         String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
    }

    @Parameterized.Parameters
    public static Object[][] getFieldInputs() {
        return new Object[][]{
                {"Имя", "Фамилия", "Адрес", "Парк культуры", "84950000000"},
                {"МоёИмя", "МояФамилия", "МойАдрес", "Сокольники", "84950000001"}
        };
    }

    @Test
    public void orderPageTest() {
        driver = new FirefoxDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        OrderPage objOrderPage = new OrderPage(driver);
        // Принимаем cookies
        objOrderPage.acceptCookies();
        // Проверяем, что обе кнопки "Заказать" работают
        objOrderPage.checkOrderButtons();
        // Заполняем форму "Для кого самокат"
        objOrderPage.fillOrderForm(firstName, lastName, address, metroStation, phoneNumber);
        // Ждём загрузки страницы "Про аренду"
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPage.makeOrderButton));
        // Заполняем форму "Про аренду"
        objOrderPage.fillAboutRentForm();
        // Ждём загрузки страницы "Хотите оформить заказ?"
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPage.yesButton));
        // Нажимаем на кнопку "Да"
        driver.findElement(objOrderPage.yesButton).click();
        // Ждём загрузки страницы "Заказ оформлен"
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPage.checkStatusButton));
        // Проверяем, что появилось всплывающее окно с подтверждением
        String expectedResult = "Заказ оформлен";
        String actualResult = (driver.findElement(objOrderPage.textOrderDone)).getText();
        assertTrue("Окно подтверждения заказа не появилось!", actualResult.contains(expectedResult));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}