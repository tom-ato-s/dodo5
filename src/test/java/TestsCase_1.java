import Pages.*;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Epic("Тестирование сайта пиццерии ДоДоПица")
@Link("https://dodopizza.ru")
@Feature("Step1: Перейти на 'https://dodopizza.ru/'" +
        "Step2: Добавить случайную пиццу из раздела \"Пицца\" в корзину, нажав кнопку \"Выбрать\"" +
        "Step3: Выбрать маленький размер пиццы, кликнув кнопку \"Маленькая\"" +
        "Step4: Добавить в корзину")
public class TestsCase_1 {
    public static WebDriver driver;
    public static Logger log = LogManager.getLogger();
    private static MainPage mainPage;
    private static SityPage sityPage;
    private List<WebElement> list;
    private static ParametrPizzaPage parametrPizzaPage;

    private static NewAdressPage newAdressPage;
    private static DeliveryPage deliveryPage;
    private int summPizza;

    @BeforeAll
    public static void start() {
        log.info("Case_0 Подключаем драйвер, настройки");
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
    @Issue(value = "Case_1")
    @Owner("Архипова Надежда")
    @DisplayName("Step1: Перейти на 'https://dodopizza.ru/'")
    @Description("Переход на сайт" +
            "Нажатие на регион во всплывающем окне. Выбрать регион «Москва»" +
            "Проверка отрытия страницы")
    public void test1() throws InterruptedException {
        log.info(" Step1 Создаем объекты MainPage, SityPage ");
        mainPage = new MainPage(driver);
        sityPage = new SityPage(driver);
        log.info("Открывается сайт \"dodopizza.ru\"");
        driver.get(ConfProperties.getProperty("syteDodoPizza"));
        Thread.sleep(5000);
        log.info("Проверка: открытия первой станицы сайта");
        Assertions.assertNotNull(driver.getTitle());
        log.info("Нажатие на регион во всплывающем окне. Выбрать регион «Москва»");
        sityPage.clickCityBtn();
    }

    @Test
    @Order(2)
    @Issue(value = "Case_1")
    @Owner("Архипова Надежда")
    @DisplayName("Step2: Добавить случайную пиццу из раздела \"Пицца\" в корзину, нажав кнопку \"Выбрать\"")
    @Description("Получение всx в товаров в РАЗДЕЛЕ \"Пицца\"" +
            "Нажатьие кнопки \"Выбрать\" на рендомно выбранной пицце" +
            "Получение и добавить значение названия добавленной пиццы в главном меню в mass[]" +
            "Проверить: соответствие наименования добавленной пиццы в главном меню к наименованию в окне параметризации")
    public void test2() {
        log.info("Step2: Добавить случайную пиццу из раздела \"Пицца\" в корзину, нажав кнопку \"Выбрать\"");
        log.info("Создаем объекты ParametrPizzaPage");
        parametrPizzaPage = new ParametrPizzaPage(driver);
        log.info("Получение всx в товаров в РАЗДЕЛЕ \"Пицца\"");
        list = mainPage.getListPizza();
        mainPage.clikRandomPizza(list);
        mainPage.addInMass(mainPage.getNamePizza());
        log.info("Проверить: соответствие наименования добавленной пиццы в главном меню к наименованию в окне параметризации");
        Assertions.assertEquals(mainPage.getNamePizza(), parametrPizzaPage.getNamePizzaParam());
    }
    @Test
    @Order(3)
    @Issue(value = "Case_1")
    @Owner("Архипова Надежда")
    @DisplayName("Step3: Выбрать маленький размер пиццы, кликнув кнопку \"Маленькая\"")
    @Description("Нажатие кнопки \"Маленькая\" в окне параметризации пиццы" +
            "Взятие значения цены на кнопке \"Добавить в корзину за {}Р" +
            "Сравнение значения цены на кнопке \"Добавить в корзину за {}Р\" и на главной странице (т.к. там указана цена за мелнькую пиццу)")
    public void test3() {
        log.info("Step3: Выбрать маленький размер пиццы, кликнув кнопку \"Маленькая\"");
        parametrPizzaPage = new ParametrPizzaPage(driver);
        log.info("Взятие значения цены на кнопке \"Добавить в корзину за {}Р");
        parametrPizzaPage.clicBtnSmail();
        log.info("Сравнение значения цены на кнопке \"Добавить в корзину за {}Р\" и на главной странице");
        Assertions.assertEquals(parametrPizzaPage.getPriseInBtn(), mainPage.getSummPizza());

    }

    @Test
    @Order(4)
    @Issue(value = "Case_1")
    @Owner("Архипова Надежда")
    @DisplayName("Step4: Добавить в корзину")
    @Description("Нажать: кнопку \"Добавить в корзину за {}Р\" на странице параметризации пиццы" +
            "Нажать кнопку \"Указать адрес доставки\" на всплывшем окне," +
            "Заполните поле \"Укажите улицу\" во всплывшем окне \"Новый адресс\"" +
            "Нажатие кнопки \"Сохранить\"" +
            "Проверить: Значение = 1 в шапке главной страницы счётчика добавленных товаров, в кнопке \"Корзина\"")
    public void test4() throws InterruptedException {
        log.info("Step4: Добавить в корзину");
        parametrPizzaPage = new ParametrPizzaPage(driver);
        deliveryPage = new DeliveryPage(driver);
        newAdressPage = new NewAdressPage(driver);

        parametrPizzaPage.clicBtnAddTooBasket();
        log.info("Нажать кнопку \"Указать адрес доставки\" на всплывшем окне");
        deliveryPage.clikAdress();
        log.info("Заполните поле \"Укажите улицу\" во всплывшем окне \"Новый адресс\"" );
        newAdressPage.putAdress();
        log.info("Нажатие кнопки \"Сохранить\"");
        newAdressPage.clickSave();
        log.info( "Проверить: Значение = 1 в шапке главной страницы счётчика добавленных товаров, в кнопке \"Корзина\"");
        Assertions.assertEquals(1, mainPage.getNamberPizza());
    }

    @AfterEach
    public void takeSkreenshot(TestInfo info) throws IOException {
        log.info("Делаем скриншот после окончания Step");
        File source = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/screenshots/" + info.getDisplayName().toLowerCase() + ".jpeg");
        FileHandler.copy(source, dest);
        try {
            Allure.addAttachment("PageView", FileUtils.openInputStream(source));
            log.info("Создан скриншот");
        } catch (IOException | NoSuchSessionException e) {
            log.error("Не удалось создать скриншот: \n" + e );
        }
    }
    @AfterAll
    public static void tearDown() {
        log.info("Закрытие окна браузера");
        driver.close();
        log.info("Закрытие драйвера");
        driver.quit();
    }
}
