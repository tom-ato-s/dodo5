package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
public class BasketPage {
    private WebDriver driver;

    private String xpathStringElementPizza;
    private List<WebElement> listBasket;
    private String[] massBasket = new String[5];
    private int summBasket = 0;

    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public BasketPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    /**
     * Метод для возврата листа webElement названий пицц в "Корзина"
     */
    public List<WebElement> getListBasket() {
        xpathStringElementPizza = "/html/body/div[4]/div/div[2]/div/div/div[1]/main/section[2]/article[2]/div[2]/div/h3";
        listBasket = driver.findElements(By.xpath(xpathStringElementPizza));
        return listBasket;
    }

    /**
     * Метод для получения имени пицы из WebElement пицы
     */
    public String getNameBasket(WebElement name) {
        String stringName = name.getText().toString();
        StringBuilder strBuilder = new StringBuilder();
        StringBuilder strBuilderFin = new StringBuilder();
        strBuilder.append(stringName);
        int len = strBuilder.length();
        for(int i = 0; i<len; i++) {
            char ch = strBuilder.charAt(i);
            if((ch == ' ')||(ch == ' ')||((ch <= 'Я')&(ch>= 'A'))||((ch <= 'я')&(ch>= 'а'))||(ch == 'ё')) {
                strBuilderFin.append(ch);
            }else {strBuilderFin.delete(i-1, i);
                break;
            };
        }
        return strBuilderFin.toString();
    }

    /**
     * Метод для получения массива имен пиц из окна "Корзина"
     */
    public String[] getMassNamesBasket(List<WebElement>  listBasket) {
        for(int i=0; i<=5; i++) {
            massBasket[i] = getNameBasket(listBasket.get(i));
        }
        return massBasket;
    }

    /**
     * Метод для получения суммы заказа на странице "Корзина"
     */
    public int  getSummBasket() {
        String summString =  driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div/div[1]/main/div/div[2]/section/div[2]/span")).getText().toString();
        StringBuilder strBuilder = new StringBuilder();
        StringBuilder strBuilderFin = new StringBuilder();
        strBuilder.append(summString);
        int len = strBuilder.length();
        for(int i = 0; i<len; i++) {
            char ch = strBuilder.charAt(i);
            if((ch >='0')&(ch<='9')) strBuilderFin.append(ch);
        }
        summBasket = Integer.parseInt(strBuilderFin.toString());
        return summBasket;
    }
}