package ru.yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderPage {

    private final WebDriver driver;
    // Кнопка "Заказать" наверху страницы
    protected By topOrderButton = By.className("Button_Button__ra12g");
    // Кнопка "Заказать" в середине страницы
    protected By middleOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // Поле "Имя"
    private final By firstNameInput = By.cssSelector("input[placeholder = '* Имя']");
    // Поле "Фамилия"
    private final By lastNameInput = By.cssSelector("input[placeholder = '* Фамилия']");
    // Поле "Адрес"
    private final By addressInput = By.cssSelector("input[placeholder = '* Адрес: куда привезти заказ']");
    // Поле "Станция метро"
    private final By metroStationInput = By.cssSelector("input[placeholder = '* Станция метро']");
    // Поле "Телефон"
    private final By phoneNumberInput = By.cssSelector("input[placeholder = '* Телефон: на него позвонит курьер']");
    // Кнопка "Далее"
    protected By nextButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // Поле "Когда привезти самокат"
    private final By startDateInput = By.cssSelector("input[placeholder = '* Когда привезти самокат']");
    // Дата из календаря
    private final By selectStartDate = By.cssSelector
            (".react-datepicker__day.react-datepicker__day--030." +
                    "react-datepicker__day--selected.react-datepicker__day--weekend");
    // Поле "Срок аренды"
    private final By durationTimeInput = By.xpath
            ("//div[(@class = 'Dropdown-placeholder') and (text() = '* Срок аренды')]");
    // Поле "сутки" в выпадающем листе
    private final By setDurationTime = By.xpath
            ("//div[(@class = 'Dropdown-option') and (text() = 'сутки')]");
    // Кнопка "Заказать"
    protected By makeOrderButton = By.xpath
            ("//button[(@class = 'Button_Button__ra12g Button_Middle__1CSJM') and " +
                    "(text() = 'Заказать')]");
    // Кнопка "Да"
    protected By yesButton = By.xpath("//div[@class = 'Order_Modal__YZ-d3']" +
            "//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");
    // Текст "Заказ оформлен"
    protected By textOrderDone = By.xpath("//div[contains(text(), 'Заказ оформлен')]");
    // Кнопка "Посмотреть статус"
    protected By checkStatusButton = By.xpath("//div[@class = 'Order_Modal__YZ-d3']" +
            "//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");

    public OrderPage (WebDriver driver) {
        this.driver = driver;
    }

    // Метод кликает на кнопку "Заказать" наверху страницы
    public void clickTopOrderButton() {
        WebElement element = driver.findElement(topOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        driver.findElement(topOrderButton).click();
    }

    // Метод кликает на кнопку "Заказать" в середине страницы
    public void clickMiddleOrderButton() {
        WebElement element = driver.findElement(middleOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        driver.findElement(middleOrderButton).click();
    }

    // Метод заполняет форму заказа
    public void fillOrderForm(String firstName,
                              String lastName,
                              String address,
                              String metroStation,
                              String phoneNumber) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);
        selectMetroStation(metroStation);
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
        driver.findElement(nextButton).click();
    }

    // Метод выбирает станцию метро из списка станций
    public void selectMetroStation(String metroStation) {
        driver.findElement(metroStationInput).sendKeys(metroStation);
        // Выпадающий список станций метро
        driver.findElement(By.xpath("//div[text() = '" + metroStation + "']")).click();
    }

    // Метод заполняет форму "Про аренду"
    public void fillAboutRentForm() {
        driver.findElement(startDateInput).click();
        driver.findElement(startDateInput).sendKeys("30.03.2025");
        driver.findElement(selectStartDate).click();
        driver.findElement(durationTimeInput).click();
        driver.findElement(setDurationTime).click();
        driver.findElement(makeOrderButton).click();
    }

}
