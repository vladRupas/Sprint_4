package ru.yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

class HomePageScooter {
    private final WebDriver driver;

    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
    }

    // Метод закрывает нижний баннер (принимает cookies)
    public void acceptCookies() {
        driver.findElement(By.className("App_CookieButton__3cvqF")).click();
    }

    // Метод проверяет, совпадают ли тексты
    public void verifyImportantAnswers(int index, String expectedText) {
        List<WebElement> questions = driver.findElements(By.className("accordion__button"));
        List<WebElement> answers = driver.findElements(By.className("accordion__panel"));

        WebElement question = questions.get(index);
        question.click();

        WebElement answer = answers.get(index);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(answer));

        String actualText = answer.getText();

        assertEquals("Texts aren't equal at index " + index + "!", expectedText, actualText);
    }
}