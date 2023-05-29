package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeliveryPage {
    private WebDriver driver;
    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public DeliveryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    /**
     * Определение локатора кнопки "Укажите адрес доставки"
     */
    @FindBy(xpath = "/html/body/div[4]/div[2]/div[2]/div/div/div[1]/button")
    private WebElement btmAdress;

    /**
     * Метод для нажатия кнопки "Указать адрес доставки"
     */
    public void clikAdress() {
        btmAdress.click();
    }

}