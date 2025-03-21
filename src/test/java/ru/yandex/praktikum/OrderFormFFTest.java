package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFormFFTest {

    private WebDriver driverFF;

    private final String firstName;
    private final String lastName;
    private final String address;
    protected final String metroStation;
    private final String phoneNumber;

    public OrderFormFFTest (String firstName,
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
        // Этот тест для верхней кнопки "Заказать" и только в Firefox
        driverFF = new FirefoxDriver();
        OrderPage objOrderPageFF = new OrderPage(driverFF);
        driverFF.get("https://qa-scooter.praktikum-services.ru/");

        WebElement questionFF = driverFF.findElement(objOrderPageFF.topOrderButton);
        ((JavascriptExecutor) driverFF).executeScript("arguments[0].scrollIntoView();", questionFF);

        objOrderPageFF.clickTopOrderButton();
        // Заполняем форму "Для кого самокат"
        objOrderPageFF.fillOrderForm(firstName, lastName, address, metroStation, phoneNumber);
        // Ждём загрузки страницы "Про аренду"
        new WebDriverWait(driverFF, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPageFF.makeOrderButton));
        // Заполняем форму "Про аренду"
        objOrderPageFF.fillAboutRentForm();
        // Ждём загрузки страницы "Хотите оформить заказ?"
        new WebDriverWait(driverFF, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPageFF.yesButton));
        // Нажимаем на кнопку "Да"
        driverFF.findElement(objOrderPageFF.yesButton).click();
        // Ждём загрузки страницы "Заказ оформлен"
        new WebDriverWait(driverFF, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(objOrderPageFF.checkStatusButton));
        // Проверяем, что появилось всплывающее окно с подтверждением
        String expectedResult = "Заказ оформлен";
        String actualResultFF = (driverFF.findElement(objOrderPageFF.textOrderDone)).getText();
        assertTrue("Окно подтверждения заказа в Firefox не появилось!", actualResultFF.contains(expectedResult));
    }
    @After
    public void tearDown() {
        driverFF.quit();
    }

}
