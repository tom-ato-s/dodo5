package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewAdressPage {
    private WebDriver driver;

    WebDriverWait webDriverWait;

    /**
     * Определение локатора поля "Укажите улицу"
     */
    @FindBy(xpath = "//*[@id=\"animated-input-1\"]")
    private WebElement adress;

    /**
     * Определение локатора поля "Сохранить" на всплывающем поле "Новый адрес"
     */
    @FindBy(xpath = "/html/body/div[4]/div[2]/div[2]/div/div/form/button")
    private WebElement btnSave;
    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public NewAdressPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    /**
     * Метод для заполнения поля "Адрес доставки"
     */
    public void putAdress() throws InterruptedException {
        webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOf(adress));
        adress.sendKeys("Москва, Профсоюзная улица, 120/29"+Keys.TAB+ Keys.TAB+ Keys.ENTER);
        Thread.sleep(10000);
    }
    /**
     * Метод для заполнения поля "Адрес доставки"
     */
    public void clickSave() {
        webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(btnSave));
        btnSave.click();
    }
}
