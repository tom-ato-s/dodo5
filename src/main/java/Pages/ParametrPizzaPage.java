package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Integer.parseInt;

public class ParametrPizzaPage {
    private WebDriver driver;
    private int summParam = 0;
    private String[] massParam = new String[5];
    private int sumParam = 0;

    /**
     * Определение локатора кнопки "Маленькая "в окне параметризации пиццы
     */
    @FindBy(xpath = "/html/body/div[4]/div/div[2]/div/div/div[2]/div[1]/div/div/div[1]/div/div[4]/div[1]/label[1]")
    private WebElement smailPizza;

    /**
     * Определение локатора кнопки "Добавить в корзину за {} "в окне параметризации пиццы
     */
    @FindBy(xpath = "/html/body/div[4]/div/div[2]/div/div/div[2]/div[2]/button")
    private WebElement btmAddToBasket;

    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public ParametrPizzaPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Метод для получения названия пиццы в окне параметризации пиццы
     */
    public String getNamePizzaParam() {
        String stringName = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div/div[2]/div[1]/div/div/div[1]/div/div[1]/h1")).getText().toString();
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
     * Метод для нажатия кнопки "Маленькая"
     */
    public void clicBtnSmail() {
        smailPizza.click();
    }


    /**
     * Метод для получения значения суммы пиццы в окне параметризации
     */
    public int getPriseInBtn() {
        String summString = btmAddToBasket.getText();
        StringBuilder strBuilder = new StringBuilder();
        StringBuilder strBuilderFin = new StringBuilder();
        strBuilder.append(summString);
        int len = strBuilder.length();
        for(int i = 0; i<len; i++) {
            char ch = strBuilder.charAt(i);
            if((ch >='0')&(ch<='9')) strBuilderFin.append(ch);
        }
        summParam = parseInt(strBuilderFin.toString());
        return  summParam;
    }
    /**
     * Метод для нажатия кнопки "Добавить в корзину за {}Р"
     */
    public void clicBtnAddTooBasket() {
        btmAddToBasket.click();
    }
    /**
     * Метод для добавления значения в mass[] значения имени пиццы
     */
    public void addInMassParam(String namePizzaParam) {
        for(int j = 0; j<5; j++) {
            if (massParam[j] == null) {
                massParam[j] = namePizzaParam;
                break;
            }
        }
    }

    /**
     * Метод для получения массива имен пицц massParam[] со страницы параметризации при добавлении пиццы
     */
    public String[] getMassNamesParam() {
        return massParam;
    }

    /**
     * Метод для добавления в массив sumParam цену пицы из окна параметризации
     */
    public void addPriseInSum(int prise) {
        sumParam = sumParam + prise;
    }
    /**
     * Метод для получения суммы всех выбранных пицц в окне параметризации
     */
    public int getSummParam() {
        return sumParam;
    }
}
