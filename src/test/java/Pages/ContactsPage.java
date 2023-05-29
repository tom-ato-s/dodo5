package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ContactsPage {
   private WebDriver driver;
   private List <WebElement> telefonlist;

    /**
     * Определение локатора номера телефона на странице "Контакты"
     */
    @FindBy(xpath = "//div[@class='contacts-phone__number']")
    private WebElement telephone;


    /**
     * Определение локатора электронной почты на странице "Контакты"
     */
    @FindBy(xpath = "//a[@class='contacts-link footer-link']")
    private WebElement mail;
    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public ContactsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    /**
     * Метод для получения номера телефона на странице "Контакты"
     */
    public String getTelephone() {
        String telefonXpath = "//div[@class='contacts-phone__number']";
        telefonlist = driver.findElements(By.xpath(telefonXpath));
        String telefonSt = null;
        for (int i = 0; i < telefonlist.size(); i++) {
            WebElement telefonElement = telefonlist.get(i);
            telefonSt = telefonElement.getText().toString();
            if (telefonSt == "") {
                System.out.println(" WebElement 'Телефон' не отображается");
            }
        }

        return telefonSt;
    }
    public String getMail() {
        System.out.println( mail.getText().toString());
        return mail.getText().toString();
    }
    public boolean visiblTelefon() {
        String telefonXpath = "//div[@class='contacts-phone__number']";
        telefonlist = driver.findElements(By.xpath(telefonXpath));
        boolean telefonVisible = false;
        for (int i = 0; i < telefonlist.size(); i++) {
            WebElement telefonElement = telefonlist.get(i);
            telefonVisible = telefonElement.isDisplayed();
            if (telefonVisible == false) {
                System.out.println(" WebElement 'Телефон' не отображается");
            }
        }
        return telefonVisible;
    }
    public boolean visiblMail() {
        boolean mailVisibl = mail.isDisplayed();
        return mailVisibl;
    }

}
