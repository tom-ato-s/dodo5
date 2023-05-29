package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.Random;

public class MainPage {
    private WebDriver driver;
    private List<WebElement> list;
    private WebElement rendomWebElement;
    private int randomPizzaInt;
    private String xpathStringElementPizza;
    private int summ = 0;
    private String[]  mass = new  String[5];

    public static String SyteDodoPizza = "https://dodopizza.ru";
    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Определение локаторов товаров в РАЗДЕЛЕ "Пицца"
     */
    @FindBy(xpath = ".//a[@class='header__about-slogan-text header__about-slogan-text_locality header__about-slogan-text_link']")
    private WebElement Region;


    /**
     * Определение локатора окна параметризации пиццы
     */
    @FindBy(xpath = "/html/body/div[4]/div")
    private WebElement PageParamPizza;

    /**
     * Определение локатора кнопки "Корзина" на главной странице
     */
    @FindBy(xpath = "//*[@id=\"react-app\"]/nav/div/div[2]/button")
    //*[@id="react-app"]/nav/div/div[2]/button/div[2]
    private WebElement BtnBasket;

    /**
     * Определение локатора кнопки "Корзина" с количеством пиц на главной странице
     */
    @FindBy(xpath = "//*[@id=\"react-app\"]/nav/div/div[2]/button/div[2]")
    private WebElement BtnBasketCalk;

    /**
     * Определение локатора кнопки "Контакты"
     */
    @FindBy(xpath = "//*[@id=\"react-app\"]/nav/div/ul/li[8]/a")
    private WebElement BtnContacts;
    /**
     * Метод для подсчета количества товаров в РАЗДЕЛЕ "Пицца"
     */
    public int quantityPizzasInSection() {
        List list = driver.findElements(By.xpath("//*[@id='pizzas']//*[@class='sc-1tpn8pe-3 bXdCJp']"));
        int calc = list.size();
        return calc;
    }

    /**
     * Метод проверка отображения региона
     */
    public String getNameRegion() {
        return  Region.getText();
    }

    /**
     * Метод для получение всx в товаров в РАЗДЕЛЕ "Пицца"
     */
    public List<WebElement> getListPizza() {
        xpathStringElementPizza = "//*[@id=\"pizzas\"]/article";
        // list = driver.findElements(By.xpath(".//*[@id='pizzas']/article/footer/button[@type='button']"));
        list = driver.findElements(By.xpath(xpathStringElementPizza));
        return list;
    }

    /**
     * Метод: на рендомно выбранную пиццу нажимаем кнопку "Выбрать"
     */
    public void clikRandomPizza(List<WebElement> list) {

        int calc = list.size();
        //Получаем рендомное значение элемента в list
        Random random = new Random();
        randomPizzaInt = random.nextInt(calc);
        //получем из list элемент с рендомным номером
        rendomWebElement = list.get(randomPizzaInt-1);
        // Нажимаем на кнопку "Выбрать"
        try {
            driver.findElement(By.xpath(getXpathStrElementPizzaBtn())).click();
        }catch (ElementClickInterceptedException ex) {
            System.out.println("Не полуилось нажать на кнопку " + ex);
            // открываем страницу пицци через ссытку на название рендомной пиццы
            driver.findElement(By.xpath(getXpathStrElementPizzaName())).click();
        }
    }
    /**
     * Метод для Xpath кнопки "Выбрать" рендомного элемента пиццы
     */
    public String getXpathStrElementPizzaBtn() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xpathStringElementPizza).append("[").append(randomPizzaInt).append("]/footer/button");
        return stringBuilder.toString();
    }

    /**
     * Метод для Xpath кнопки названия пиццы рендомного элемента пиццы в главном меню
     */
    public String getXpathStrElementPizzaName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xpathStringElementPizza).append("[").append(randomPizzaInt).append("]/main/div");
        return stringBuilder.toString();
    }

    /**
     * Метод для Xpath кнопки названия пиццы рендомного элемента пиццы в окне параметризации
     */
    public String getXpathStrElementPizzaNameParam() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xpathStringElementPizza).append("/div/div[").append(randomPizzaInt).append("]");
        return stringBuilder.toString();
    }
    /**
     * Метод для Xpath кнопки суммы пиццы рендомного элемента пиццы в главном меню
     */
    public String getXpathStrElementPizzaSumm() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xpathStringElementPizza).append("[").append(randomPizzaInt).append("]/footer/div");
        return stringBuilder.toString();
    }


    /**
     * Метод для получения значения названия добавленной пиццы в главном меню
     */
    public String getNamePizza() {
        String stringName = driver.findElement(By.xpath(getXpathStrElementPizzaName())).getText().toString();
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
     * Метод для получения значения суммы рендомной пиццы в главном меню
     */
    public int getSummPizza() {
        String summString =  driver.findElement(By.xpath(getXpathStrElementPizzaSumm())).getText();
        StringBuilder strBuilder = new StringBuilder();
        StringBuilder strBuilderFin = new StringBuilder();
        strBuilder.append(summString);
        int len = strBuilder.length();
        for(int i = 0; i<len; i++) {
            char ch = strBuilder.charAt(i);
            if((ch >='0')&(ch<='9')) strBuilderFin.append(ch);
        }
        summ = summ + Integer.parseInt(strBuilderFin.toString());
        return  summ;
    }

    /**
     * Метод для добавления значения в mass[] значения имени пиццы
     */
    public void addInMass(String name) {
        for(int j = 0; j<6; j++) {
            if (mass[j] ==null) {
                mass[j] = name;
                break;
            }
        }

    }

    /**
     * Метод для получения количества пицц на кнопке "Козина" главном меню
     */
    public int getNamberPizza() {
        String st = BtnBasketCalk.getText().toString();
        int namber = Integer.parseInt(st);
        return namber;
    }
    /**
     * Метод для нажатия кнопки "Корзина"
     */
    public void clickBtnBasket() {
        BtnBasket.click();
    }

    /**
     * Метод для нажатия кнопки "Контакты"
     */
    public void clickBtnContacts() throws InterruptedException {
        Thread.sleep(5000);
        BtnContacts.click();
    }

    /**
     * Метод возвращает элемент "Контакты"
     */
    public WebElement getElementContact() {
      return   BtnContacts;
    }
}
