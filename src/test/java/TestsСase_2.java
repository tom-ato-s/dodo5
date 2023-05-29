
import Pages.*;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Epic("Тестирование сайта пиццерии ДоДоПица")
@Link("https://dodopizza.ru")
@Feature("Step1: Перейти на 'https://dodopizza.ru/'" +
        "Step2: Добавить 5 случайных пицц из раздела \"Пицца\"" +
        "Step3: Кликнуть на кнопку корзины" +
        "Step4: Получить стоимость на странице \"Корзина\"")
public class TestsСase_2 {
    private static WebDriver driver;
    private SityPage sityPage;
    private MainPage mainPage;
    private BasketPage basketPage;
    private ParametrPizzaPage parametrPizzaPage;
    private static NewAdressPage newAdressPage;
    private static DeliveryPage deliveryPage;
    private List<WebElement> list;
    private WebDriverWait webDriverWait;

    @BeforeAll
    public static void start() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    @Order(1)
    @Issue(value = "Case_2")
    @Owner("Архипова Надежда")
    @DisplayName("Step1: Перейти на 'https://dodopizza.ru/'")
    @Description("Переход на сайт" +
            "Нажатие на регион во всплывающем окне. Выбрать регион «Москва»" +
            "Проверка отрытия страницы")
    public void test1() throws InterruptedException {
        System.out.println("1dfgdfgdf");
        mainPage = new MainPage(driver);
        sityPage = new SityPage(driver);

        driver.get(ConfProperties.getProperty("syteDodoPizza"));
        Thread.sleep(5000);
        Assertions.assertNotNull(driver.getTitle());
        Thread.sleep(500);
        sityPage.clickCityBtn();
    }


    @Test
    @Order(2)
    @Issue(value = "Case_2")
    @Owner("Архипова Надежда")
    @DisplayName("Step2: Добавить 5 случайных пицц из раздела \"Пицца\"")
    @Description("Получение всx товаров в РАЗДЕЛЕ \"Пицца\" на главной странице" +
            "Сравнение счётчика добавленных товаров в корзине и Значения = 5")
    public void test2() throws InterruptedException {
        mainPage = new MainPage(driver);
        parametrPizzaPage = new ParametrPizzaPage(driver);
//        deliveryPage = new DeliveryPage(driver);
//        newAdressPage = new NewAdressPage(driver);
        // получение всx товаров в РАЗДЕЛЕ "Пицца" на главной странице
        list = mainPage.getListPizza();

        // следующие 4 пункта повторяем 5 раза (Добавляем 5 пиццы в корзину)
        for(int k = 0; k < 5; k++) {
            // Нажатие кнопки "Выбрать" на рендомно выбранной пицце
            mainPage.clikRandomPizza(list);
            // Получить и добавить значения названия пиццы в массив massParams[]
            parametrPizzaPage.addInMassParam(parametrPizzaPage.getNamePizzaParam());
            // Получить и добавить заначение суммы пицы в переменну sumParam
            parametrPizzaPage.addPriseInSum(parametrPizzaPage.getPriseInBtn());
            // Нажать на кнопку «Добавить в корзину за {}Р»
            parametrPizzaPage.clicBtnAddTooBasket();
            if(k == 0) {
                deliveryPage.clikAdress();
                newAdressPage.putAdress();
                newAdressPage.clickSave();
            }
        }
        // закончили добавлять товар в корзину
        // Сравнение счётчика добавленных товаров в корзине и Значения = 5
        Assertions.assertEquals(mainPage.getNamberPizza(), 5);
    }
    @Test
    @Order(3)
    @Issue(value = "Case_2")
    @Owner("Архипова Надежда")
    @DisplayName("Step3: Кликнуть на кнопку корзины")
    @Description("Нажать на кнопку «Корзина» на странице главного меню" +
            "Сравнить отсортированные массивы massBasket[] и massParams[] на равенство компонентов")
    public void test3(){

        basketPage = new BasketPage(driver);
     //   mainPage = new MainPage(driver);
        parametrPizzaPage = new ParametrPizzaPage(driver);
        // Нажать на кнопку «Корзина» на странице главного меню
        mainPage.clickBtnBasket();
        // Сравнить отсортированные массивы massBasket[] и massParams[] на равенство компонентов
        Assertions.assertTrue(Arrays.equals(basketPage.getMassNamesBasket(basketPage.getListBasket()), parametrPizzaPage.getMassNamesParam()));
    }

    @Test
    @Order(4)
    @Issue(value = "Case_2")
    @Owner("Архипова Надежда")
    @DisplayName("Step4: Получить стоимость на странице \"Корзина\"")
    @Description("Сравнить сумму заказа summBasket  и полученую ранее summParam")
    public void test4(){
        Assertions.assertEquals(basketPage.getSummBasket(), parametrPizzaPage.getSummParam());
    }

    @AfterEach
    public void takeSkreenshot(TestInfo info) throws IOException {
        File source =((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/screenshots/"+info.getDisplayName().toLowerCase() + ".jpeg");
        FileHandler.copy(source, dest);
        try {
            Allure.addAttachment("PageView", FileUtils.openInputStream(source));
        } catch (IOException | NoSuchSessionException e) {
            System.out.println(e);
        }
        System.out.println("Делаем скриншот");
    }
    @AfterAll
    public static void tearDown() {
        System.out.println("All the tests are executed");
        driver.close();
        driver.quit();
    }
}
