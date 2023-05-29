package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SityPage {
    private static WebDriver driver;

    /**
     * Определение локатора выбора города
     */
@FindBy(xpath = "//a[@href='/moscow']")
private WebElement CityBtns;
    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public SityPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Метод для осуществления выбора города
     */
    @Step("Выбор региона - Москва")
    public void clickCityBtn() {
        try {
            CityBtns.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Не получилось нажать на кнопку " + e);
        }
    }
    /**
     * Метод возвращает WebElement кнопки с городом
     */
    public WebElement getElementSityButton(){
        return CityBtns;
    }
}