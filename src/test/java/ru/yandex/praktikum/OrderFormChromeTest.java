package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFormChromeTest {

    private WebDriver driverChrome;

    private final String firstName;
    private final String lastName;
    private final String address;
    protected final String metroStation;
    private final String phoneNumber;

    public OrderFormChromeTest (String firstName,
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
        // Этот тест для средней кнопки "Заказать" и только в Chrome (итак уже выглядит громоздко)
        driverChrome = new ChromeDriver();
        OrderPage objOrderPageChrome = new OrderPage(driverChrome);
        driverChrome.get("https://qa-scooter.praktikum-services.ru/");

        WebElement questionChrome = driverChrome.findElement(objOrderPageChrome.middleOrderButton);
        ((JavascriptExecutor) driverChrome).executeScript("arguments[0].scrollIntoView();", questionChrome);

        objOrderPageChrome.clickMiddleOrderButton();
        // Заполняем форму "Для кого самокат"
        objOrderPageChrome.fillOrderForm(firstName, lastName, address, metroStation, phoneNumber);
        // Ждём загрузки страницы "Про аренду"
        new WebDriverWait(driverChrome, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPageChrome.makeOrderButton));
        // Заполняем форму "Про аренду"
        objOrderPageChrome.fillAboutRentForm();
        // Ждём загрузки страницы "Хотите оформить заказ?"
        new WebDriverWait(driverChrome, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPageChrome.yesButton));
        // Нажимаем на кнопку "Да"
        driverChrome.findElement(objOrderPageChrome.yesButton).click();
        // Ждём загрузки страницы "Заказ оформлен"
        new WebDriverWait(driverChrome, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPageChrome.checkStatusButton));
        // Проверяем, что появилось всплывающее окно с подтверждением
        String expectedResult = "Заказ оформлен";
        String actualResultChrome = (driverChrome.findElement(objOrderPageChrome.textOrderDone)).getText();
        assertTrue("Окно подтверждения заказа в Chrome не появилось!", actualResultChrome.contains(expectedResult));
    }
    @After
    public void tearDown() {
        driverChrome.quit();
    }

}
