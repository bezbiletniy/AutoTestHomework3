package ru.netology.webdriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

        System.setProperty("webdriver.chrome.driver", "driver/macOS/chromedriver");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearsDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldValidData() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Андрей Бородин");
        elements.get(1).sendKeys("+79256734744");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldNotValidNameAndLastName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        List<WebElement> elements1 = driver.findElements(By.className("input__sub"));
        elements.get(0).sendKeys("Andrey");
        elements.get(1).sendKeys("+79234565454");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__text")).click();
        String text = elements1.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotValidPhone() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        List<WebElement> elements1 = driver.findElements(By.className("input__sub"));
        elements.get(0).sendKeys("Андрей Бородин");
        elements.get(1).sendKeys("Игорь");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__text")).click();
        String text = elements1.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldValidDataUseCss() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Андрей Бородин");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79256734744");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }


}
