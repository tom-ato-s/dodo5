import Pages.MainPage;
import Pages.SityPage;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
@Epic("Тестирование сайта пиццерии ДоДоПица")
@Link("https://dodopizza.ru")
@Feature("Step1: Проверка открытия первой страницы, " +
        "Step2: Проверить общее кол-во товаров в РАЗДЕЛЕ \"Пицца\" " +
        "Step3: В шапке страницы, рядом с надписью \"Доставка пиццы \", отображается наименование региона")
public class TestsCase_0 {

    private static WebDriver driver;
    public static Logger log = LogManager.getLogger();
    private MainPage mainPage;
    private SityPage sityPage;


    @BeforeAll
    public static void start() {
        log.info("Case_1 Подключаем драйвер, настройки");
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
    @Issue(value = "Case_0")
    @Owner("Архипова Надежда")
    @DisplayName("Step1: Проверка открытия первой страницы")
    @Description("Проверка: открытия первой станицы сайта" +
            "Нажатие на регион во всплывающем окне. Выбрать регион «Москва»")
    public void test1() throws InterruptedException {
        log.info(" Step1 Создаем объекты MainPage, SityPage ");
        mainPage = new MainPage(driver);
        sityPage = new SityPage(driver);
        log.info("Открывается сайт \"dodopizza.ru\"");
        driver.get(ConfProperties.getProperty("syteDodoPizza"));
        Thread.sleep(5000);
        log.info("Проверка: открытия первой станицы сайта");
        Assertions.assertNotNull(driver.getTitle());
        Thread.sleep(500);
        log.info("Нажатие на регион во всплывающем окне. Выбрать регион «Москва»");
        sityPage.clickCityBtn();
    }
    @Test
    @Order(2)
    @Issue(value = "Case_0")
    @Owner("Архипова Надежда")
    @DisplayName("Step2: Проверить общее кол-во товаров в РАЗДЕЛЕ \"Пицца\" ")
    @Description("Получение количества товаров в разделе \"Пицца\"" +
            " Сравнение количества отображаемых элементов и ожидаемых 33")
    public void test2() {
        log.info("Step2: Проверить общее кол-во товаров в РАЗДЕЛЕ \"Пицца\"" );
        mainPage = new MainPage(driver);
        System.out.println("2dfgdfgdf");
        log.info("Получение количества товаров в разделе \"Пицца\"");
        int calk = mainPage.quantityPizzasInSection();
        log.info("Сравнение количества отображаемых элементов и ожидаемых 33");
        calcElementStep(33, calk);
    }
    @Step ("Сравнение количества отображенных элементов в разделе Пицца. Ожидается {expected}, реально {actual}")
    public static void calcElementStep(int expected, int actual) {
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @Issue(value = "Case_0")
    @Owner("Архипова Надежда")
    @DisplayName("Step3: В шапке страницы, рядом с надписью \"Доставка пиццы \", отображается наименование региона")
    @Description(" в шапке страницы, рядом с надписью \"Доставка пиццы \" получить значение названия региона" +
            "Сравнение значений поля региона и ожидаемого «Москва»")
    public void test3() {
        log.info("Step3: В шапке страницы, рядом с надписью \"Доставка пиццы \", отображается наименование региона");
        mainPage = new MainPage(driver);
        log.info(" в шапке страницы, рядом с надписью \"Доставка пиццы \" получить значение названия региона");
        String region = mainPage.getNameRegion();
        log.info("Сравнение значений поля региона и ожидаемого «Москва»");
        Assertions.assertEquals("Москва", region);
    }
    @AfterEach
    public void takeSkreenshot(TestInfo info) throws IOException {
        log.info("Делаем скриншот после окончания Step");
        File source =((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/screenshots/"+info.getDisplayName().toLowerCase() + ".jpeg");
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
