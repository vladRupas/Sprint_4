package ru.yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HomePageScooter {

    private final WebDriver driver;
    // Список "Вопросов о важном"
    private final List<By> questionChoices = Arrays.asList(
            By.id("accordion__heading-0"),
            By.id("accordion__heading-1"),
            By.id("accordion__heading-2"),
            By.id("accordion__heading-3"),
            By.id("accordion__heading-4"),
            By.id("accordion__heading-5"),
            By.id("accordion__heading-6"),
            By.id("accordion__heading-7")
    );
    // Список текстов ответов, которые открываются при клике на вопросы
    private final List<By> answerTexts = Arrays.asList(
            By.id("accordion__panel-0"),
            By.id("accordion__panel-1"),
            By.id("accordion__panel-2"),
            By.id("accordion__panel-3"),
            By.id("accordion__panel-4"),
            By.id("accordion__panel-5"),
            By.id("accordion__panel-6"),
            By.id("accordion__panel-7")
    );
    // Ожидаемые ответы
    private final List<String> expectedTexts = Arrays.asList(
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. "
                    + "Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. "
                    + "Мы привозим самокат 8 мая в течение дня. "
                    + "Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. "
                    + "Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. "
                    + "Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. "
                    + "Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    );

    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
    }
    // Метод кликает на все вопросы о важном (по порядку) и проверяет, правильный ли открывается текст
    public void verifyImportantQuestion() {
        for (int i = 0; i < questionChoices.size(); i++) {
            /* Я вынужден включить следующие 2-е строчки в код:
            потому что без них:
            -> если Chrome (или Firefox) браузер во время теста открылся не в режиме полного окна, то:
            1). невозможно кликнуть на последний вопрос "Я жизу за МКАДом, привезёте?"
            (кстати в котором есть опечатка: "жиЗу" вместо "жиВу"),
            так как он будет закрыт баннером "И здесь куки! В общем, мы их используем.".
            2). тест падает с такой ошибкой:
            "org.openqa.selenium.ElementClickInterceptedException:
            Element <div id="accordion__heading-7" class="accordion__button"> is not clickable at point (773,545)
            because another element <div class="App_CookieConsent__1yUIN"> obscures it"
            */
            WebElement question = driver.findElement(questionChoices.get(i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);

            String expectedText = expectedTexts.get(i);
            driver.findElement(questionChoices.get(i)).click();
            String actualText = driver.findElement(answerTexts.get(i)).getText();
            assertEquals("Texts aren't equal at index " + i + " !", expectedText, actualText);
        }
    }

}
