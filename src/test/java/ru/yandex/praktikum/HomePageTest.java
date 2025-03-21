package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HomePageTest {

    private final WebDriver driverChrome = new ChromeDriver();
    private final WebDriver driverFF = new FirefoxDriver();

    @Test
    public void testHomePageScooter() {
        driverChrome.get("https://qa-scooter.praktikum-services.ru/");
        driverFF.get("https://qa-scooter.praktikum-services.ru/");

        HomePageScooter objHomePageChrome = new HomePageScooter(driverChrome);
        HomePageScooter objHomePageFirefox = new HomePageScooter(driverFF);

        objHomePageChrome.verifyImportantQuestion();
        objHomePageFirefox.verifyImportantQuestion();
    }
    @After
    public void tearDown() {
        driverChrome.quit();
        driverFF.quit();
    }

}
